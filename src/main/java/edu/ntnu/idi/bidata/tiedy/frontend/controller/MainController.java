package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.util.Collection;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
 * @version 2025.04.09
 */
public class MainController {

  @FXML private FlowPane taskViewPane;

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
    taskViewPane.setHgap(10);
    taskViewPane.setVgap(10);

    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));

    // Initialize tasks with all tasks for the current user
    updateTaskViewPane(TiedyApp.getDataAccessFacade().findByAssignedUser(user.getId()));

    // Set up the menu bar to call updateFlowPane when filters are selected
    if (menuBarController != null) {
      menuBarController.setUpdateTaskViewPaneCallback(this::updateTaskViewPane);
    }
  }

  private void updateTaskViewPane(Collection<Task> tasks) {
    taskViewPane.getChildren().clear();
    tasks.stream().map(this::createTaskPane).forEach(taskViewPane.getChildren()::add);
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

    Button deleteButton = new Button("X");
    deleteButton.setStyle(
        "-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
    deleteButton.setLayoutX(90);
    deleteButton.setLayoutY(10);
    deleteButton.setVisible(false);
    deleteButton.setOnAction(
        event -> {
          Alert confirmationAlert =
              AlertFactory.generateConfirmationAlert(
                  "Delete task", "Are you sure you want to delete this task?");

          // Delete the task only if the OK button is pressed
          if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            TiedyApp.getDataAccessFacade().deleteTask(task.getId());
            updateTaskViewPane(
                TiedyApp.getDataAccessFacade().findByAssignedUser(UserSession.getCurrentUserId()));
          }
        });

    cardPane.setOnMouseEntered(event -> deleteButton.setVisible(true));
    cardPane.setOnMouseExited(event -> deleteButton.setVisible(false));

    cardPane.getChildren().addAll(taskBg, rankText, deleteButton);
    cardPane.setPadding(new Insets(5));
    cardPane.setOnMouseClicked(event -> showEditTaskDialog(task));
    return cardPane;
  }

  /**
   * Opens the task dialog to edit an existing task.
   *
   * @param task The task to edit
   */
  private void showEditTaskDialog(Task task) {
    DialogFactory.editTaskDialog(
        task,
        // the callback function to be called when the passed task is updated
        updatedTask -> {
          try {
            // Update the task in the repository
            TiedyApp.getDataAccessFacade().updateTask(updatedTask);

            updateTaskViewPane(
                TiedyApp.getDataAccessFacade().findByAssignedUser(UserSession.getCurrentUserId()));

          } catch (IllegalArgumentException e) {
            AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
          }
        });
  }
}
