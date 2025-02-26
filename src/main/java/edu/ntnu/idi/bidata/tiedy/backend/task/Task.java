package edu.ntnu.idi.bidata.tiedy.backend.task;

import java.util.Date;

/**
 * The Task class represents a task with a description and a priority level.
 * It provides methods to retrieve and update the task's description and priority.
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
  private Date deadline;
  private Priority priority;

  public Task(int id, String title, String description, Status status, User assignedTo, Date deadline, Priority priority) {

    this.id = id;
    this.title = title;
    this.setDescription(description);
    this.status = status;
    this.assignedTo = assignedTo;
    this.deadline = deadline;
    this.setPriority(priority);
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
   * Sets the description for the task. The description must not be null
   * or blank. If a null or bl  ank value is provided, an IllegalArgumentException
   * is thrown.
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
   */
  public void setPriority(Priority priority) {
    this.priority = priority;
  }
}
