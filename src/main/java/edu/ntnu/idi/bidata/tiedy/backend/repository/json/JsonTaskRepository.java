package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * JSON powered implementation of the TaskRepository, Inherit all basic operations from the {@link
 * JsonRepository} class.
 *
 * @author Nick Heggø
 * @version 2025.04.04
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
  public List<Task> findByAssignedUser(UUID userId) {
    return getAll().stream().filter(task -> task.getAssignedUsers().contains(userId)).toList();
  }

  @Override
  public List<Task> findByStatus(Status status) {
    return getAll().stream().filter(task -> task.getStatus() == status).toList();
  }

  @Override
  public List<Task> findByPriority(Priority priority) {
    return getAll().stream().filter(task -> task.getPriority() == priority).toList();
  }

  @Override
  public List<Task> findByDeadLineBefore(LocalDate date) {
    return getAll().stream().filter(task -> task.getDeadline().isBefore(date)).toList();
  }

  @Override
  public boolean assignToUser(UUID taskId, UUID userId) {
    Task task = getById(taskId).orElse(null);
    if (Objects.isNull(task)) {
      return false;
    }
    return task.getAssignedUsers().add(userId);
  }

  @Override
  public boolean unassignFromUser(UUID taskId, UUID userId) {
    Task task = getById(taskId).orElse(null);
    if (Objects.isNull(task)) {
      return false;
    }
    return task.getAssignedUsers().remove(userId);
  }
}
