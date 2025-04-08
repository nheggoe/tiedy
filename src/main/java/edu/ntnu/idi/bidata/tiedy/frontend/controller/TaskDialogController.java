package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.TaskBuilder;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.jspecify.annotations.NonNull;

/**
 * Controller for the TaskDialog component that provides a user interface for creating and editing
 * tasks. The dialog includes basic and advanced sections that can be collapsed.
 *
 * @author Nick Heggø
 * @version 2025.04.07
 */
public class TaskDialogController {

  private final TaskBuilder taskBuilder = new TaskBuilder();

  @FXML private TextField taskNameField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TitledPane advancedOptionsPane;
  @FXML private DatePicker dueDatePicker;
  @FXML private ComboBox<Priority> priorityComboBox;
  @FXML private ComboBox<Status> statusComboBox;

  @NonNull private Mode mode;
  @NonNull private Task task;

  public TaskDialogController() {
    mode = Mode.CREATE;
    task = new Task();
  }

  /** Initializes the dialog components. */
  @FXML
  public void initialize() {
    priorityComboBox.setItems(FXCollections.observableArrayList(Priority.values()));
    statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));

    // --------  start hidden advanced options  --------
    advancedOptionsPane.setExpanded(false);
    // default value
    priorityComboBox.setValue(Priority.NONE);
    statusComboBox.setValue(Status.OPEN);
    dueDatePicker.setValue(LocalDate.now().plusDays(1));
  }

  /**
   * Populates the dialog with an existing task's information for editing.
   *
   * @param task The task to edit
   */
  public void setTask(Task task) {
    this.task = task;

    // Populate fields with task data
    taskNameField.setText(task.getTitle());
    descriptionTextArea.setText(task.getDescription());
    dueDatePicker.setValue(task.getDeadline());
    priorityComboBox.setValue(task.getPriority());
    statusComboBox.setValue(task.getStatus());
  }

  public Task getTask() {
    task.setTitle(taskNameField.getText());
    task.setDescription(descriptionTextArea.getText());
    task.setDeadline(dueDatePicker.getValue());
    task.setPriority(priorityComboBox.getValue());
    task.setStatus(statusComboBox.getValue());
    return task;
  }

  /**
   * Validates the input fields in the dialog.
   *
   * @return true if all required fields are valid, false otherwise
   */
  public boolean validateInput() {
    try {
      taskBuilder
          .title(taskNameField.getText())
          .description(descriptionTextArea.getText())
          .deadline(dueDatePicker.getValue())
          .priority(priorityComboBox.getValue())
          .status(statusComboBox.getValue())
          .build();
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  private enum Mode {
    CREATE,
    EDIT,
    VIEW;
  }
}
