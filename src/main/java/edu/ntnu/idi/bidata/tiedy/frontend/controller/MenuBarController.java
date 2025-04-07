package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.dialog.TaskDialogController;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * @version 2025.04.07
 */
public class MenuBarController {

  @FXML private MenuButton taskFilter;
  @FXML private MenuItem allTasks;
  @FXML private MenuItem openTasks;
  @FXML private MenuItem inProgressTasks;
  @FXML private MenuItem postponedTasks;
  @FXML private MenuItem closedTasks;

  private Consumer<Collection<Task>> taskUpdateCallback;

  /**
   * Sets up a callback that will be triggered when filter menu items are selected.
   *
   * @param callback Consumer that will receive the filtered task collection
   */
  public void setTaskUpdateCallback(Consumer<Collection<Task>> callback) {
    this.taskUpdateCallback = callback;
    setupFilterListeners();
  }

  private void setupFilterListeners() {
    if (taskUpdateCallback == null) {
      return;
    }

    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));
    UUID userId = user.getId();

    if (allTasks != null) {
      allTasks.setOnAction(
          e ->
              taskUpdateCallback.accept(TiedyApp.getDataAccessFacade().findByAssignedUser(userId)));
    }

    if (openTasks != null) {
      openTasks.setOnAction(
          e ->
              taskUpdateCallback.accept(
                  TiedyApp.getDataAccessFacade().getTasksByUserAndStatus(userId, Status.OPEN)));
    }

    if (inProgressTasks != null) {
      inProgressTasks.setOnAction(
          e ->
              taskUpdateCallback.accept(
                  TiedyApp.getDataAccessFacade()
                      .getTasksByUserAndStatus(userId, Status.IN_PROGRESS)));
    }

    if (closedTasks != null) {
      closedTasks.setOnAction(
          e ->
              taskUpdateCallback.accept(
                  TiedyApp.getDataAccessFacade().getTasksByUserAndStatus(userId, Status.CLOSED)));
    }

    if (postponedTasks != null) {
      postponedTasks.setOnAction(
          e ->
              taskUpdateCallback.accept(
                  TiedyApp.getDataAccessFacade()
                      .getTasksByUserAndStatus(userId, Status.POSTPONED)));
    }
  }

  @FXML
  public void initialize() {
    // Initialize menu components if needed
  }

  @FXML
  public void onHomeButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }

  @FXML
  public void onStatisticsButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.STATISTIC);
  }

  @FXML
  public void onNewTaskButtonPress() {
    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));

    try {
      // Load the dialog
      FXMLLoader loader =
          new FXMLLoader(
              TiedyApp.class.getResource("/edu/ntnu/idi/bidata/tiedy/fxml/dialog/TaskDialog.fxml"));
      DialogPane dialogPane = loader.load();

      // Create the dialog
      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.setDialogPane(dialogPane);
      dialog.setTitle("Create Task");

      // Get controller reference
      TaskDialogController controller = loader.getController();

      // Show dialog and handle result
      dialog
          .showAndWait()
          .ifPresent(
              buttonType -> {
                if (buttonType == ButtonType.OK && controller.validateInput()) {
                  try {
                    Task task = controller.getTask();

                    if (TiedyApp.getDataAccessFacade().addTask(task) != null) {
                      TiedyApp.getDataAccessFacade().assignToUser(task.getId(), user.getId());

                      // Update the task view if we have a callback
                      if (taskUpdateCallback != null) {
                        taskUpdateCallback.accept(
                            TiedyApp.getDataAccessFacade().findByAssignedUser(user.getId()));
                      }

                      AlertFactory.generateInfoAlert("Success", "Task created successfully!")
                          .showAndWait();
                    } else {
                      AlertFactory.generateWarningAlert("Failed to create task").showAndWait();
                    }
                  } catch (IllegalArgumentException e) {
                    AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
                  }
                }
              });
    } catch (IOException e) {
      AlertFactory.generateErrorAlert("Error loading dialog: " + e.getMessage()).showAndWait();
    }
  }

  @FXML
  public void onGroupButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.GROUP);
  }

  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
  }
}
