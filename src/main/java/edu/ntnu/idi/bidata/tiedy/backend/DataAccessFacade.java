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
import java.time.LocalDate;
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
 * @version 2025.04.03
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

  public static synchronized DataAccessFacade getInstance() {
    if (instance == null) {
      instance = new DataAccessFacade();
    }
    return instance;
  }

  // ------------------------  Runnable  ------------------------

  @Override
  public void run() {
    List.of(groupRepository, userRepository, taskRepository).forEach(DataRepository::saveChanges);
    LOGGER.info(() -> LocalDateTime.now() + " Application state saved");
  }

  // ------------------------   Group Repository Methods ------------------------

  public List<Group> findAllByUserId(UUID userId) {
    return groupRepository.findAllByUserId(userId);
  }

  public List<Group> findByAdmin(UUID userId) {
    return groupRepository.findByAdmin(userId);
  }

  public boolean addMember(UUID groupId, UUID userId, boolean isAdmin) {
    return groupRepository.addMember(groupId, userId, isAdmin);
  }

  public boolean removeMember(UUID groupId, UUID userId) {
    return groupRepository.removeMember(groupId, userId);
  }

  public boolean updateMemberAdminStatus(UUID groupId, UUID userId, boolean isAdmin) {
    return groupRepository.updateMemberAdminStatus(groupId, userId, isAdmin);
  }

  // ------------------------  User Repository Methods  ------------------------

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> authenticate(String username, String password) {
    return userRepository.authenticate(username, password);
  }

  // ------------------------  Task Repository Methods  ------------------------

  public List<Task> findByAssignedUser(String userID) {
    return taskRepository.findByAssignedUser(userID);
  }

  public List<Task> findByStatus(Status status) {
    return taskRepository.findByStatus(status);
  }

  public List<Task> findByPriority(Priority priority) {
    return taskRepository.findByPriority(priority);
  }

  public List<Task> findByDeadLineBefore(LocalDate date) {
    return taskRepository.findByDeadLineBefore(date);
  }

  public boolean assignToUser(UUID taskId, UUID userId) {
    return taskRepository.assignToUser(taskId, userId);
  }

  public boolean unassignFromUser(UUID taskId, UUID userId) {
    return taskRepository.unassignFromUser(taskId, userId);
  }
}
