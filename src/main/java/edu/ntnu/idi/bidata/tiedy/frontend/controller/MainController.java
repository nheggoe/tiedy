package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.component.MenuBarController;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
 * @author Nick Heggø and Odin Arvhage
 * @version 2025.04.25
 */
public class MainController implements DataController {

  @FXML private MenuBarController menuBarController;

  @FXML private HBox dayLabels;
  @FXML private HBox taskContainers;

  @FXML private Label yearLabel;
  @FXML private Label startOfWeekLabel;
  @FXML private Label endOfWeekLabel;
  @FXML private Label weekNumberLabel;

  private LocalDate startOfWeek;

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
    setStartOfWeek(LocalDate.now());

    for (var children : dayLabels.getChildren()) {
      if (children instanceof Label label) {
        label
            .prefWidthProperty()
            .bind(dayLabels.widthProperty().subtract(2).divide(dayLabels.getChildren().size()));
      }
    }

    for (var children : taskContainers.getChildren()) {
      if (children instanceof VBox taskContainer) {
        taskContainer.setSpacing(5);
        taskContainer
            .prefWidthProperty()
            .bind(
                taskContainers
                    .widthProperty()
                    .subtract(2)
                    .divide(taskContainers.getChildren().size()));
        taskContainer.prefHeightProperty().bind(taskContainers.heightProperty().subtract(2));
      }
    }

    menuBarController.setUpdateTaskViewPaneCallback(this::renderTasksByWeek, this::getStartOfWeek);

    register();
    updateData();
  }

  /**
   * Is called when the week button in the Main scene is pressed. Sets the date to the current week
   * and then updates the data.
   */
  @FXML
  public void onWeekButtonPressed() {
    setStartOfWeek(LocalDate.now());
    updateData();
  }

  /**
   * Finds the week number based on the date.
   *
   * @return returns the week number
   */
  public int calculateWeekNumber() {
    return startOfWeek.get(WeekFields.of(Locale.getDefault()).weekOfYear());
  }

  /** Updates the text of the labels in the Main scene. */
  @FXML
  public void updateLabels() {
    startOfWeekLabel.setText(startOfWeek.getDayOfMonth() + "-" + startOfWeek.getMonth().toString());
    endOfWeekLabel.setText(
        startOfWeek.plusDays(6).getDayOfMonth()
            + "-"
            + startOfWeek.plusDays(6).getMonth().toString());
    yearLabel.setText(startOfWeek.getYear() + "");
    weekNumberLabel.setText("Week " + calculateWeekNumber());
  }

  /**
   * Sets the date of the MainController. Will set the date to the first monday going back in time.
   *
   * @param startOfWeek date to be set
   */
  public void setStartOfWeek(LocalDate startOfWeek) {
    this.startOfWeek = startOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
  }

  /** Updates the data of the Main scene. */
  @Override
  public void updateData() {
    renderTasksByWeek(
        TiedyApp.getDataAccessFacade()
            .getActiveTasksByUserIdAndWeek(UserSession.getCurrentUserId(), startOfWeek));
    updateLabels();
  }

  /**
   * Is called when the next button is pressed in the Main scene. Goes a week forward and then
   * updates all data.
   */
  @FXML
  public void onNextButtonPressed() {
    startOfWeek = startOfWeek.plusDays(7);
    updateData();
  }

  /**
   * Is called when the previous button is pressed in the Main scene. Goes a week back and then
   * updates all data.
   */
  @FXML
  public void onPrevButtonPressed() {
    startOfWeek = startOfWeek.minusDays(7);
    updateData();
  }

  /**
   * Renders the given map of tasks in the week view.
   *
   * @param tasksToBeDisplayed map of the tasks to be rendered.
   */
  public void renderTasksByWeek(Map<LocalDate, List<Task>> tasksToBeDisplayed) {
    // lookup map for dayOfWeek to its corresponding container
    var tasksByDayOfWeek = new HashMap<LocalDate, VBox>();
    int daysToAdd = 0;
    for (var children : taskContainers.getChildren()) {
      if (children instanceof VBox taskContainer) {
        tasksByDayOfWeek.put(startOfWeek.plusDays(daysToAdd++), taskContainer);
      }
    }

    tasksByDayOfWeek.values().forEach(taskContainer -> taskContainer.getChildren().clear());

    for (var entry : tasksToBeDisplayed.entrySet()) {
      var taskContainer = tasksByDayOfWeek.get(entry.getKey());
      var tasks = entry.getValue();
      tasks.stream()
          .sorted(Comparator.comparing(Task::getPriority))
          .map(this::createTaskPane)
          .forEach(taskContainer.getChildren()::add);
    }
  }

  /**
   * Creates a task pane to be displayed in the calendar view.
   *
   * @param task to be displayed
   * @return finished task render
   */
  private Pane createTaskPane(Task task) {
    Pane cardPane = new Pane();
    cardPane.setPrefSize(110, 80);

    Rectangle taskBg = new Rectangle(0, 0, 110, 80);
    taskBg.setFill(Color.web("6495ed"));
    taskBg.setArcWidth(10);
    taskBg.setArcHeight(10);

    Text rankText = new Text(10, 15, task.getTitle());
    rankText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    rankText.setFill(Color.web("fff8dc"));
    rankText.setWrappingWidth(90);

    String statusText = task.getStatus().getDisplayName();
    Text statusIndicator = new Text(10, 55, statusText);
    statusIndicator.setFont(Font.font("Arial", 8));
    statusIndicator.setFill(Color.web("fff8dc"));
    statusIndicator.setWrappingWidth(90);

    switch (task.getStatus()) {
      case CLOSED -> statusIndicator.setFill(Color.GREEN);
      case IN_PROGRESS -> statusIndicator.setFill(Color.BLUE);
      case POSTPONED -> statusIndicator.setFill(Color.ORANGE);
      default -> statusIndicator.setFill(Color.BLACK);
    }

    Button deleteButton = new Button("X");
    deleteButton.setStyle(
        "-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
    deleteButton.setLayoutX(40);
    deleteButton.setLayoutY(20);
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
    completeButton.setLayoutX(10);
    completeButton.setLayoutY(20);
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
    cardPane.setOnMouseClicked(unused -> showEditTaskDialog(task));
    cardPane.setPadding(new Insets(15));
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

  /**
   * Gets the date of this class.
   *
   * @return the date
   */
  public LocalDate getStartOfWeek() {
    return this.startOfWeek;
  }
}
