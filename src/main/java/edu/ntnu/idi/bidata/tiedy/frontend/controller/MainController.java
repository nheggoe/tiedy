package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
 * @version 2025.04.12
 */
public class MainController implements DataController {

  @FXML private FlowPane taskViewPane;

  // Reference to the included MenuBar's controller
  @FXML private MenuBarController menuBarController;
  @FXML private Button prevButton;
  @FXML private Button nextButton;
  @FXML private VBox sunday;
  @FXML private VBox saturday;
  @FXML private VBox friday;
  @FXML private VBox thursday;
  @FXML private VBox wednesday;
  @FXML private VBox tuesday;
  @FXML private VBox monday;
  private LocalDate date;
  @FXML private Label startOfWeekLabel;
  @FXML private Label endOfWeekLabel;
  @FXML private Label yearTracker;
  @FXML private HBox dayViewContainer;
  @FXML private HBox dayImageContainer;
  @FXML private HBox mondayContainer;
  @FXML private HBox tuesdayContainer;
  @FXML private HBox wednesdayContainer;
  @FXML private HBox thursdayContainer;
  @FXML private HBox fridayContainer;
  @FXML private HBox saturdayContainer;
  @FXML private HBox sundayContainer;

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

    register();
    updateData();

    // Set up the menu bar to call updateFlowPane when filters are selected
    if (menuBarController != null) {
      menuBarController.setUpdateTaskViewPaneCallback(this::updateTaskViewPane);
    }
  }

  @Override
  public void updateData() {
    updateTaskViewPane(
        TiedyApp.getDataAccessFacade().getActiveTasksByUserId(UserSession.getCurrentUserId()));
  }

  private void updateTaskViewPane(Collection<Task> tasks) {
    taskViewPane.getChildren().clear();
    tasks.stream()
        .sorted(Comparator.comparing(Task::getPriority))
        .map(this::createTaskPane)
        .forEach(taskViewPane.getChildren()::add);
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

    String statusText = task.getStatus().getDisplayName();
    Text statusIndicator = new Text(10, 65, statusText);
    statusIndicator.setFont(Font.font("Arial", 10));

    switch (task.getStatus()) {
      case CLOSED -> statusIndicator.setFill(Color.GREEN);
      case IN_PROGRESS -> statusIndicator.setFill(Color.BLUE);
      case POSTPONED -> statusIndicator.setFill(Color.ORANGE);
      default -> statusIndicator.setFill(Color.BLACK);
    }

    Button deleteButton = new Button("X");
    deleteButton.setStyle(
        "-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
    deleteButton.setLayoutX(90);
    deleteButton.setLayoutY(10);
    deleteButton.setVisible(false);
    deleteButton.setOnAction(
        unused -> {
          Alert confirmationAlert =
              AlertFactory.generateConfirmationAlert(
                  "Delete task", "Are you sure you want to delete this task?");

          if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            deleteTask(task);
          }
        });

    Button completeButton = new Button("✓");
    completeButton.setStyle(
        "-fx-background-color: limegreen; -fx-text-fill: white; -fx-font-weight: bold;");
    completeButton.setLayoutX(60);
    completeButton.setLayoutY(10);
    completeButton.setVisible(false);

    if (task.getStatus() != Status.CLOSED) {
      completeButton.setOnAction(unused -> completeTask(task));
    } else {
      taskBg.setFill(Color.LIGHTGRAY);
      completeButton.setStyle(
          "-fx-background-color: darkgrey; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    // Show/hide buttons on hover
    cardPane.setOnMouseEntered(
        unused -> {
          deleteButton.setVisible(true);
          if (task.getStatus() != Status.CLOSED) {
            completeButton.setVisible(true);
          }
        });

    cardPane.setOnMouseExited(
        unused -> {
          deleteButton.setVisible(false);
          completeButton.setVisible(false);
        });

    cardPane.getChildren().addAll(taskBg, rankText, statusIndicator, deleteButton, completeButton);
    cardPane.setPadding(new Insets(5));
    cardPane.setOnMouseClicked(unused -> showEditTaskDialog(task));
    return cardPane;
  }

  private void completeTask(Task task) {
    Status status = task.getStatus();
    task.setStatus(Status.CLOSED);

    // if the task is successfully updated
    if (TiedyApp.getDataAccessFacade().updateTask(task) != null) {
      AlertFactory.generateInfoAlert(
              "Task Completed", "Task '" + task.getTitle() + "' has been marked as closed.")
          .showAndWait();
      if (UserSession.completeTask()) {
        AlertFactory.generateInfoAlert(
                "Level UP!",
                "Congratulations! You have leveled up. Your current level is now "
                    + UserSession.getCurrentLevel()
                    + ".")
            .showAndWait();
      }
    } else {
      AlertFactory.generateWarningAlert("Failed to mark task as closed").showAndWait();
      task.setStatus(status);
      TiedyApp.getDataAccessFacade().updateTask(task);
    }

    TiedyApp.getDataChangeNotifier().notifyObservers();
  }

  private void deleteTask(Task task) {
    if (TiedyApp.getDataAccessFacade().removeTask(task.getId())) {
      TiedyApp.getDataChangeNotifier().notifyObservers();
    } else {
      AlertFactory.generateWarningAlert("Failed to delete task").showAndWait();
    }
  }

  /**
   * Opens the task dialog to edit an existing task.
   *
   * @param taskToEdit The task to edit
   */
  private void showEditTaskDialog(Task taskToEdit) {
    DialogFactory.launchEditTaskDialog(
        taskToEdit,
        updatedTask -> {
          if (TiedyApp.getDataAccessFacade().updateTask(updatedTask) != null) {
            TiedyApp.getDataChangeNotifier().notifyObservers();
          } else {
            AlertFactory.generateWarningAlert("Failed to update task").showAndWait();
          }
        });
  }
}
