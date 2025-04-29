package edu.ntnu.idi.bidata.tiedy.backend;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonGroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonTaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonUserRepository;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DataChangeNotifier;
import java.time.LocalDate;
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
 * @version 2025.04.29
 */
public class DataAccessFacade {

  private static final Logger LOGGER = Logger.getLogger(DataAccessFacade.class.getName());

  private static DataAccessFacade instance;

  private final DataChangeNotifier notifier;
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final GroupRepository groupRepository;

  private DataAccessFacade() {
    notifier = DataChangeNotifier.getInstance();
    userRepository = JsonUserRepository.getInstance();
    taskRepository = JsonTaskRepository.getInstance();
    groupRepository = JsonGroupRepository.getInstance();
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

  // ------------------------  Defensive Copying  ------------------------

  private Task createDetachedCopy(Task original) {
    return new Task(original);
  }

  private Group createDetachedCopy(Group original) {
    return new Group(original);
  }

  private User createDetachedCopy(User original) {
    return new User(original);
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
    User u = userRepository.add(user);
    notifier.notifyObservers();
    return u;
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

  /**
   * Filters the list of users based on the provided filter condition and returns a list of detached
   * user copies.
   *
   * @param filterCondition the condition to filter users; a predicate that determines whether a
   *     user should be included in the result
   * @return a list of users that satisfy the filter condition with each user as a detached copy
   */
  public List<User> filterUsers(Predicate<User> filterCondition) {
    return userRepository.getAll().filter(filterCondition).map(this::createDetachedCopy).toList();
  }

  /**
   * Retrieves a list of users based on the provided list of user IDs.
   *
   * @param userIds a list of user IDs to find matching users
   * @return a list of User objects whose IDs match the provided user IDs
   */
  public List<User> getUsersByIds(List<UUID> userIds) {
    return userRepository.getAll().filter(user -> userIds.contains(user.getId())).toList();
  }

  /**
   * Retrieves the usernames of the users corresponding to the provided list of unique user IDs.
   *
   * @param userIds a list of UUIDs representing the unique identifiers of the users, whose
   *     usernames need to be retrieved; must not be null
   * @return a list of strings containing the usernames of the users whose IDs match the provided
   *     list; if no matches are found, returns an empty list
   */
  public List<String> getUserNamesByIds(List<UUID> userIds) {
    return userRepository
        .getAll()
        .filter(user -> userIds.contains(user.getId()))
        .map(User::getUsername)
        .toList();
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
    Task t = taskRepository.update(task);
    notifier.notifyObservers();
    return t;
  }

  /**
   * Deletes a task from the task repository using its unique identifier.
   *
   * @param taskId the unique identifier of the task to be deleted; must not be null
   */
  public boolean removeTask(UUID taskId) {
    Objects.requireNonNull(taskId);
    boolean status = taskRepository.remove(taskId);
    notifier.notifyObservers();
    return status;
  }

  /**
   * Adds a new task to the repository.
   *
   * @param task the Task object to be added
   * @return the added Task object
   */
  public Task addTask(Task task) {
    Objects.requireNonNull(task);
    Task t = taskRepository.add(task);
    notifier.notifyObservers();
    return t;
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
    boolean isAssigned = taskRepository.assignTaskToUser(taskId, userId);
    notifier.notifyObservers();
    return isAssigned;
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

  /**
   * Retrieves a list of active tasks associated with a specific group. Active tasks are tasks that
   * are not in the CLOSED status. If the group does not exist or no tasks are found, an empty list
   * is returned.
   *
   * @param groupId the unique identifier (UUID) of the group whose active tasks are to be
   *     retrieved; must not be null
   * @return a list of Task objects that are active and belong to the specified group; returns an
   *     empty list if no tasks meet the criteria or the group does not exist
   */
  public List<Task> getActiveTasksByGroupId(UUID groupId) {
    Group group = groupRepository.getById(groupId).orElse(null);
    if (group == null) {
      return List.of();
    }
    return taskRepository.getActiveTasksByUserId(groupId).map(this::createDetachedCopy).toList();
  }

  private Predicate<Task> filterTasksForCurrentWeek(LocalDate startOfWeek) {
    Objects.requireNonNull(startOfWeek);
    Predicate<Task> beforeThisWeek = task -> task.getDeadline().isBefore(startOfWeek);
    Predicate<Task> afterThisWeek = task -> task.getDeadline().isAfter(startOfWeek.plusDays(6));
    return Predicate.not(beforeThisWeek).and(Predicate.not(afterThisWeek));
  }

  // ------------------------  Group Repository  ------------------------

  /**
   * Updates an existing group in the repository. Notifies observers after a successful update.
   *
   * @param group the Group object to be updated; must not be null
   * @return the updated Group object
   */
  public Group updateGroup(Group group) {
    Objects.requireNonNull(group);
    Group g = groupRepository.update(group);
    notifier.notifyObservers();
    return g;
  }

  /**
   * Retrieves a list of groups that a specific user is associated with.
   *
   * @param userId the unique identifier (UUID) of the user whose groups are to be retrieved; must
   *     not be null
   * @return a list of Group objects associated with the specified user; if no groups are found,
   *     returns an empty list
   */
  public List<Group> getGroupsByUserId(UUID userId) {
    return groupRepository.getGroupsByUserId(userId).map(this::createDetachedCopy).toList();
  }

  /**
   * Adds a new group to the repository and notifies observers of the change.
   *
   * @param group the Group object to be added; must not be null
   * @return the added Group object
   */
  public Group addGroup(Group group) {
    Group g = groupRepository.add(group);
    notifier.notifyObservers();
    return g;
  }
}
