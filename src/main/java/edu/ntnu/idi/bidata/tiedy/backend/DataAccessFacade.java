package edu.ntnu.idi.bidata.tiedy.backend;

import edu.ntnu.idi.bidata.tiedy.backend.repository.DataRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.GroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.TaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonGroupRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonTaskRepository;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Intended to be the only class that the frontend package interacts with, it would provide all the
 * necessary public interface.
 *
 * @author Nick Heggø
 * @version 2025.04.03
 */
public class DataAccessFacade implements Runnable {

  private static final Logger LOGGER = Logger.getLogger(DataAccessFacade.class.getName());

  private static DataAccessFacade instance;

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;

  private DataAccessFacade() {
    groupRepository = JsonGroupRepository.getInstance();
    userRepository = JsonUserRepository.getInstance();
    taskRepository = JsonTaskRepository.getInstance();
  }

  public static synchronized DataAccessFacade getInstance() {
    if (instance == null) {
      instance = new DataAccessFacade();
    }
    return instance;
  }

  @Override
  public void run() {
    List.of(groupRepository, userRepository, taskRepository).forEach(DataRepository::saveChanges);
    LOGGER.info(() -> LocalDateTime.now() + " Application state saved");
  }
}
