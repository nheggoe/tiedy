package edu.ntnu.idi.bidata.tiedy.backend.task;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Task class represents a task with values like id, title, description, status, who it`s
 * assigned to, deadline and priority. It provides methods to retrieve and update the task's values.
 *
 * @author Nick Heggø and Ida Løvås
 * @version 2025.03.13
 */
public class Task {

  private final List<User> assignedUsers = new ArrayList<>();
  private final String id = UUID.randomUUID().toString();
  private final LocalDateTime createdAt = LocalDateTime.now();

  private Status status = Status.OPEN;
  private Priority priority = Priority.NONE;

  private String title;
  private String description;
  private LocalDate deadline;

  public Task() {}

  public Task(String title, String description) {
    setTitle(title);
    setDescription(description);
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
   * Retrieves the status of the task
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
  public boolean isOverdue() {
    return deadline.isBefore(LocalDate.now());
  }

  /**
   * Retrieves the date for deadline for task completion
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
    if (deadline == null) {
      throw new IllegalArgumentException("Deadline cannot be null");
    }
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
}
