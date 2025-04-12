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
 * @version 2025.04.09
 */
public class TaskDialogController implements Controller {

  private final TaskBuilder taskBuilder = new TaskBuilder();

  @FXML private TextField taskNameField;
  @FXML private TextArea descriptionTextArea;
  @FXML private TitledPane advancedOptionsPane;
  @FXML private DatePicker dueDatePicker;
  @FXML private ComboBox<Priority> priorityComboBox;
  @FXML private ComboBox<Status> statusComboBox;

  @NonNull private Task task;

  public TaskDialogController() {
    task = new Task();
  }

  /** Initializes the dialog components. */
  @Override
  public void initialize() {
    priorityComboBox.setItems(FXCollections.observableArrayList(Priority.values()));
    statusComboBox.setItems(FXCollections.observableArrayList(Status.values()));
    statusComboBox.setValue(Status.OPEN);

    // --------  start hidden advanced options  --------
    advancedOptionsPane.setExpanded(false);
    priorityComboBox.setValue(Priority.NONE);
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
    dueDatePicker.setValue(
        task.getDeadline() == null ? LocalDate.now().plusDays(1) : task.getDeadline());
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
   * Validates if all the necessary information is provided
   *
   * @throws IllegalArgumentException if not all necessary information is provided
   */
  public void validateInput() {
    taskBuilder
        .title(taskNameField.getText())
        .description(descriptionTextArea.getText())
        .deadline(dueDatePicker.getValue())
        .priority(priorityComboBox.getValue())
        .status(statusComboBox.getValue())
        .build();
  }
}
