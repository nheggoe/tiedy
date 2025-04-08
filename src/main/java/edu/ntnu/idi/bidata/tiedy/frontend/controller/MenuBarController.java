package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * @version 2025.04.07
 */
public class MenuBarController {

  @FXML private MenuButton taskFilters; // currently no use
  @FXML private MenuItem allTaskFilter;
  @FXML private MenuItem openTaskFilter;
  @FXML private MenuItem inProgressTaskFilter;
  @FXML private MenuItem postponedTaskFilter;
  @FXML private MenuItem closedTaskFilter;

  private Consumer<Collection<Task>> updateTaskViewPaneCallback;

  /**
   * Sets up a callback that will be triggered when filter menu items are selected.
   *
   * @param callback Consumer that will receive the filtered task collection
   */
  public void setUpdateTaskViewPaneCallback(Consumer<Collection<Task>> callback) {
    this.updateTaskViewPaneCallback = callback;
    setupFilterListeners();
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
    DialogFactory.createTaskDialog(
        "New Task",
        new Task(),
        // start of callback function
        task -> {
          try {
            if (TiedyApp.getDataAccessFacade().addTask(task) != null) {

              TiedyApp.getDataAccessFacade()
                  .assignToUser(task.getId(), UserSession.getCurrentUserId());

              // Update the task view if we have a callback
              if (updateTaskViewPaneCallback != null) {
                updateTaskViewPaneCallback.accept(
                    TiedyApp.getDataAccessFacade()
                        .findByAssignedUser(UserSession.getCurrentUserId()));
              }

              AlertFactory.generateInfoAlert("Success", "Task created successfully!").showAndWait();
            } else {
              AlertFactory.generateWarningAlert("Failed to create task").showAndWait();
            }
          } catch (IllegalArgumentException e) {
            AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
          }
        });
  }

  @FXML
  public void onGroupButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.GROUP);
  }

  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
  }

  private void setupFilterListeners() {
    if (updateTaskViewPaneCallback == null) {
      return;
    }

    User user =
        UserSession.getInstance()
            .getCurrentUser()
            .orElseThrow(() -> new IllegalStateException("No user logged in"));
    UUID userId = user.getId();

    allTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade().findByAssignedUser(userId)));

    openTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade().getTasksByUserAndStatus(userId, Status.OPEN)));

    inProgressTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserAndStatus(userId, Status.IN_PROGRESS)));

    closedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade().getTasksByUserAndStatus(userId, Status.CLOSED)));

    postponedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade().getTasksByUserAndStatus(userId, Status.POSTPONED)));
  }
}
