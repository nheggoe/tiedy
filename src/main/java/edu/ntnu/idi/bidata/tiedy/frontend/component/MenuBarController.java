package edu.ntnu.idi.bidata.tiedy.frontend.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
  private Supplier<LocalDate> startOfWeekSupplier;
  private Consumer<Map<LocalDate, List<Task>>> updateTaskViewPaneCallback;

  /**
   * Sets up a callback that will be triggered when filter menu items are selected.
   *
   * @param callback Consumer that will receive the filtered task collection
   */
  public void setUpdateTaskViewPaneCallback(
      Consumer<Map<LocalDate, List<Task>>> callback, Supplier<LocalDate> date) {
    this.updateTaskViewPaneCallback = Objects.requireNonNull(callback);
    this.startOfWeekSupplier = Objects.requireNonNull(date);
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

  @FXML
  public void onGroupButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.GROUP);
    taskFilterMenu.setDisable(true);
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
                TiedyApp.getDataAccessFacade()
                    .getActiveTasksByUserIdAndWeek(
                        UserSession.getCurrentUserId(), startOfWeekSupplier.get())));

    openTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserIdAndWeekAndStatus(
                        UserSession.getCurrentUserId(), startOfWeekSupplier.get(), Status.OPEN)));

    inProgressTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserIdAndWeekAndStatus(
                        UserSession.getCurrentUserId(),
                        startOfWeekSupplier.get(),
                        Status.IN_PROGRESS)));

    closedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserIdAndWeekAndStatus(
                        UserSession.getCurrentUserId(), startOfWeekSupplier.get(), Status.CLOSED)));

    postponedTaskFilter.setOnAction(
        unused ->
            updateTaskViewPaneCallback.accept(
                TiedyApp.getDataAccessFacade()
                    .getTasksByUserIdAndWeekAndStatus(
                        UserSession.getCurrentUserId(),
                        startOfWeekSupplier.get(),
                        Status.POSTPONED)));
  }
}
