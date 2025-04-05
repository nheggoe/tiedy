package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

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
  public void onStatisticsButtonPress() {}

  @FXML
  public void onNewTaskButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.TASK);
  }

  @FXML
  public void onGroupButtonPress() {}

  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
  }
}
