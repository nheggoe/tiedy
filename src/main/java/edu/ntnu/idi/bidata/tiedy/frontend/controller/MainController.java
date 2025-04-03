package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The MainController class is responsible for handling user interactions and managing the
 * application's main scene. This controller uses JavaFX components to display user tasks and
 * provides methods for initializing the view, navigating to other scenes, and adding tasks.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class MainController {

  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

  @FXML private FlowPane flowPane;
  @FXML private Label info;
  @FXML private Button newTaskButton;

  @FXML private MenuButton taskFilter;
  @FXML private MenuItem allTasks;
  @FXML private MenuItem openTasks;
  @FXML private MenuItem inProgressTasks;
  @FXML private MenuItem postponedTasks;
  @FXML private MenuItem closedTasks;

  /**
   * Initializes the main scene by checking the current user session and updating the view
   * accordingly.
   *
   * <p>If no user is logged in, it disables the task creation button and displays an informational
   * message.
   *
   * <p>If a user is logged in, it retrieves the user's list of tasks under the "reminders"
   * category, logs the number of loaded tasks, and dynamically populates the task display area with
   * the corresponding task panes.
   */
  @FXML
  public void initialize() {
    flowPane.setHgap(10);
    flowPane.setVgap(10);
    var optionalUser = UserSession.getInstance().getCurrentUser();
    if (optionalUser.isEmpty()) {
      newTaskButton.setDisable(true);
      taskFilter.setDisable(true);
      info.setText("No user logged in");
    } else {
      User user = optionalUser.get();
      flowPane.getChildren().clear();
      var tasks = TiedyApp.getTaskJsonService().loadJsonAsStream().toList();
      LOGGER.log(
          Level.INFO, () -> "Found " + tasks.size() + " tasks for user " + user.getUsername());

      tasks.stream()
          .filter(task -> task.getAssignedUsers().contains(user.getId()))
          .map(this::createTaskPane)
          .forEach(flowPane.getChildren()::add);
    }

    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));

    allTasks.setOnAction(
        e -> {
          var tasks =
              TiedyApp.getTaskJsonService()
                  .loadJsonAsStream()
                  .filter(task -> task.getAssignedUsers().contains(user.getId()))
                  .toList();
          updateFlowPane(tasks);
        });
    openTasks.setOnAction(
        e -> {
          var tasks =
              TiedyApp.getTaskJsonService()
                  .loadJsonAsStream()
                  .filter(t -> t.getAssignedUsers().contains(user.getId()))
                  .filter(t -> t.getStatus() == Status.OPEN)
                  .toList();
          updateFlowPane(tasks);
        });
    inProgressTasks.setOnAction(
        e -> {
          var tasks =
              TiedyApp.getTaskJsonService()
                  .loadJsonAsStream()
                  .filter(t -> t.getAssignedUsers().contains(user.getId()))
                  .filter(t -> t.getStatus() == Status.IN_PROGRESS)
                  .toList();
          updateFlowPane(tasks);
        });
    closedTasks.setOnAction(
        e -> {
          var tasks =
              TiedyApp.getTaskJsonService()
                  .loadJsonAsStream()
                  .filter(t -> t.getAssignedUsers().contains(user.getId()))
                  .filter(t -> t.getStatus() == Status.CLOSED)
                  .toList();
          updateFlowPane(tasks);
        });
    postponedTasks.setOnAction(
        e -> {
          var tasks =
              TiedyApp.getTaskJsonService()
                  .loadJsonAsStream()
                  .filter(t -> t.getAssignedUsers().contains(user.getId()))
                  .filter(t -> t.getStatus() == Status.POSTPONED)
                  .toList();
          updateFlowPane(tasks);
        });
  }

  private void updateFlowPane(Collection<Task> tasks) {
    flowPane.getChildren().clear();
    tasks.stream().map(this::createTaskPane).forEach(flowPane.getChildren()::add);
  }

  /**
   * Handles the event triggered by pressing the profile button in the main scene.
   *
   * <p>This method switches the current scene of the application to the profile scene. It uses the
   * SceneManager to load the PROFILE scene from its associated FXML file, updating the
   * application's UI to display the profile interface.
   *
   * <p>This method is typically invoked when a user attempts to navigate to the profile view.
   */
  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
  }

  /**
   * Navigates the application to the task creation scene.
   *
   * <p>This method is triggered as a response to user events (e.g., clicking the "Add Task" button)
   * and uses the SceneManager to switch the current scene to the Task scene.
   *
   * <p>It ensures that the application's UI updates to display the task creation interface,
   * allowing users to add a new task.
   */
  @FXML
  public void addTask() {
    TiedyApp.getSceneManager().switchScene(SceneName.TASK);
  }

  private Pane createTaskPane(Task task) {
    Pane cardPane = new Pane();
    cardPane.setPrefSize(120, 80);

    Rectangle taskBg = new Rectangle(0, 0, 120, 80);
    taskBg.setFill(Color.WHITE);
    taskBg.setStroke(Color.BLACK);
    taskBg.setArcWidth(10);
    taskBg.setArcHeight(10);

    Text rankText = new Text(10, 30, task.getTitle());
    rankText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

    cardPane.getChildren().addAll(taskBg, rankText);
    cardPane.setOnMouseClicked(event -> JavaFxFactory.generateTaskDialog(task).showAndWait());
    cardPane.setPadding(new Insets(5));
    return cardPane;
  }
}
