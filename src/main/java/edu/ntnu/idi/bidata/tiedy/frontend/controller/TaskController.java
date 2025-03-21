package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.task.TaskBuilder;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
 * @version 2025.03.19
 */
public class TaskController {

  @FXML private TextField taskName;
  @FXML private TextField taskDescription;
  @FXML private DatePicker dueDate;

  private final TaskBuilder builder = new TaskBuilder();

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
      builder.setTitle(taskName.getText());
      builder.setDescription(taskDescription.getText());
      builder.setDeadline(dueDate.getValue());

      // FIXME need to update JSON file with the newly added task
      // FIXME Currently there is no data persistence.
      User user = UserSession.getInstance().getCurrentUser();
      boolean success = user.addTask(builder.getTask());
      if (success) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task added");
        alert.setContentText("Task added successfully");
        alert.showAndWait();
        TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
      } else {
        throw new IllegalArgumentException("Task could not be added");
      }
    } catch (IllegalArgumentException e) {
      JavaFxFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }

  /** Navigates the user back to the main screen of the application. */
  @FXML
  public void backToMain() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }
}
