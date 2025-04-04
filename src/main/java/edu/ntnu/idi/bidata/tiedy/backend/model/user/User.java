package edu.ntnu.idi.bidata.tiedy.backend.model.user;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.util.MapUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The object that represents the customers of the application.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.03.28
 */
public class User {

  private static final String DEFAULT_LIST_NAME = "Reminders";

  private final UUID id;
  private final LocalDateTime createdAt;
  private final Map<String, Set<UUID>> taskMap; // K: listName, V: Set<Task ID>

  private String username;
  private String password;

  private User() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.taskMap = new HashMap<>();
  }

  public User(String username, String password) {
    this();
    setUsername(username);
    setPassword(password);
  }

  // ------------------------   Public Interface  ------------------------

  /**
   * Retrieves the default set of tasks associated with the user. If no default task set exists, a
   * new, empty set is created and added to the task map under the default list name.
   *
   * @return an immutable copy of the default set of task UUIDs
   */
  public Set<UUID> getDefaultTaskSet() {
    return Set.copyOf(
        taskMap.computeIfAbsent(MapUtil.generateMapKey(DEFAULT_LIST_NAME), k -> new HashSet<>()));
  }

  /**
   * Adds a task to the default set of tasks associated with the user. If the default task set does
   * not exist, it creates a new set and associates it under the default list name before adding the
   * task's unique identifier.
   *
   * @param task the task to be added to the default set; must not be null
   */
  public void addTaskDefaultSet(Task task) {
    taskMap
        .computeIfAbsent(MapUtil.generateMapKey(DEFAULT_LIST_NAME), k -> new HashSet<>())
        .add(task.getId());
  }

  // ------------------------   Getters and Setters ------------------------

  public UUID getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Map<String, Set<UUID>> getTaskMap() {
    return taskMap;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be blank!");
    }
    this.username = username.strip();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    PasswordUtil.validatePasswordStrength(password);
    PasswordUtil.validatePasswordFormat(password);
    this.password = password;
  }

  // ------------------------  Overrides  ------------------------

  @Override
  public String toString() {
    return "User{id=%s, createdAt=%s, taskLists=%s, username='%s', password='%s'}"
        .formatted(id, createdAt, taskMap, username, password);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) {
      return false;
    }
    return id.equals(user.id) && createdAt.equals(user.createdAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + createdAt.hashCode();
    return result;
  }
}
