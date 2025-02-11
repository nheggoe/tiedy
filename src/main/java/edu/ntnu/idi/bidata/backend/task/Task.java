package edu.ntnu.idi.bidata.backend.task;

/**
 * The Task class represents a task with a description and a priority level.
 * It provides methods to retrieve and update the task's description and priority.
 *
 * @author Nick Heggø
 * @version 2025.02.11
 */
public class Task {
  private String description;
  private Priority priority;

  public Task(String description) {
    setDescription(description);
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
   * or blank. If a null or blank value is provided, an IllegalArgumentException
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
