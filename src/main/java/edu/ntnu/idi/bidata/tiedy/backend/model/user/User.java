package edu.ntnu.idi.bidata.tiedy.backend.model.user;

import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * The object that represents the customers of the application.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.04.12
 */
public class User {

  private final UUID id;
  private final LocalDateTime createdAt;
  private final LevelSystem levelSystem;

  private String username;
  private String hashedPassword;

  private User() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.levelSystem = new LevelSystem();
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

  /**
   * Constructs a new User instance as a deep copy of another User instance. All fields, including
   * the {@code levelSystem} object, are copied to ensure that the new User instance is independent
   * of the original instance.
   *
   * @param other the User instance to copy; must not be null
   */
  public User(User other) {
    Objects.requireNonNull(other);
    this.id = other.id;
    this.createdAt = other.createdAt;
    this.levelSystem = new LevelSystem(other.levelSystem);
    this.username = other.username;
    this.hashedPassword = other.hashedPassword;
  }

  // ------------------------  Overrides  ------------------------

  @Override
  public String toString() {
    return "User{id=%s, createdAt=%s, username='%s', password='%s'}"
        .formatted(id, createdAt, username, hashedPassword);
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
   * Marks a task as completed for the user by delegating the operation to the user's level system.
   * This method increments the user's experience, total experience, and the number of completed
   * tasks based on the internal logic of the level system. It also handles any potential leveling
   * up if the criteria are met.
   *
   * @return true if completing the task results in the user leveling up, false otherwise
   */
  public boolean completeTask() {
    return levelSystem.completeTask();
  }

  public int getCurrentLevel() {
    return levelSystem.getCurrentLevel();
  }

  public int getCurrentExperience() {
    return levelSystem.getCurrentExperience();
  }

  public int getExperienceThreshold() {
    return levelSystem.getExperienceThreshold();
  }

  public int getCompletedTaskCount() {
    return levelSystem.getCompletedTaskCount();
  }

  // ------------------------  Getters and Setters  ------------------------

  public UUID getId() {
    return id;
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
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");
    if (!pattern.matcher(username).matches()) {
      throw new IllegalArgumentException(
          "Username must be alphanumeric and between 3 and 20 characters!");
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
    if (plainTextPassword == null || plainTextPassword.isBlank()) {
      throw new IllegalArgumentException("Password cannot be blank or weak!");
    }
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{8,20}$");
    if (!pattern.matcher(plainTextPassword).matches()) {
      throw new IllegalArgumentException(
          "Password must be alphanumeric and between 8 and 20 characters!");
    }
    this.hashedPassword = PasswordUtil.hashPassword(plainTextPassword);
  }
}
