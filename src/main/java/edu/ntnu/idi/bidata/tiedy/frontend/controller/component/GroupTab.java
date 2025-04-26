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
  @FXML private TableView<Task> tasksTable;
  @FXML private TableColumn<Task, String> groupTasksColumn;

  @FXML private TableView<User> groupLeaderBoard;
  @FXML private TableColumn<User, String> usernameColumn;
  @FXML private TableColumn<User, Integer> levelColumn;

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

    List.of(groupTasksColumn, usernameColumn, levelColumn)
        .forEach(
            column -> {
              column.setReorderable(false);
              column.setEditable(false);
              column.setResizable(false);
              column.setSortable(false);
              column
                  .prefWidthProperty()
                  .bind(groupLeaderBoard.widthProperty().divide(2).subtract(1));
            });

    // ------------------------  left  ------------------------
    // table
    tasksTable.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    // column
    groupTasksColumn.prefWidthProperty().bind(tasksTable.widthProperty().subtract(2));
    groupTasksColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

    // ------------------------  right  ------------------------
    // table
    groupLeaderBoard.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    // colum
    usernameColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getUsername()));
    levelColumn.setCellValueFactory(
        param -> new ReadOnlyObjectWrapper<>(param.getValue().getCurrentLevel()));
    levelColumn.setSortable(true);

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
