package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.TaskBuilder;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controller class responsible for managing the task-related functionalities in the application.
 * This includes handling user interactions for creating and submitting new tasks, as well as
 * navigating back to the main screen. The tasks created by the user are managed and persisted using
 * JSON files.
 *
 * <p>The controller uses a {@link JsonService} to update the JSON data for user tasks, and relies
 * on JavaFX components for user interface interactions.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class TaskController {

  @FXML private TextField taskName;
  @FXML private TextField taskDescription;
  @FXML private DatePicker dueDate;

  private final TaskBuilder taskBuilder = new TaskBuilder();

  /**
   * Handles submission of a new task by the user.
   *
   * <p>This method reads the task name and description from the corresponding text fields and
   * validates their content. If validation passes, the task is added to the currently logged-in
   * user's task list. The method updates the JSON storage to reflect the changes and provides
   * feedback to the user through alert dialogs.
   *
   * <p>If the task is successfully added, the user is returned to the main screen. Otherwise,
   * appropriate error or warning alerts are displayed.
   *
   * <p>Alert dialogs are used for notifying the user in case of success or failure.
   */
  @FXML
  public void submitTask() {
    try {
      User user =
          UserSession.getInstance()
              .getCurrentUser()
              .orElseThrow(() -> new IllegalStateException("No user is logged in"));
      Task task =
          taskBuilder
              .title(taskName.getText())
              .description(taskDescription.getText())
              .deadline(dueDate.getValue())
              .build();

      if (Objects.nonNull(TiedyApp.getDataAccessFacade().addTask(task))) {
        TiedyApp.getDataAccessFacade().assignToUser(task.getId(), user.getId());
      } else {
        throw new IllegalArgumentException("Task could not be added");
      }

      JavaFxFactory.generateInfoAlert("Task added successfully", "Task added successfully")
          .showAndWait();
      backToMain();

    } catch (IllegalArgumentException | DateTimeParseException e) {
      JavaFxFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }

  /** Navigates the user back to the main screen of the application. */
  @FXML
  public void backToMain() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }
}
