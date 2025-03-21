package edu.ntnu.idi.bidata.tiedy.backend.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.util.MapUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The object that represents the customers of the application.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.03.19
 */
public class User {

  @JsonInclude(JsonInclude.Include.ALWAYS)
  private final Map<String, List<Task>> taskLists;

  @JsonInclude private final Map<Group, Boolean> groupRoles;

  private final String id = UUID.randomUUID().toString();
  private final LocalDateTime createdAt = LocalDateTime.now();

  private String username;
  private String password;
  private String email;

  private User() {
    taskLists = new HashMap<>();
    groupRoles = new HashMap<>();
    taskLists.put("reminders", new ArrayList<>());
  }

  /**
   * Constructs a new User instance with the specified username and password.
   *
   * @param username the username for the new user
   * @param password the password for the new user, which will be validated and hashed
   */
  public User(String username, String password) {
    this();
    setUsername(username);
    setPassword(password);
  }

  /**
   * Constructor to create users with the given username, password and email address.
   *
   * @param username the name of the user
   * @param plainTextPassword the password user has entered
   * @param email the email address of the user
   */
  public User(String username, String plainTextPassword, String email) {
    this();
    setUsername(username);
    setPassword(plainTextPassword);
    setEmail(email);
  }

  /**
   * Adds a task to the default task list named "reminders". If the "Reminders" list does not exist
   * in the task lists map, it creates the list and adds the task.
   *
   * @param task the Task object to be added to the "reminders" list
   */
  public boolean addTask(Task task) {
    return taskLists
        .computeIfAbsent(MapUtil.generateMapKey("Reminders"), k -> new ArrayList<>())
        .add(task);
  }

  /**
   * Adds a task to a specified task list. If the specified task list does not exist in the user's
   * task lists, a new list is created and the task is added to it.
   *
   * @param listName the name of the task list to which the task should be added
   * @param tasks the Task object to be added to the specified task list
   */
  public void addTaskList(String listName, Task tasks) {
    taskLists.computeIfAbsent(MapUtil.generateMapKey(listName), k -> new ArrayList<>()).add(tasks);
  }

  /**
   * Retrieves a list of tasks associated with the specified task list name. If the task list does
   * not exist in the user's task lists, an empty list is returned.
   *
   * @param listName the name of the task list to retrieve tasks from
   * @return a list of Task objects from the specified task list; returns an empty list if the task
   *     list does not exist
   */
  public List<Task> getTaskLists(String listName) {
    String listNameKey = MapUtil.generateMapKey(listName);
    if (!taskLists.containsKey(MapUtil.generateMapKey(listNameKey))) {
      return List.of();
    }
    return List.copyOf(taskLists.get(MapUtil.generateMapKey(listNameKey)));
  }

  /**
   * Retrieves the unique identifier of the user.
   *
   * @return the unique identifier of the user as a String.
   */
  public String getId() {
    return id;
  }

  /**
   * Retrieves the username associated with the user.
   *
   * @return the username as a String
   */
  public String getUsername() {
    return username;
  }

  /**
   * Updates the username of the user.
   *
   * @param username the new username to set for the user
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Retrieves the password associated with the user.
   *
   * @return the password of the user as a String
   */
  public String getPassword() {
    return password;
  }

  @JsonSetter("password")
  private void setHashedPassword(String hashedPassword) {
    this.password = hashedPassword;
  }

  /**
   * Sets the user's password after validating its strength and format. The password is securely
   * hashed before being stored.
   *
   * @param plainTextPassword the plain-text password to be validated and hashed
   * @throws IllegalArgumentException if the password is null, blank or does not meet strength or
   *     format requirements
   */
  public void setPassword(String plainTextPassword) {
    validatePasswordStrength(plainTextPassword);
    validatePasswordFormat(plainTextPassword);
    this.password = PasswordUtil.hashPassword(plainTextPassword);
  }

  /**
   * Retrieves the email address associated with the user.
   *
   * @return the email address as a String
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address for the user. The email format will be validated before it is updated.
   *
   * @param email the email address to set for the user
   * @throws IllegalArgumentException if the provided email address does not match the standard
   *     email format
   */
  public void setEmail(String email) {
    validateEmailFormat(email);
    this.email = email;
  }

  /**
   * Retrieves the creation timestamp for this user.
   *
   * @return the creation date and time as a {@link LocalDateTime} object.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Validates if the provided email address adheres to a standard email format.
   *
   * @param emailAddress the email address to be validated
   */
  private void validateEmailFormat(String emailAddress) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (emailAddress != null && !pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }

  /**
   * Validates the strength of a given plain-text password. Ensures the password meets certain
   * criteria, including being non-null, not blank, at least 8 characters long and containing at
   * least one lowercase letter.
   *
   * @param plainTextPassword the plain-text password to be validated
   * @throws IllegalArgumentException if the password is null, blank, shorter than 8 characters or
   *     does not contain at least one lowercase letter
   */
  private void validatePasswordStrength(String plainTextPassword) {
    if (plainTextPassword == null || plainTextPassword.isBlank()) {
      throw new IllegalArgumentException("Password cannot be blank!");
    }
    if (plainTextPassword.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long!");
    }
    if (!plainTextPassword.matches(".*[a-z].*")) {
      throw new IllegalArgumentException("Password must contain at least one lowercase letter!");
    }
  }

  /**
   * Validates the format of a provided plain-text password to ensure it does not contain any
   * special characters and is not blank or null.
   *
   * @param plainTextPassword the plain-text password to be validated
   * @throws IllegalArgumentException if the password is null, blank or contains invalid special
   *     characters
   */
  private void validatePasswordFormat(String plainTextPassword) {
    String invalidChars = "(){}[]|`¬¦!'£%^&*\"<>:;#~\\_-+=,@ \t";
    if (plainTextPassword == null || plainTextPassword.isBlank()) {
      throw new IllegalArgumentException("Password cannot be blank!");
    }
    for (char c : plainTextPassword.toCharArray()) {
      if (invalidChars.contains(String.valueOf(c))) {
        throw new IllegalArgumentException("Password cannot contain special characters!");
      }
    }
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) {
      return false;
    }

    return Objects.equals(taskLists, user.taskLists)
        && Objects.equals(groupRoles, user.groupRoles)
        && Objects.equals(id, user.id)
        && createdAt.equals(user.createdAt)
        && Objects.equals(username, user.username)
        && Objects.equals(password, user.password)
        && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(taskLists);
    result = 31 * result + Objects.hashCode(groupRoles);
    result = 31 * result + Objects.hashCode(id);
    result = 31 * result + createdAt.hashCode();
    result = 31 * result + Objects.hashCode(username);
    result = 31 * result + Objects.hashCode(password);
    result = 31 * result + Objects.hashCode(email);
    return result;
  }
}
