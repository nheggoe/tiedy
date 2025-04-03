package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The Task class represents a task with values like id, title, description, status, who it's
 * assigned to, deadline and priority. It provides methods to retrieve and update the task's values.
 *
 * @author Nick Heggø and Ida Løvås
 * @version 2025.03.28
 */
public class Task {

  private final UUID id;
  private final LocalDateTime createdAt;
  private final Set<UUID> assignedUsers;

  private String title;
  private String description;
  private LocalDate deadline;

  private Status status;
  private Priority priority;

  /**
   * Default constructor for the Task class. Initializes a new Task instance with a unique
   * identifier, the current timestamp for creation, and default values for its fields.
   *
   * <p>The id is generated using a universally unique identifier (UUID). The creation timestamp is
   * assigned using the current system time. The assignedUsers set is initialized to an empty set.
   * The status is set to Status.OPEN, and the priority is set to Priority.NONE.
   */
  public Task() {
    this.id = UUID.randomUUID();
    this.createdAt = LocalDateTime.now();
    this.assignedUsers = new HashSet<>();
    this.status = Status.OPEN;
    this.priority = Priority.NONE;
  }

  // ------------------------   Public Interface  ------------------------

  public void addAssignedUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null!");
    }
    assignedUsers.add(user.getId());
  }

  // ------------------------   Getters and Setters ------------------------

  public UUID getId() {
    return id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Set<UUID> getAssignedUsers() {
    return Set.copyOf(assignedUsers);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("Task title cannot be blank!");
    }
    this.title = title.strip();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = (description == null) ? "" : description.strip();
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    if (deadline == null) {
      throw new IllegalArgumentException("Deadline cannot be null!");
    }
    this.deadline = deadline;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null!");
    }
    this.status = status;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    if (priority == null) {
      throw new IllegalArgumentException("Priority cannot be null!");
    }
    this.priority = priority;
  }

  @Override
  public String toString() {
    return "Task{id=%s, createdAt=%s, assignedUsers=%s, title='%s', description='%s', deadline=%s, status=%s, priority=%s}"
        .formatted(id, createdAt, assignedUsers, title, description, deadline, status, priority);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Task task)) {
      return false;
    }
    return id.equals(task.id) && createdAt.equals(task.createdAt);
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + createdAt.hashCode();
    return result;
  }
}
