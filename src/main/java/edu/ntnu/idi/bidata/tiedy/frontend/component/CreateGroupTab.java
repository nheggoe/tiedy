package edu.ntnu.idi.bidata.tiedy.frontend.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import java.io.IOException;
import java.util.List;
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

/**
 * @author Nick Heggø
 * @version 2025.04.18
 */
public class CreateGroupTab extends Tab {

  @FXML private VBox root;

  @FXML private TextField nameTextField;
  @FXML private TextField descriptionTextField;

  // ------------------------  Left Table  ------------------------
  @FXML private TableView<User> availableUserTableView;
  @FXML private TableColumn<User, String> usernameTableColumn;

  // ------------------------  Right Table  ------------------------
  @FXML private TableView<User> groupTableView;
  @FXML private TableColumn<User, String> groupMemberNameTableColumn;
  @FXML private TableColumn<User, String> groupMemberRoleTableColumn;

  private Group group;

  public CreateGroupTab() {
    setClosable(false);
    setText("+");
    setOnSelectionChanged(unused -> setText(isSelected() ? "Create New Group" : "+"));

    try {
      FXMLLoader loader =
          new FXMLLoader(
              getClass().getResource("/edu/ntnu/idi/bidata/tiedy/fxml/component/CreateGroup.fxml"));
      loader.setController(this);
      setContent(loader.load());
    } catch (IOException e) {
      throw new IllegalStateException("Could not load CreateGroup.fxml", e);
    }
  }

  @FXML
  public void initialize() {
    group = new Group(UserSession.getCurrentUserId());
    updateData();

    List.of(usernameTableColumn, groupMemberNameTableColumn, groupMemberRoleTableColumn)
        .forEach(
            column -> {
              column.setReorderable(false);
              column.setEditable(false);
              column.setResizable(false);
              column.setSortable(false);
              column
                  .prefWidthProperty()
                  .bind(availableUserTableView.widthProperty().divide(2).subtract(2));
            });

    // ------------------------  left  ------------------------
    // table
    availableUserTableView.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    // column
    usernameTableColumn
        .prefWidthProperty()
        .bind(availableUserTableView.widthProperty().subtract(2));
    usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    // ------------------------  right  ------------------------
    // table
    groupTableView.prefWidthProperty().bind(root.widthProperty().multiply(0.4));
    // colum
    groupMemberNameTableColumn.setCellValueFactory(
        param -> new SimpleStringProperty(param.getValue().getUsername()));
    groupMemberRoleTableColumn.setCellValueFactory(
        param ->
            new SimpleStringProperty(group.isAdmin(param.getValue().getId()) ? "Admin" : "Member"));
    groupMemberRoleTableColumn.setSortable(true);
  }

  public void updateData() {
    var availableUsers =
        TiedyApp.getDataAccessFacade()
            .filterUsers(user -> !group.getMembers().containsKey(user.getId()));
    availableUserTableView.setItems(FXCollections.observableArrayList(availableUsers));

    var groupMembers =
        TiedyApp.getDataAccessFacade()
            .filterUsers(user -> group.getMembers().containsKey(user.getId()));
    groupTableView.setItems(FXCollections.observableArrayList(groupMembers));

    availableUserTableView.getSelectionModel().selectFirst();
  }

  @FXML
  private void onAddButtonPress() {
    try {

      User user = availableUserTableView.getSelectionModel().getSelectedItem();
      if (user == null) {
        throw new IllegalArgumentException("Please select an user to add.");
      }

      group.addMember(user.getId(), false);
      updateData();

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }

  @FXML
  private void onRemoveButtonPress() {
    try {

      User user = groupTableView.getSelectionModel().getSelectedItem();
      if (user == null) {
        throw new IllegalArgumentException("Please select an user to remove.");
      }

      if (group.isAdmin(user.getId())) {
        throw new IllegalArgumentException("You cannot remove an admin from the group.");
      } else {
        group.removeMember(user.getId());
      }

      updateData();

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    } catch (IllegalStateException e) {
      AlertFactory.generateErrorAlert(e.getMessage()).showAndWait();
    }
  }

  @FXML
  private void onCreateGroupButtonPress() {
    try {
      String name = nameTextField.getText();
      if (name.isBlank()) {
        throw new IllegalArgumentException("Please provide a valid group name");
      }

      group.setName(name);
      group.setDescription(descriptionTextField.getText());
      if (TiedyApp.getDataAccessFacade().addGroup(group) == null) {
        throw new IllegalStateException("Failed to save the group, please try again.");
      }

      AlertFactory.generateInfoAlert("Success", "Successfully created group: " + group.getName())
          .showAndWait();

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    } catch (IllegalStateException e) {
      AlertFactory.generateErrorAlert(e.getMessage()).showAndWait();
    }
  }

  @FXML
  private void onCancelButtonPress() {

    TiedyApp.getSceneManager().switchScene(SceneName.GROUP);
  }
}
