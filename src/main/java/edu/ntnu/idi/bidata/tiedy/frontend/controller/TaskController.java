package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import java.io.IOException;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

  private final JsonService taskService = new JsonService(Task.class);

  @FXML private TextField taskName;
  @FXML private TextField taskDescription;

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
      String name = taskName.getText();
      String description = taskDescription.getText();
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Task name cannot be empty");
      }
      if (description == null || description.isBlank()) {
        throw new IllegalArgumentException("Task description cannot be empty");
      }
      User user = UserSession.getInstance().getCurrentUser();
      var task = new Task(user, name, description);
      boolean success = user.addTask(task);
      var tasks = taskService.loadJsonAsStream();
      taskService.writeCollection(Stream.concat(tasks, Stream.of(tasks)));

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
    } catch (IOException e) {
      JavaFxFactory.generateErrorAlert("Cannot write to JSON, please try again").showAndWait();
    }
  }

  /** Navigates the user back to the main screen of the application. */
  @FXML
  public void backToMain() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }
}
