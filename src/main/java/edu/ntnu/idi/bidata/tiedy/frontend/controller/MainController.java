package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.logging.Logger;
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
  @FXML private HBox mondayContainer;
  @FXML private HBox tuesdayContainer;
  @FXML private HBox wednesdayContainer;
  @FXML private HBox thursdayContainer;
  @FXML private HBox fridayContainer;
  @FXML private HBox saturdayContainer;
  @FXML private HBox sundayContainer;
  @FXML private Label weekNumberDisplay;
  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

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
    List<VBox> dayViews = List.of(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    dayViews.forEach(
            dayView -> {
              dayView.setSpacing(5);
              dayView
                      .prefWidthProperty()
                      .bind(dayViewContainer.widthProperty().subtract(2).divide(dayViews.size()));
              dayView.prefHeightProperty().bind(dayViewContainer.heightProperty().subtract(2));
            });
    List<HBox> dayImageContainers = List.of(mondayContainer, tuesdayContainer, wednesdayContainer, thursdayContainer, fridayContainer, saturdayContainer, sundayContainer);
    dayImageContainers.forEach(dayImageContainer -> {dayImageContainer.prefWidthProperty().bind(dayViewContainer.widthProperty().subtract(2).divide(dayImageContainers.size()));});
    register();
    setDate(LocalDate.now());
    updateData();
    menuBarController.setUpdateTaskViewPaneCallback(this::renderTasksByWeek, this::getDate);
  }

  @FXML
  public void onWeekButtonPressed() {
    setDate(LocalDate.now());
    updateData();
  }

  public int findWeekNumber() {
    return date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
  }

  @FXML
  public void updateLabels() {
    startOfWeekLabel.setText(date.getDayOfMonth() + "-" + date.getMonth().toString());
    endOfWeekLabel.setText(
            date.plusDays(6).getDayOfMonth() + "-" + date.plusDays(6).getMonth().toString());
    yearTracker.setText(date.getYear() + "");
    weekNumberDisplay.setText("Week "+ findWeekNumber());
  }

  public void setDate(LocalDate date) {
    this.date = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
  }

  @Override
  public void updateData() {
    renderTasksByWeek(
            TiedyApp.getDataAccessFacade()
                    .getActiveTasksByUserIdAndWeek(UserSession.getCurrentUserId(), date));
    updateLabels();
  }

  @FXML
  public void onNextButtonPressed() {
    date = date.plusDays(7);
    updateData();
  }

  @FXML
  public void onPrevButtonPressed() {
    date = date.minusDays(7);
    updateData();
  }

  public void renderTasksByWeek(Map<LocalDate, List<Task>> tasksToBeDisplayed) {
    Map<LocalDate, VBox> vboxMap = new HashMap<>();
    vboxMap.put(date, monday);
    vboxMap.put(date.plusDays(1), tuesday);
    vboxMap.put(date.plusDays(2), wednesday);
    vboxMap.put(date.plusDays(3), thursday);
    vboxMap.put(date.plusDays(4), friday);
    vboxMap.put(date.plusDays(5), saturday);
    vboxMap.put(date.plusDays(6), sunday);
    for (VBox vbox : vboxMap.values()) {
      vbox.getChildren().clear();
    }
    for (Map.Entry<LocalDate, List<Task>> entry : tasksToBeDisplayed.entrySet()) {
      LocalDate taskDate = entry.getKey();
      VBox targetVBox = vboxMap.get(taskDate);
      List<Task> taskList = entry.getValue();
      for (Task task : taskList) {
        targetVBox.getChildren().add(createTaskPane(task));
      }
    }
  }

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

  public LocalDate getDate() {
    return this.date;
  }
}
