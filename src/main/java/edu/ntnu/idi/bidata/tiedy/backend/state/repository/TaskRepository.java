package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * This interface defines a repository for managing Task entities. It extends the generic
 * DataRepository interface, providing additional task-specific CRUD and query methods.
 *
 * @author Nick Heggø
 * @version 2025.04.03
 */
public interface TaskRepository extends DataRepository<Task> {

  /**
   * Finds all tasks assigned to a specific user.
   *
   * @param userID the ID of the user
   * @return a list of tasks assigned to the user
   */
  List<Task> findByAssignedUser(String userID);

  /**
   * Finds all tasks with a specific status
   *
   * @param status the status to filter by
   * @return a list of tasks with the specified status
   */
  List<Task> findByStatus(Status status);

  /**
   * Finds all tasks with a deadline before the specified data
   *
   * @param priority the priority to filter by
   * @return a list of tasks with the specified priority
   */
  List<Task> findByPriority(Priority priority);

  /**
   * Finds all tasks with a deadline before the specified date.
   *
   * @param date the date to compare deadlines against
   * @return a list of tasks with deadlines before the specified date
   */
  List<Task> findByDeadLineBefore(LocalDate date);

  /**
   * Assigns a task to a user.
   *
   * @param taskId the ID of the task to assign
   * @param userId the ID of the user to assign the task to
   * @return true if the assignment was successful, false otherwise
   */
  boolean assignToUser(UUID taskId, UUID userId);

  /**
   * Unassigns a task from a user.
   *
   * @param taskId the ID of the task to unassign
   * @param userId the ID of the user to unassign the task from
   * @return true if the unassignment was successful, false otherwise
   */
  boolean unassignFromUser(UUID taskId, UUID userId);
}
