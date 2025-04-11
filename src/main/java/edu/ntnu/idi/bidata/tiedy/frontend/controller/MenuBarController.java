package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * @version 2025.04.09
 */
public class MenuBarController implements Controller {

  private final List<Observer> observers = new ArrayList<>();

  @FXML private MenuButton taskFilterMenu; // currently no use
  @FXML private MenuItem allTaskFilter;
  @FXML private MenuItem openTaskFilter;
  @FXML private MenuItem inProgressTaskFilter;
  @FXML private MenuItem postponedTaskFilter;
  @FXML private MenuItem closedTaskFilter;

  private Consumer<Collection<Task>> updateTaskViewPaneCallback;

  @Override
  public void initialize() {}

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
    taskFilterMenu.setDisable(false);
  }

  @FXML
  public void onStatisticsButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.STATISTIC);

    taskFilterMenu.setDisable(true);
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
                        .getAllNoneClosedTaskByUserId(UserSession.getCurrentUserId()));
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
    taskFilterMenu.setDisable(true);
  }

  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
    taskFilterMenu.setDisable(true);
  }

  private void setupFilterListeners() {
    if (updateTaskViewPaneCallback == null) {
      return;
    }

    allTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getAllNoneClosedTaskByUserId(UserSession.getCurrentUserId())));

    openTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserAndStatus(UserSession.getCurrentUserId(), Status.OPEN)));

    inProgressTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserAndStatus(UserSession.getCurrentUserId(), Status.IN_PROGRESS)));

    closedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserAndStatus(UserSession.getCurrentUserId(), Status.CLOSED)));

    postponedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserAndStatus(UserSession.getCurrentUserId(), Status.POSTPONED)));
  }

  public interface Observer {
    void update();
  }
}
