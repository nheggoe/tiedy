package edu.ntnu.idi.bidata.tiedy.backend;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.DataRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonGroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonTaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

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

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  private DataAccessFacade() {
    groupRepository = JsonGroupRepository.getInstance();
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
    List.of(groupRepository, userRepository, taskRepository).forEach(DataRepository::saveChanges);
    LOGGER.info(() -> LocalDateTime.now() + " Application state saved");
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

  // ------------------------  Group Repository Methods  ------------------------

  /**
   * Finds all groups that a specific user is a member of by their unique identifier.
   *
   * @param userId the UUID of the user whose groups are to be retrieved
   * @return a list of Group objects that the user is a member of; if no groups are found, an empty
   *     list is returned
   */
  public List<Group> findGroupsByUserId(UUID userId) {
    return groupRepository.findAllByUserId(userId).map(this::createDetachedCopy).toList();
  }

  /**
   * Retrieves a list of groups where the specified user is an admin.
   *
   * @param userId the unique identifier (UUID) of the user
   * @return a list of Group objects where the user has admin privileges; returns an empty list if
   *     the user is not an admin in any group
   */
  public List<Group> findByAdmin(UUID userId) {
    return groupRepository.findByAdmin(userId).map(this::createDetachedCopy).toList();
  }

  /**
   * Adds a user to a group with the specified membership and admin status.
   *
   * @param groupId the unique identifier of the group
   * @param userId the unique identifier of the user to be added
   * @param isAdmin a boolean indicating whether the user should be granted admin privileges
   * @return true if the user was successfully added to the group, false otherwise
   */
  public boolean addMember(UUID groupId, UUID userId, boolean isAdmin) {
    return groupRepository.addMember(groupId, userId, isAdmin);
  }

  /**
   * Removes a user from a specified group.
   *
   * @param groupId the unique identifier of the group
   * @param userId the unique identifier of the user to be removed from the group
   * @return true if the user was successfully removed, false otherwise
   */
  public boolean removeMember(UUID groupId, UUID userId) {
    return groupRepository.removeMember(groupId, userId);
  }

  /**
   * Updates the admin status of a user in a specific group.
   *
   * @param groupId the unique identifier of the group
   * @param userId the unique identifier of the user whose admin status is to be updated
   * @param isAdmin the new admin status for the user (true to grant admin privileges, false to
   *     revoke)
   * @return true if the admin status was successfully updated, false otherwise
   */
  public boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin) {
    return groupRepository.updateMemberAdminStatus(groupId, userId, isAdmin);
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
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      return null;
    }

    return userRepository.add(user);
  }

  /**
   * Retrieves a User by their username.
   *
   * @param username the username to search for; must not be null or blank
   * @return an Optional containing the User if found; otherwise, an empty Optional
   */
  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
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
    return taskRepository.update(task);
  }

  /**
   * Deletes a task from the task repository using its unique identifier.
   *
   * @param taskId the unique identifier of the task to be deleted; must not be null
   */
  public boolean removeTask(UUID taskId) {
    return taskRepository.remove(taskId);
  }

  /**
   * Adds a new task to the repository.
   *
   * @param task the Task object to be added
   * @return the added Task object
   */
  public Task addTask(Task task) {
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
    return taskRepository.findByAssignedUser(userId).map(this::createDetachedCopy).toList();
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
    return taskRepository
        .findByAssignedUser(userId)
        .filter(task -> task.getStatus() != Status.CLOSED)
        .map(this::createDetachedCopy)
        .toList();
  }

  public List<Task> getActiveTasksByUserIdAndStatus(UUID userId, Status status) {
    return taskRepository
        .findByAssignedUser(userId)
        .filter(task -> task.getStatus() != Status.CLOSED)
        .filter(task -> task.getStatus() == status)
        .map(this::createDetachedCopy)
        .toList();
  }

  public List<Task> getActiveTasksByUserIdAndPriority(UUID userId, Priority priority) {
    return taskRepository
        .findByAssignedUser(userId)
        .filter(task -> task.getStatus() != Status.CLOSED)
        .filter(task -> task.getPriority() == priority)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Assigns a task to a user.
   *
   * @param taskId the unique identifier of the task to be assigned
   * @param userId the unique identifier of the user to whom the task is to be assigned
   */
  public boolean assignTaskToUser(UUID taskId, UUID userId) {
    return taskRepository.assignToUser(taskId, userId);
  }

  /**
   * Unassigns a task from a user.
   *
   * @param taskId the unique identifier of the task to be unassigned
   * @param userId the unique identifier of the user from whom the task is to be unassigned
   * @return true if the task was successfully unassigned from the user, false otherwise
   */
  public boolean unassignTaskFromUser(UUID taskId, UUID userId) {
    return taskRepository.unassignFromUser(taskId, userId);
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
    return taskRepository
        .findByAssignedUser(userId)
        .filter(task -> task.getStatus() == status)
        .map(this::createDetachedCopy)
        .toList();
  }

  /**
   * Retrieves a list of tasks assigned to a specific user and filtered by the specified priority
   * level.
   *
   * @param userId the unique identifier of the user whose tasks are to be retrieved
   * @param priority the priority level to filter tasks (e.g., HIGH, MEDIUM, LOW)
   * @return a list of Task objects assigned to the specified user that match the given priority; an
   *     empty list if no tasks meet the criteria
   */
  public List<Task> getTasksByUserAndPriority(UUID userId, Priority priority) {
    return taskRepository
        .findByAssignedUser(userId)
        .filter(task -> task.getPriority() == priority)
        .map(this::createDetachedCopy)
        .toList();
  }
}
