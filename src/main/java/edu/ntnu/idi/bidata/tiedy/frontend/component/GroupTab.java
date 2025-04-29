package edu.ntnu.idi.bidata.tiedy.frontend.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DialogFactory;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class GroupTab extends Tab {

  @FXML private VBox root;
  @FXML private TextField searchBox;

  @FXML private Label groupNameLabel;
  @FXML private Label groupDescriptionLabel;

  // left
  @FXML private TableView<Task> tasksTable;
  @FXML private TableColumn<Task, String> taskTitleColumn;
  @FXML private TableColumn<Task, String> taskPriorityColumn;
  @FXML private TableColumn<Task, String> taskAssignedColumn;

  // center
  @FXML private Button addButton;
  @FXML private Button removeButton;

  // right
  @FXML private TableView<User> groupLeaderBoard;
  @FXML private TableColumn<User, String> userColumn;
  @FXML private TableColumn<User, Integer> levelColumn;
  @FXML private TableColumn<User, String> roleColumn;

  private final Group group;

  public GroupTab(Group group) {
    if (group == null) {
      throw new IllegalArgumentException("Group cannot be null!");
    }
    setText(group.getName());
    setClosable(false);

    this.group = group;
    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/edu/ntnu/idi/bidata/tiedy/fxml/component/GroupTab.fxml"));
      loader.setController(this);
      setContent(loader.load());
    } catch (IOException e) {
      throw new IllegalStateException("Could not load GroupTab.fxml");
    }
  }

  @FXML
  public void initialize() {
    groupNameLabel.setText(group.getName());
    groupDescriptionLabel.setText(group.getDescription());
    searchBox.textProperty().addListener(unused -> updateData());

    for (var table : List.of(tasksTable, groupLeaderBoard)) {
      table.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    }

    for (var column :
        List.of(
            taskTitleColumn,
            taskPriorityColumn,
            taskAssignedColumn,
            userColumn,
            levelColumn,
            roleColumn)) {
      column.setReorderable(false);
      column.setEditable(false);
      column.setResizable(false);
      column.setSortable(false);
      column.prefWidthProperty().bind(tasksTable.widthProperty().divide(3).subtract(2));
    }

    // ------------------------  left  ------------------------
    taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    taskPriorityColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getPriority().getDisplayName()));
    taskAssignedColumn.setCellValueFactory(
        param -> {
          var userIds = param.getValue().getAssignedUsers();

          return new SimpleStringProperty(
              userIds.isEmpty()
                  ? "None"
                  : TiedyApp.getDataAccessFacade()
                      .getUserNamesByIds(List.copyOf(userIds))
                      .toString());
        });

    taskTitleColumn.setSortable(true);
    taskPriorityColumn.setSortable(true);

    // ------------------------  right  ------------------------
    userColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getUsername()));
    levelColumn.setCellValueFactory(
        param -> new ReadOnlyObjectWrapper<>(param.getValue().getCurrentLevel()));
    levelColumn.setSortable(true);
    roleColumn.setCellValueFactory(
        param ->
            new SimpleStringProperty(group.isAdmin(param.getValue().getId()) ? "Admin" : "Member"));
    roleColumn.setSortable(true);

    addButton.setOnAction(
        unused -> {
          try {
            tasksTable
                .getSelectionModel()
                .getSelectedItem()
                .assignUser(groupLeaderBoard.getSelectionModel().getSelectedItem().getId());

            TiedyApp.getDataAccessFacade()
                .updateTask(tasksTable.getSelectionModel().getSelectedItem());
            TiedyApp.getDataChangeNotifier().notifyObservers();
          } catch (NullPointerException e) {
            AlertFactory.generateWarningAlert("Please select both a task and a user to assign.")
                .showAndWait();
          }
        });

    removeButton.setOnAction(
        unused -> {
          try {
            tasksTable
                .getSelectionModel()
                .getSelectedItem()
                .unassignUser(groupLeaderBoard.getSelectionModel().getSelectedItem().getId());
            TiedyApp.getDataAccessFacade()
                .updateTask(tasksTable.getSelectionModel().getSelectedItem());
            TiedyApp.getDataChangeNotifier().notifyObservers();
          } catch (NullPointerException e) {
            AlertFactory.generateWarningAlert("Please select both a task and a user to unassign.")
                .showAndWait();
          }
        });

    updateData();
  }

  private void updateData() {
    // left
    var groupTasks = TiedyApp.getDataAccessFacade().getActiveTasksByGroupId(group.getId());
    if (!searchBox.getText().isBlank()) {
      groupTasks =
          groupTasks.stream()
              .filter(
                  task -> task.getTitle().toLowerCase().contains(searchBox.getText().toLowerCase()))
              .toList();
    }
    tasksTable.setItems(FXCollections.observableArrayList(groupTasks));

    // enable double click to show the edit dialog
    tasksTable.setRowFactory(
        tv -> {
          TableRow<Task> row = new TableRow<>();

          row.setOnMouseClicked(
              event -> {
                if (event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2
                    && !row.isEmpty()) {
                  Task selectedTask = row.getItem();

                  DialogFactory.launchEditTaskDialog(
                      selectedTask,
                      updatedTask -> {
                        if (TiedyApp.getDataAccessFacade().updateTask(updatedTask) != null) {
                          TiedyApp.getDataChangeNotifier().notifyObservers();
                        } else {
                          AlertFactory.generateWarningAlert("Failed to update task").showAndWait();
                        }
                      });
                }
              });
          return row;
        });

    // right
    var groupMembers =
        TiedyApp.getDataAccessFacade()
            .filterUsers(user -> group.getMembers().containsKey(user.getId()));
    groupLeaderBoard.setItems(FXCollections.observableArrayList(groupMembers));
  }

  @FXML
  private void onNewTaskButtonPress() {
    DialogFactory.launchTaskCreationDialog(
        createdTask -> {
          if (TiedyApp.getDataAccessFacade().addTask(createdTask) != null) {

            if (TiedyApp.getDataAccessFacade()
                .assignTaskToUser(createdTask.getId(), group.getId())) {

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
  private void onManageMembersButtonPress() {}
}
