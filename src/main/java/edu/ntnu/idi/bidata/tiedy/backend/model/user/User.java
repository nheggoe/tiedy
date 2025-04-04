package edu.ntnu.idi.bidata.tiedy.backend.model.user;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.util.MapUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
  private String hashedPassword;

  private User() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.taskMap = new HashMap<>();
  }

  /**
   * Constructs a new User instance with the specified username and plaintext password. The username
   * is validated and set, and the plaintext password is hashed and stored securely.
   *
   * @param username the username to be assigned to the user; must not be null or blank
   * @param plainTextPassword the user's plaintext password to be hashed; must not be null or weak
   * @throws IllegalArgumentException if the username is null or blank, or if the password is
   *     invalid
   */
  public User(String username, String plainTextPassword) {
    this();
    setUsername(username);
    setHashedPassword(plainTextPassword);
  }

  // ------------------------  Overrides  ------------------------

  @Override
  public String toString() {
    return "User{id=%s, createdAt=%s, taskLists=%s, username='%s', password='%s'}"
        .formatted(id, createdAt, taskMap, username, hashedPassword);
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

  // ------------------------  Getters and Setters  ------------------------

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

  /**
   * Sets the user's username after validating it. The username must not be null or blank. Leading
   * and trailing whitespace, if any, is stripped before assigning it.
   *
   * @param username the username to be set for the user; must not be null or blank
   * @throws IllegalArgumentException if the provided username is null or blank
   */
  public void setUsername(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be blank!");
    }
    this.username = username.strip();
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  /**
   * Sets the user's hashed password by securely hashing the provided plain-text password using the
   * {@code PasswordUtil.hashPassword} method. The hashed password is then stored in the {@code
   * hashedPassword} field of the user.
   *
   * @param plainTextPassword the plain-text password to be hashed and stored; must not be null,
   *     blank, or fail validation
   * @throws IllegalArgumentException if the provided password is null, blank, or invalid
   */
  public void setHashedPassword(String plainTextPassword) {
    this.hashedPassword = PasswordUtil.hashPassword(plainTextPassword);
  }
}
