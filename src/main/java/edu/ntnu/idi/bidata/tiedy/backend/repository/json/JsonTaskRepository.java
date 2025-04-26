package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * JSON powered implementation of the TaskRepository, Inherit all basic operations from the {@link
 * JsonRepository} class.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 * @see JsonRepository
 */
public class JsonTaskRepository extends JsonRepository<Task> implements TaskRepository {

  private static JsonTaskRepository instance;

  private JsonTaskRepository() {
    super(Task.class, Task::getId);
  }

  /**
   * Returns the singleton instance of the JsonTaskRepository. This implementation ensures that only
   * one instance of JsonTaskRepository exists throughout the application lifecycle.
   *
   * @return the singleton instance of JsonTaskRepository
   */
  public static synchronized JsonTaskRepository getInstance() {
    if (instance == null) {
      instance = new JsonTaskRepository();
    }
    return instance;
  }

  @Override
  public Stream<Task> getTasksByUserId(UUID userId) {
    return getAll().filter(task -> task.getAssignedUsers().contains(userId));
  }

  @Override
  public Stream<Task> getActiveTasksByUserId(UUID userId) {
    return getTasksByUserId(userId).filter(task -> task.getStatus() != Status.CLOSED);
  }


  @Override
  public Stream<Task> getTasksByStatus(Status status) {
    return getAll().filter(task -> task.getStatus() == status);
  }

  @Override
  public Stream<Task> getTasksByPriority(Priority priority) {
    return getAll().filter(task -> task.getPriority() == priority);
  }

  @Override
  public Stream<Task> getTasksBeforeDate(LocalDate date) {
    return getAll().filter(task -> task.getDeadline().isBefore(date));
  }

  @Override
  public boolean assignTaskToUser(UUID taskId, UUID userId) {
    Task task = getById(taskId).orElse(null);
    if (task == null) {
      return false;
    }
    return task.assignUser(userId);
  }

  @Override
  public boolean unassignTaskFromUser(UUID taskId, UUID userId) {
    Task task = getById(taskId).orElse(null);
    if (task == null) {
      return false;
    }
    return task.getAssignedUsers().remove(userId);
  }
}
