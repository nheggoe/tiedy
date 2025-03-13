package edu.ntnu.idi.bidata.tiedy.backend.user;

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
 * @version 2025.03.13
 */
public class User {

  private final Map<String, List<Task>> taskLists;
  private final String id = UUID.randomUUID().toString();
  private final LocalDateTime createdAt = LocalDateTime.now();

  private String username;
  private String password;
  private String email;

  private User() {
    taskLists = new HashMap<>();
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
  public void addTask(Task task) {
    taskLists
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

  public void setPassword(String plainTextPassword) {
    validatePasswordStrength(plainTextPassword);
    validatePasswordFormat(plainTextPassword);
    this.password = PasswordUtil.hashPassword(plainTextPassword);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    validateEmailFormat(email);
    this.email = email;
  }

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
    if (emailAddress == null) {
      throw new NullPointerException("email address cannot be null!");
    }
    if (!pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }

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
        && id.equals(user.id)
        && username.equals(user.username)
        && password.equals(user.password)
        && Objects.equals(email, user.email)
        && createdAt.equals(user.createdAt);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(taskLists);
    result = 31 * result + id.hashCode();
    result = 31 * result + username.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + Objects.hashCode(email);
    result = 31 * result + createdAt.hashCode();
    return result;
  }
}
