package edu.ntnu.idi.bidata.tiedy.frontend.controller.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class GroupTab extends Tab {

  @FXML private VBox root;
  @FXML private TextField searchBox;

  // left
  @FXML private TableView<Task> tasksTable;
  @FXML private TableColumn<Task, String> taskTitleColumn;
  @FXML private TableColumn<Task, String> taskPriorityColumn;
  @FXML private TableColumn<Task, String> taskAssignedColumn;

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
        param ->
            new SimpleStringProperty(
                param.getValue().getAssignedUsers().isEmpty() ? "None" : "Yes"));

    // ------------------------  right  ------------------------
    userColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getUsername()));
    levelColumn.setCellValueFactory(
        param -> new ReadOnlyObjectWrapper<>(param.getValue().getCurrentLevel()));
    levelColumn.setSortable(true);
    roleColumn.setCellValueFactory(
        param ->
            new SimpleStringProperty(group.isAdmin(param.getValue().getId()) ? "Admin" : "Member"));

    updateData();
  }

  private void updateData() {
    var groupTasks = TiedyApp.getDataAccessFacade().getActiveTasksByGroupId(group.getId());
    if (!searchBox.getText().isBlank()) {
      groupTasks =
          groupTasks.stream()
              .filter(task -> task.getTitle().contains(searchBox.getText()))
              .toList();
    }
    tasksTable.setItems(FXCollections.observableArrayList(groupTasks));
    var groupMembers =
        TiedyApp.getDataAccessFacade()
            .filterUsers(user -> group.getMembers().containsKey(user.getId()));
    groupLeaderBoard.setItems(FXCollections.observableArrayList(groupMembers));
  }
}
