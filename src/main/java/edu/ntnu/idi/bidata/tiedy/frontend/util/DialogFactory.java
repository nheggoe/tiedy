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

/**
 * A utility class for creating and configuring JavaFX dialogs. Provides factory methods for common
 * dialog types used throughout the application.
 *
 * @author Nick Heggø
 * @version 2025.04.08
 */
public class DialogFactory {

  private DialogFactory() {}

  private static Dialog<ButtonType> generateTaskDialog(String title) throws IOException {
    FXMLLoader loader =
        new FXMLLoader(
            TiedyApp.class.getResource("/edu/ntnu/idi/bidata/tiedy/fxml/dialog/TaskDialog.fxml"));

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle(title);
    dialog.setDialogPane(loader.load());
    return dialog;
  }

  /**
   * Creates a dialog for editing an existing task.
   *
   * @param dialogTitle The dialog title
   * @param existingTask The task to edit
   * @param onSuccess Consumer that will be called with the updated task on success
   * @return Optional containing the updated task if successful, or empty if cancelled
   */
  public static Optional<Task> createTaskDialog(
      String dialogTitle, Task existingTask, Consumer<Task> onSuccess) {
    try {
      // Load the dialog
      FXMLLoader loader =
          new FXMLLoader(
              TiedyApp.class.getResource("/edu/ntnu/idi/bidata/tiedy/fxml/dialog/TaskDialog.fxml"));

      Dialog<ButtonType> dialog = new Dialog<>();
      dialog.setDialogPane(loader.load());

      dialog.setTitle(
          Optional.ofNullable(dialogTitle)
              .orElseGet(() -> ((existingTask.getTitle() == null) ? "Create Task" : "Edit Task")));

      // Get controller reference
      TaskDialogController controller = loader.getController();

      // Set task for editing if provided
      if (existingTask != null) {
        controller.setTask(existingTask);
      }

      // Show dialog and handle result
      Optional<ButtonType> result = dialog.showAndWait();

      if (result.isPresent() && result.get() == ButtonType.OK && controller.validateInput()) {
        Task task = controller.getTask();

        if (onSuccess != null) {
          onSuccess.accept(task);
        }

        return Optional.of(task);
      }

    } catch (IOException e) {
      AlertFactory.generateErrorAlert("Error loading dialog: " + e.getMessage()).showAndWait();
    }

    return Optional.empty();
  }

  /**
   * Creates a dialog for editing a task.
   *
   * @param taskToEdit The task to edit
   * @param onSuccess Consumer that will be called with the updated task on success
   * @return Optional containing the updated task if successful, or empty if cancelled
   */
  public static Optional<Task> editTaskDialog(Task taskToEdit, Consumer<Task> onSuccess) {
    return createTaskDialog("Edit Task", taskToEdit, onSuccess);
  }
}
