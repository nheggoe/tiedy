package edu.ntnu.idi.bidata.tiedy.backend.task;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.time.LocalDate;

/**
 * The Task class represents a task with values like id, title, description, status, who it`s
 * assigned to, deadline and priority. It provides methods to retrieve and update the task's values.
 *
 * @author Nick Heggø and Ida Løvås
 * @version 2025.02.26
 */
public class Task {
  private int id;
  private String title;
  private String description;
  private Status status;
  private User assignedTo;
  private LocalDate deadline;
  private Priority priority;

  /**
   * Constructor to assign a task with these values
   *
   * @param id id to identify the task
   * @param title of the task
   * @param description of the task
   * @param status completion status for the task
   * @param assignedTo which user the task is assigned to
   * @param deadline date for completion of task
   * @param priority of the task
   */
  public Task(
      int id,
      String title,
      String description,
      Status status,
      User assignedTo,
      LocalDate deadline,
      Priority priority) {

    this.setId(id);
    this.setTitle(title);
    this.setDescription(description);
    this.setStatus(status);
    this.setAssignedTo(assignedTo);
    this.setDeadline(deadline);
    this.setPriority(priority);
  }

  /**
   * Accessor for task id
   *
   * @return id of the task
   */
  public int getId() {
    return id;
  }

  /**
   * Mutator for task id
   *
   * @param id of the task
   * @throws IllegalArgumentException if task id is less or equal to 0
   */
  public void setId(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Task id need to be a positive number");
    }
    this.id = id;
  }

  /**
   * Accessor for task title
   *
   * @return title of task
   */
  public String getTitle() {
    return title;
  }

  /**
   * Mutator for title
   *
   * @param title of task
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
   * Sets the description for the task. The description must not be null or blank. If a null or bl
   * ank value is provided, an IllegalArgumentException is thrown.
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
   * Sets the status for the task
   *
   * @param status of the task
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * Retrieves which user the task is assigned to
   *
   * @return user task is assigned to
   */
  public User getAssignedTo() {
    return assignedTo;
  }

  /**
   * Sets which user the task is assigned to
   *
   * @param assignedTo user task is assigned to
   */
  public void setAssignedTo(User assignedTo) {
    this.assignedTo = assignedTo;
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
   * Sets the deadline of a task
   *
   * @param deadline for task
   * @throws IllegalArgumentException if deadline is null or in the past
   */
  public void setDeadline(LocalDate deadline) {
    if (deadline == null) {
      throw new IllegalArgumentException("Deadline cannot be null");
    }
    if (deadline.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Deadline cannot be in the past");
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
