package edu.ntnu.idi.bidata.tiedy.backend.task;

/**
 * @author Nick Heggø
 * @version 2025.03.13
 */
public class TaskBuilder {

  private Task task;

  public TaskBuilder() {
    reset();
  }

  public TaskBuilder(Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }
    this.task = task;
  }

  public void setTitle(String title) {
    task.setTitle(title);
  }

  public void setDescription(String description) {
    task.setDescription(description);
  }

  public void setPriority(Priority priority) {
    task.setPriority(priority);
  }

  public void setStatus(Status status) {
    task.setStatus(status);
  }

  public Task getTask() {
    assertTaskInfo();
    Task taskToReturn = task;
    reset();
    return taskToReturn;
  }

  private void reset() {
    task = new Task();
  }

  private void assertTaskInfo() {
    if (task.getTitle() == null || task.getTitle().isBlank()) {
      throw new IllegalStateException("Task title cannot be null or blank");
    }
    if (task.getPriority() == null) {
      throw new IllegalStateException("Task priority cannot be null");
    }
    if (task.getStatus() == null) {
      throw new IllegalStateException("Task status cannot be null");
    }
  }
}
