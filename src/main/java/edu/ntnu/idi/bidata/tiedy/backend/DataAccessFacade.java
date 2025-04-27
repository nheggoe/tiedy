package edu.ntnu.idi.bidata.tiedy.backend;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.DataRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonTaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonUserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Intended to be the only class that the frontend package interacts with, it would provide all the
 * necessary public interface.
 *
 * @author Nick Heggø
 * @version 2025.04.12
 */
public class DataAccessFacade implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(DataAccessFacade.class.getName());

  private static DataAccessFacade instance;

  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  private DataAccessFacade() {
    userRepository = JsonUserRepository.getInstance();
    taskRepository = JsonTaskRepository.getInstance();
  }

  /**
   * Retrieves the singleton instance of the DataAccessFacade class. This method ensures that only
   * one instance of the DataAccessFacade is created during the application lifecycle, providing
   * centralized access to data management and related operations.
   *
   * @return the singleton instance of DataAccessFacade
   */
  public static synchronized DataAccessFacade getInstance() {
    if (instance == null) {
      instance = new DataAccessFacade();
    }
    return instance;
  }

  // ------------------------  Runnable  ------------------------

  /**
   * Executes a routine to persist changes in the application's repositories and logs the
   * operation's timestamp.
   *
   * <p>This method iterates through relevant repositories (groupRepository, userRepository, and
   * taskRepository) and calls their `saveChanges` method, ensuring all pending changes are saved.
   * After completing the save operations, it logs the current time to indicate the application's
   * state-saving operation has been performed.
   */
  @Override
  public void run() {
    List.of(userRepository, taskRepository).forEach(DataRepository::saveChanges);
    LOGGER.info(() -> LocalDateTime.now() + " Application state saved");
  }

  // ------------------------  Defensive Copying  ------------------------

  private Task createDetachedCopy(Task original) {
    return new Task(original);
  }

  // ------------------------  User Repository Methods  ------------------------

  /**
   * Registers a new user in the system. If a user with the same username already exists, the
   * registration will fail and return null.
   *
   * @param user the User object to be registered; must contain a valid username
   * @return the newly registered User object if successful, or null if a user with the same
   *     username already exists
   */
  public User registerUser(User user) {
    Objects.requireNonNull(user);
    if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
      return null;
    }

    return userRepository.add(user);
  }

  /**
   * Authenticates a user using the provided username and plaintext password.
   *
   * @param username the username of the user attempting to authenticate; must not be null or blank
   * @param plainTextPassword the plaintext password of the user attempting to authenticate; must
   *     not be null or blank
   * @return an {@code Optional} containing the authenticated {@code User} if authentication is
   *     successful; otherwise, an empty {@code Optional}
   */
  public Optional<User> authenticate(String username, String plainTextPassword) {
    Objects.requireNonNull(username);
    Objects.requireNonNull(plainTextPassword);
    return userRepository.authenticate(username, plainTextPassword);
  }

  // ------------------------  Task Repository Methods  ------------------------

  /**
   * Updates an existing task in the repository.
   *
   * @param task the Task object to be updated
   * @return the updated Task object
   */
  public Task updateTask(Task task) {
    Objects.requireNonNull(task);
    return taskRepository.update(task);
  }

  /**
   * Deletes a task from the task repository using its unique identifier.
   *
   * @param taskId the unique identifier of the task to be deleted; must not be null
   */
  public boolean removeTask(UUID taskId) {
    Objects.requireNonNull(taskId);
    return taskRepository.remove(taskId);
  }

  /**
   * Adds a new task to the repository.
   *
   * @param task the Task object to be added
   * @return the added Task object
   */
  public Task addTask(Task task) {
    Objects.requireNonNull(task);
    return taskRepository.add(task);
  }

  /**
   * Retrieves a list of tasks assigned to a specific user.
   *
   * @param userId the unique identifier of the user whose assigned tasks are to be retrieved
   * @return a list of Task objects assigned to the specified user; if no tasks are found, returns
   *     an empty list
   */
  public List<Task> getTasksByUserId(UUID userId) {
    Objects.requireNonNull(userId);
    return taskRepository.getTasksByUserId(userId).map(this::createDetachedCopy).toList();
  }

  /**
   * Retrieves a list of tasks assigned to a specific user that are not in the CLOSED status. The
   * default filter option
   *
   * @param userId the unique identifier (UUID) of the user whose non-closed tasks are to be
   *     retrieved
   * @return a list of Task objects that are assigned to the specified user and are not in the
   *     CLOSED status; an empty list is returned if no such tasks are found
   */
  public List<Task> getActiveTasksByUserId(UUID userId) {
    Objects.requireNonNull(userId);
    return taskRepository
        .getTasksByUserId(userId)
        .filter(task -> task.getStatus() != Status.CLOSED)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Retrieves a list of active tasks assigned to a specific user that match a given status. Only
   * tasks that are not in the CLOSED status and match the specified status are included.
   *
   * @param userId the unique identifier (UUID) of the user whose tasks are to be retrieved
   * @param status the status of the tasks to be filtered (e.g., PENDING, COMPLETED)
   * @return a list of Task objects that are assigned to the specified user, are not in the CLOSED
   *     status, and have the specified status. Returns an empty list if no tasks meet the criteria.
   */
  public List<Task> getActiveTasksByUserIdAndStatus(UUID userId, Status status) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(status);
    return taskRepository
        .getActiveTasksByUserId(userId)
        .filter(task -> task.getStatus() == status)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Retrieves a list of active tasks assigned to a specific user that match a given priority.
   * Active tasks are tasks that are not in the CLOSED status.
   *
   * @param userId the unique identifier (UUID) of the user whose tasks are to be retrieved; must
   *     not be null
   * @param priority the priority of the tasks to be filtered (e.g., HIGH, MEDIUM, LOW); must not be
   *     null
   * @return a list of Task objects that are assigned to the specified user, are not in the CLOSED
   *     status, and have the specified priority; returns an empty list if no tasks meet the
   *     criteria
   */
  public List<Task> getActiveTasksByUserIdAndPriority(UUID userId, Priority priority) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(priority);
    return taskRepository
        .getActiveTasksByUserId(userId)
        .filter(task -> task.getPriority() == priority)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Assigns a task to a user.
   *
   * @param taskId the unique identifier of the task to be assigned
   * @param userId the unique identifier of the user to whom the task is to be assigned
   * @return true if the task is successfully assigned to the user, false otherwise
   */
  public boolean assignTaskToUser(UUID taskId, UUID userId) {
    Objects.requireNonNull(taskId);
    Objects.requireNonNull(userId);
    return taskRepository.assignTaskToUser(taskId, userId);
  }

  /**
   * Retrieves a list of tasks that are assigned to a specific user and match a given status.
   *
   * @param userId the unique identifier (UUID) of the user whose tasks are to be retrieved
   * @param status the status of the tasks to be filtered (e.g., PENDING, COMPLETED)
   * @return a list of Task objects that are assigned to the specified user and have the specified
   *     status. Returns an empty list if no tasks meet the criteria.
   */
  public List<Task> getTasksByUserAndStatus(UUID userId, Status status) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(status);
    return taskRepository
        .getTasksByUserId(userId)
        .filter(task -> task.getStatus() == status)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Retrieves a map of active tasks for a specific user, grouped by their deadlines within a given
   * week. Only tasks that are not in the CLOSED status and fall within the week starting from the
   * specified date are included.
   *
   * @param userId the unique identifier (UUID) of the user whose active tasks are to be retrieved;
   *     must not be null
   * @param startOfWeek the starting date of the week for which tasks are to be retrieved; must not
   *     be null
   * @return a map where the key is the task deadline (LocalDate) and the value is a list of active
   *     tasks for the specified user with that deadline; if no matching tasks are found, an empty
   *     map is returned
   */
  public Map<LocalDate, List<Task>> getActiveTasksByUserIdAndWeek(
      UUID userId, LocalDate startOfWeek) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(startOfWeek);
    return taskRepository
        .getActiveTasksByUserId(userId)
        .filter(filterTasksForCurrentWeek(startOfWeek))
        .map(this::createDetachedCopy)
        .collect(Collectors.groupingBy(Task::getDeadline));
  }

  /**
   * Retrieves a map of tasks assigned to a specific user, grouped by their deadlines, filtered by a
   * specified week and status.
   *
   * @param userId the unique identifier (UUID) of the user whose tasks are to be retrieved; must
   *     not be null
   * @param startOfWeek the starting date of the week for which tasks are to be retrieved; must not
   *     be null
   * @param status the status of the tasks to be filtered (e.g., PENDING, COMPLETED); must not be
   *     null
   * @return a map where the key is the task deadline (LocalDate) and the value is a list of tasks
   *     that match the specified user, week, and status; if no matching tasks are found, an empty
   *     map is returned
   */
  public Map<LocalDate, List<Task>> getTasksByUserIdAndWeekAndStatus(
      UUID userId, LocalDate startOfWeek, Status status) {
    Objects.requireNonNull(userId);
    Objects.requireNonNull(startOfWeek);
    Objects.requireNonNull(status);
    return taskRepository
        .getTasksByUserId(userId)
        .filter(filterTasksForCurrentWeek(startOfWeek))
        .filter(task -> task.getStatus() == status)
        .map(this::createDetachedCopy)
        .collect(Collectors.groupingBy(Task::getDeadline));
  }

  private Predicate<Task> filterTasksForCurrentWeek(LocalDate startOfWeek) {
    Objects.requireNonNull(startOfWeek);
    Predicate<Task> beforeThisWeek = task -> task.getDeadline().isBefore(startOfWeek);
    Predicate<Task> afterThisWeek = task -> task.getDeadline().isAfter(startOfWeek.plusDays(6));
    return Predicate.not(beforeThisWeek).and(Predicate.not(afterThisWeek));
  }
}
