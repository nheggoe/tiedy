package edu.ntnu.idi.bidata.tiedy.frontend.dialog;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.jspecify.annotations.NonNull;

public class TaskDetailDialog extends Dialog<Task> {

  @NonNull private Mode mode;
  @NonNull private Task task;

  public TaskDetailDialog() {
    super();
    this.mode = Mode.CREATE;
    this.task = new Task();
    initialize();
  }

  public TaskDetailDialog(@NonNull Task task, boolean readOnly) {
    super();
    this.mode = readOnly ? Mode.VIEW : Mode.EDIT;
    this.task = task;
    initialize();
  }

  private void initialize() {
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass()
                  .getResource("/edu/ntnu/idi/bidata/tiedy/fxml/dialog/TaskDetailDialog.fxml"));

      setTitle(mode.name() + " TASK");
      setDialogPane(loader.load());
      setResultConverter(
          buttonType -> {
            Task result = null;
            if (buttonType == ButtonType.OK) {
              return task;
            }
            return null;
          });
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load FXML file: " + e.getMessage(), e);
    }
  }

  private enum Mode {
    CREATE,
    EDIT,
    VIEW;
  }
}
