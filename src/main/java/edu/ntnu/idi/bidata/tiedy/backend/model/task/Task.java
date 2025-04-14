package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

/**
 * The Task class represents a task with values like id, title, description, status, who it's
 * assigned to, deadline and priority. It provides methods to retrieve and update the task's values.
 *
 * @author Nick Heggø and Ida Løvås
 * @version 2025.04.12
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
   * identifier, the current timestamp for creation and default values for its fields.
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

  /**
   * Constructs a new Task object by copying the properties of the given Task instance. This
   * performs a shallow copy of the provided Task's attributes, except {@code assignedUsers}, which
   * is created as a new HashSet instance containing the same UUIDs, ensuring immutability.
   *
   * @param other the Task instance to copy; must not be null
   */
  public Task(@NonNull Task other) {
    this.id = other.id;
    this.createdAt = other.createdAt;
    this.assignedUsers = new HashSet<>(other.assignedUsers); // shallow copy, but UUID is immutable
    this.title = other.title;
    this.description = other.description;
    this.deadline = other.deadline;
    this.status = other.status;
    this.priority = other.priority;
  }

  // ------------------------  Overrides  ------------------------

  @Override
  public String toString() {
    return "Task{id=%s, createdAt=%s, title='%s', description='%s'}"
        .formatted(id, createdAt, title, description);
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

  // ------------------------  Public Interface  ------------------------

  /**
   * Adds the specified user to the set of assigned users for the task. The user's unique identifier
   * is added to the {@code assignedUsers} set. If the provided user is {@code null}, an {@code
   * IllegalArgumentException} is thrown.
   *
   * @param user the user to assign to the task; must not be {@code null}
   * @throws IllegalArgumentException if the specified user is {@code null}
   */
  public void addAssignedUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null!");
    }
    assignedUsers.add(user.getId());
  }

  // ------------------------  Getters and Setters  ------------------------

  public UUID getId() {
    return id;
  }

  public Set<UUID> getAssignedUsers() {
    return Set.copyOf(assignedUsers);
  }

  /**
   * Assigns a user to the task by adding their unique identifier to the set of assigned users. If
   * the user is already assigned to the task, the method will return {@code false}.
   *
   * @param userId the unique identifier of the user to assign to the task; must not be {@code null}
   * @return {@code true} if the user was successfully assigned to the task, or {@code false} if the
   *     user was already assigned
   */
  public boolean assignUser(UUID userId) {
    if (assignedUsers.contains(userId)) {
      return false;
    }
    return assignedUsers.add(userId);
  }

  public String getTitle() {
    return title;
  }

  /**
   * Sets the title for this task. The title must not be null or blank. If the provided title is
   * null or blank, an IllegalArgumentException will be thrown. Leading and trailing whitespace will
   * be removed from the title before it is assigned.
   *
   * @param title the new title to assign to the task; must not be null or blank
   * @throws IllegalArgumentException if the title is null or blank
   */
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

  /**
   * Sets the deadline for this task. The specified deadline must not be null.
   *
   * @param deadline the deadline to assign to this task; must not be null
   * @throws IllegalArgumentException if the provided deadline is null
   */
  public void setDeadline(LocalDate deadline) {
    if (deadline == null) {
      throw new IllegalArgumentException("Deadline cannot be null!");
    }
    this.deadline = deadline;
  }

  public Status getStatus() {
    return status;
  }

  /**
   * Updates the status of this task. The status must be a valid, non-null {@link Status} value.
   *
   * @param status the new status to assign to the task; must not be null
   * @throws IllegalArgumentException if the specified status is null
   */
  public void setStatus(Status status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null!");
    }
    this.status = status;
  }

  public Priority getPriority() {
    return priority;
  }

  /**
   * Sets the priority of this task. The priority determines the importance or urgency level of the
   * task and must be a non-null {@link Priority} value.
   *
   * @param priority the priority to assign to the task; must not be null
   * @throws IllegalArgumentException if the provided priority is null
   */
  public void setPriority(Priority priority) {
    if (priority == null) {
      throw new IllegalArgumentException("Priority cannot be null!");
    }
    this.priority = priority;
  }
}
