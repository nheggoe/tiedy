package edu.ntnu.idi.bidata.tiedy.backend.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Task class represents a task with values like id, title, description, status, who it's
 * assigned to, deadline and priority. It provides methods to retrieve and update the task's values.
 *
 * @author Nick Heggø and Ida Løvås
 * @version 2025.03.24
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

  private List<User> assignedUsers;
  private String id;
  private LocalDateTime createdAt;

  private Status status = Status.OPEN;
  private Priority priority = Priority.NONE;

  private String title;
  private String description;
  private LocalDate deadline;

  /**
   * Default constructor for the Task class. This constructor initializes an empty instance of the
   * Task with default values for its fields. It can be used as a starting point to configure the
   * task's properties through its setter methods.
   */
  public Task() {}

  /**
   * Constructs a new Task instance with the specified title and description. This constructor
   * initializes the task's title and description, sets the creation date to the current date and
   * time, generates a unique task ID and prepares an empty list for assigned users.
   *
   * @param title the title of the task; must not be null or blank
   * @param description the description of the task; must not be null or blank
   * @throws IllegalArgumentException if the title or description is null or blank
   */
  public Task(User user, String title, String description) {
    id = UUID.randomUUID().toString();
    createdAt = LocalDateTime.now();
    assignedUsers = new ArrayList<>();
    assignedUsers.add(user);
    setTitle(title);
    setDescription(description);
  }

  @Override
  public String toString() {
    return "Task{"
        + "assignedUsers="
        + assignedUsers
        + ", id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + '}';
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Retrieves the unique identifier of the task.
   *
   * @return the unique identifier of the task as a String.
   */
  public String getId() {
    return id;
  }

  /**
   * Retrieves the title of the task.
   *
   * @return the title of the task as a String.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title for the task. The title must not be null or blank. If a null or blank value is
   * provided, an IllegalArgumentException is thrown.
   *
   * @param title the new title for the task
   * @throws IllegalArgumentException if the title is null or blank
   */
  public void setTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Title cannot be blank!");
    }
    this.title = title;
  }

  /**
   * Retrieves the description of the task.
   *
   * @return the description of the task as a String.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description for the task. The description must not be null or blank. If a null or
   * blank value is provided, an IllegalArgumentException is thrown.
   *
   * @param description the new description for the task
   * @throws IllegalArgumentException if the description is null or blank
   */
  public void setDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Task description cannot be blank!");
    }
    this.description = description;
  }

  /**
   * Retrieves the status of the task.
   *
   * @return status of the task
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Sets the status of the task. The provided status must not be null. If a null value is supplied,
   * an IllegalArgumentException is thrown.
   *
   * @param status the new status for the task, represented as a Status enum value
   * @throws IllegalArgumentException if the status is null
   */
  public void setStatus(Status status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null");
    }
    this.status = status;
  }

  /**
   * Retrieves an unmodifiable list of users assigned to the task.
   *
   * @return a List of User objects currently assigned to the task.
   */
  public List<User> getAssignedUsers() {
    return List.copyOf(assignedUsers);
  }

  /**
   * Adds a user to the list of users assigned to this task.
   *
   * @param assignedTo the user to be added to the assigned users of this task
   */
  public void addAssignedUser(User assignedTo) {
    assignedUsers.add(assignedTo);
  }

  /**
   * Determines whether the task is overdue by comparing its deadline with the current date.
   *
   * @return true if the task's deadline is before the current date, false otherwise
   */
  @JsonIgnore
  public boolean isOverdue() {
    return deadline.isBefore(LocalDate.now());
  }

  /**
   * Retrieves the date for the deadline for task completion.
   *
   * @return date for deadline
   */
  public LocalDate getDeadline() {
    return deadline;
  }

  /**
   * Updates the deadline for the task. The deadline must not be null and should not be a date in
   * the past. If a null or past date is provided, an IllegalArgumentException will be thrown.
   *
   * @param deadline the new deadline for the task, represented as a LocalDate object
   * @throws IllegalArgumentException if the deadline is null or a past date
   */
  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  /**
   * Retrieves the priority of the task.
   *
   * @return the priority of the task as a Priority enum value.
   */
  public Priority getPriority() {
    return priority;
  }

  /**
   * Sets the priority for the task.
   *
   * @param priority the new priority for the task, represented as a Priority enum value
   * @throws IllegalArgumentException if the priority is null
   */
  public void setPriority(Priority priority) {
    if (priority == null) {
      throw new IllegalArgumentException("Priority cannot be null");
    }
    this.priority = priority;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Task task)) return false;

    return id.equals(task.id) && createdAt.equals(task.createdAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + createdAt.hashCode();
    return result;
  }
}
