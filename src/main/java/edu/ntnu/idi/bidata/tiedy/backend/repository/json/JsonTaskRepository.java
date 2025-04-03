package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class JsonTaskRepository extends JsonRepository<Task> implements TaskRepository {

  private static JsonTaskRepository instance;

  private JsonTaskRepository() {
    super(Task.class, Task::getId);
  }

  public static synchronized JsonTaskRepository getInstance() {
    if (instance == null) {
      instance = new JsonTaskRepository();
    }
    return instance;
  }

  @Override
  public List<Task> findByAssignedUser(String userID) {
    return List.of();
  }

  @Override
  public List<Task> findByStatus(Status status) {
    return List.of();
  }

  @Override
  public List<Task> findByPriority(Priority priority) {
    return List.of();
  }

  @Override
  public List<Task> findByDeadLineBefore(LocalDate date) {
    return List.of();
  }

  @Override
  public boolean assignToUser(UUID taskId, UUID userId) {
    return false;
  }

  @Override
  public boolean unassignFromUser(UUID taskId, UUID userId) {
    return false;
  }
}
