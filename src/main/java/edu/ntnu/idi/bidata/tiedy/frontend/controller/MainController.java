package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import java.util.Collection;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

  // Reference to the included MenuBar's controller
  @FXML private MenuBarController menuBarController;

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

    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));

    // Initialize tasks with all tasks for the current user
    updateFlowPane(TiedyApp.getDataAccessFacade().findByAssignedUser(user.getId()));

    // Set up the menu bar to call updateFlowPane when filters are selected
    if (menuBarController != null) {
      menuBarController.setTaskUpdateCallback(this::updateFlowPane);
    }
  }

  private void updateFlowPane(Collection<Task> tasks) {
    flowPane.getChildren().clear();
    tasks.stream().map(this::createTaskPane).forEach(flowPane.getChildren()::add);
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
    cardPane.setPadding(new Insets(5));
    cardPane.setOnMouseClicked(
        event -> {
          JavaFxFactory.generateInfoAlert("Task selected", task.toString());
        });
    return cardPane;
  }
}
