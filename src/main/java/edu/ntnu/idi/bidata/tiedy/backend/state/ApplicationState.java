package edu.ntnu.idi.bidata.tiedy.backend.state;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.Group;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class ApplicationState {
  private static final Logger LOGGER = Logger.getLogger(ApplicationState.class.getName());

  private static ApplicationState instance;

  private final Map<UUID, User> users = new HashMap<>();
  private final Map<UUID, Task> tasks = new HashMap<>();
  private final Map<UUID, Group> groups = new HashMap<>();

  public static ApplicationState getInstance() {
    if (instance == null) {
      instance = new ApplicationState();
    }
    return instance;
  }
}
