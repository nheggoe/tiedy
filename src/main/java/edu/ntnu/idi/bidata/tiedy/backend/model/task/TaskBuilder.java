package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import java.time.LocalDate;

/**
 * The TaskBuilder class is a utility class for creating and configuring instances of the {@link
 * Task} class. It provides methods to set various properties of a task, such as title, description,
 * priority, and status. The builder ensures that the task complies with necessary constraints
 * before it can be retrieved.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class TaskBuilder {

  private Task task;

  /**
   * Constructs a new TaskBuilder instance and initializes a new Task object. This constructor sets
   * up the TaskBuilder in its default state, allowing the creation and configuration of a new task
   * using its methods.
   */
  public TaskBuilder() {
    reset();
  }

  /**
   * Sets the title of the task being built. The title must not be null or blank. If a null or blank
   * value is provided, an IllegalArgumentException will be thrown by the underlying Task class.
   *
   * @param title the new title to assign to the task
   * @throws IllegalArgumentException if the title is null or blank
   */
  public TaskBuilder title(String title) {
    task.setTitle(title);
    return this;
  }

  /**
   * Sets the description of the task being built. The provided description must not be null or
   * blank. If a null or blank value is provided, an IllegalArgumentException will be thrown by the
   * underlying {@code Task} class.
   *
   * @param description the new description to assign to the task
   * @throws IllegalArgumentException if the description is null or blank
   */
  public TaskBuilder description(String description) {
    task.setDescription(description);
    return this;
  }

  /**
   * Sets the priority of the task being built. The priority determines the importance level of the
   * task and should be provided as a {@link Priority} enum value.
   *
   * @param priority the priority to assign to the task, represented as a {@link Priority} enum
   *     value
   * @throws IllegalArgumentException if the priority is null
   */
  public TaskBuilder priority(Priority priority) {
    task.setPriority(priority);
    return this;
  }

  /**
   * Sets the status of the task being built.
   *
   * @param status the status to assign to the task, represented as a {@link Status} enum value
   * @throws IllegalArgumentException if the status is null
   */
  public TaskBuilder status(Status status) {
    task.setStatus(status);
    return this;
  }

  /**
   * Sets the deadline for the task being built. The deadline must not be null and must not be a
   * date in the past. If a null or past date is provided, the underlying Task class will throw an
   * IllegalArgumentException.
   *
   * @param deadline the new deadline to assign to the task, represented as a LocalDate object
   * @throws IllegalArgumentException if the deadline is null or a past date
   */
  public TaskBuilder deadline(LocalDate deadline) {
    task.setDeadline(deadline);
    return this;
  }

  /**
   * Retrieves the current task being built by the TaskBuilder. This method ensures that the task is
   * valid by performing necessary checks on its properties before returning the task. Once the task
   * is retrieved, the TaskBuilder is reset to its initial state to allow for the creation of a new
   * task.
   *
   * @return the completed Task object with validated properties
   * @throws IllegalStateException if required fields of the task (title, priority, or status) are
   *     missing or invalid
   */
  public Task build() {
    assertTaskInfo();
    Task taskToReturn = task;
    reset();
    return taskToReturn;
  }

  private void reset() {
    this.task = new Task();
  }

  private void assertTaskInfo() {
    if (task.getTitle() == null || task.getTitle().isBlank()) {
      throw new IllegalArgumentException("Task title cannot be null or blank");
    }
    if (task.getPriority() == null) {
      throw new IllegalArgumentException("Task priority cannot be null");
    }
    if (task.getStatus() == null) {
      throw new IllegalArgumentException("Task status cannot be null");
    }
  }
}
