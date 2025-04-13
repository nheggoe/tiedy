package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.util.Collection;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * The MenuBarController handles the interactions with the menu bar in the application's user
 * interface. It manages button clicks, navigation between scenes, and filtering of tasks based on
 * the user's selection.
 *
 * @author Nick Heggø
 * @version 2025.04.13
 */
public class MenuBarController {

  @FXML private MenuButton taskFilterMenu;
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

  /**
   * Handles the action triggered when the home button is pressed in the menubar.
   *
   * <p>This method switches the application's current scene to the MAIN scene, as defined in the
   * {@link SceneName} enum.
   */
  @FXML
  public void onHomeButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
    taskFilterMenu.setDisable(false);
  }

  /**
   * Handles the action triggered when the home button is pressed in the menubar.
   *
   * <p>This method switches the application's current scene to the STATISTIC scene, as defined in
   * the {@link SceneName} enum.
   */
  @FXML
  public void onStatisticsButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.STATISTIC);
    taskFilterMenu.setDisable(true);
  }

  /**
   * Handles the action triggered when the home button is pressed in the menubar.
   *
   * <p>This method launches a dialog to create a new {@link Task}.
   */
  @FXML
  public void onNewTaskButtonPress() {
    DialogFactory.launchTaskCreationDialog(
        createdTask -> {
          if (TiedyApp.getDataAccessFacade().addTask(createdTask) != null) {

            if (TiedyApp.getDataAccessFacade()
                .assignTaskToUser(createdTask.getId(), UserSession.getCurrentUserId())) {

              TiedyApp.getDataChangeNotifier().notifyObservers();
              AlertFactory.generateInfoAlert("Success", "Task created successfully!").showAndWait();

            } else {
              AlertFactory.generateWarningAlert("Failed to assign task to user").showAndWait();
            }

          } else {
            AlertFactory.generateWarningAlert("Failed to create task").showAndWait();
          }
        });
  }

  /**
   * Handles the action triggered when the home button is pressed in the menubar.
   *
   * <p>This method switches the application's current scene to the PROFILE scene, as defined in the
   * {@link SceneName} enum.
   */
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
                TiedyApp.getDataAccessFacade().getTasksByUserId(UserSession.getCurrentUserId())));

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
}
