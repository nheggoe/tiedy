package edu.ntnu.idi.bidata.tiedy.backend.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * This interface defines a repository for managing Task entities. It extends the generic
 * DataRepository interface, providing additional task-specific CRUD and query methods.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 */
public interface TaskRepository extends DataRepository<Task> {

  /**
   * Finds all tasks assigned to a specific user.
   *
   * @param userId the ID of the user
   * @return a stream of tasks assigned to the user
   */
  Stream<Task> getTasksByUserId(UUID userId);

  /**
   * Retrieves a stream of active tasks assigned to a specific user. A task is considered active
   * based on its status, typically excluding completed or archived tasks.
   *
   * @param userId the unique identifier of the user whose active tasks are being retrieved; must
   *     not be null
   * @return a stream of tasks that are active and assigned to the specified user
   */
  Stream<Task> getActiveTasksByUserId(UUID userId);

  /**
   * Finds all tasks with a specific status.
   *
   * @param status the status to filter by
   * @return a stream of tasks with the specified status
   */
  Stream<Task> getTasksByStatus(Status status);

  /**
   * Finds all tasks with a deadline before the specified data.
   *
   * @param priority the priority to filter by
   * @return a stream of tasks with the specified priority
   */
  Stream<Task> getTasksByPriority(Priority priority);

  /**
   * Finds all tasks with a deadline before the specified date.
   *
   * @param date the date to compare deadlines against
   * @return a stream of tasks with deadlines before the specified date
   */
  Stream<Task> getTasksBeforeDate(LocalDate date);

  /**
   * Assigns a task to a user.
   *
   * @param taskId the ID of the task to assign
   * @param userId the ID of the user to assign the task to
   * @return true if the assignment was successful, false otherwise
   */
  boolean assignTaskToUser(UUID taskId, UUID userId);

  /**
   * Unassigns a task from a user.
   *
   * @param taskId the ID of the task to unassign
   * @param userId the ID of the user to unassign the task from
   * @return true if the unassignment was successful, false otherwise
   */
  boolean unassignTaskFromUser(UUID taskId, UUID userId);
}
