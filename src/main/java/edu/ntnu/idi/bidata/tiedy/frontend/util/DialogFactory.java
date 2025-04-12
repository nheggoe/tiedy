package edu.ntnu.idi.bidata.tiedy.frontend.util;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.controller.TaskDialogController;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.jspecify.annotations.NonNull;

/**
 * Utility class for
 *
 * @author Nick Heggø
 * @version 2025.04.12
 */
public class DialogFactory {

  private DialogFactory() {}

  /**
   * Creates and show a dialog for creating a new task, calls the Consumer function to deal with the
   * newly created {@link Task} object on the OK dialog button press.
   *
   * @param taskCallback the consumer function to deal with the newly returned {@link Task} object
   */
  public static void launchTaskCreationDialog(Consumer<Task> taskCallback) {
    generateTaskDialog("New Task", new Task(), taskCallback);
  }

  /**
   * Creates and show a dialog for editing a task, calls the Consumer function on the OK dialog
   * button press, else do nothing.
   *
   * @param taskToEdit The task to edit
   * @param taskCallback Consumer that will be called with the updated task on the OK dialog button
   *     press
   */
  public static void launchEditTaskDialog(Task taskToEdit, Consumer<Task> taskCallback) {
    generateTaskDialog("Edit Task", taskToEdit, taskCallback);
  }

  private static void generateTaskDialog(
      @NonNull String dialogTitle,
      @NonNull Task existingTask,
      @NonNull Consumer<Task> taskCallback) {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              TiedyApp.class.getResource("/edu/ntnu/idi/bidata/tiedy/fxml/dialog/TaskDialog.fxml"));

      // setup dialog
      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.setTitle(dialogTitle);
      dialog.setDialogPane(loader.load());

      // setup controller
      TaskDialogController controller = loader.getController();
      controller.setTask(existingTask);

      // callback on the OK button
      Optional<ButtonType> result = dialog.showAndWait();
      if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
        controller.validateInput();
        taskCallback.accept(controller.getTask());
      }

      // else nothing

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    } catch (IOException e) {
      AlertFactory.generateErrorAlert("Error loading dialog: " + e.getMessage()).showAndWait();
    }
  }
}
