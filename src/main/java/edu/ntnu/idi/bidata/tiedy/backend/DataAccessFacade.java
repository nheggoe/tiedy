package edu.ntnu.idi.bidata.tiedy.backend;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class DataAccessFacade implements Runnable {
  private static final Logger LOGGER = Logger.getLogger(DataAccessFacade.class.getName());

  private static DataAccessFacade instance;

  private final JsonService<User> userService;
  private final JsonService<Task> taskService;
  private final JsonService<Group> groupService;
  // TODO change to Repository design pattern
  private final Map<UUID, User> users;
  private final Map<UUID, Task> tasks;
  private final Map<UUID, Group> groups;

  private DataAccessFacade() {
    userService = new JsonService<>(User.class);
    taskService = new JsonService<>(Task.class);
    groupService = new JsonService<>(Group.class);
    users = new HashMap<>();
    tasks = new HashMap<>();
    groups = new HashMap<>();
    loadData();
  }

  @Override
  public void run() {
    userService.writeCollection(new HashSet<>(users.values()));
    taskService.writeCollection(new HashSet<>(tasks.values()));
    groupService.writeCollection(new HashSet<>(groups.values()));
    LOGGER.info(() -> LocalDateTime.now() + " Application state saved");
  }

  public static DataAccessFacade getInstance() {
    if (instance == null) {
      instance = new DataAccessFacade();
    }
    return instance;
  }

  public User getUserByUsername(String username) {
    return users.values().stream()
        .filter(user -> user.getUsername().equals(username.strip()))
        .findFirst()
        .orElse(null);
  }

  public User getUser(UUID id) {
    return users.get(id);
  }

  public Task getTask(UUID id) {
    return tasks.get(id);
  }

  public Group getGroup(UUID id) {
    return groups.get(id);
  }

  private void loadData() {
    userService.loadJsonAsStream().forEach(user -> users.put(user.getId(), user));
    taskService.loadJsonAsStream().forEach(task -> tasks.put(task.getId(), task));
    groupService.loadJsonAsStream().forEach(group -> groups.put(group.getId(), group));
  }
}
