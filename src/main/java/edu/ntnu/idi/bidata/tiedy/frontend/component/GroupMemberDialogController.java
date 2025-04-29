package edu.ntnu.idi.bidata.tiedy.frontend.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * A controller class that manages the dialog for viewing and modifying the members of a group.
 * underlying group and user data.
 *
 * @author Nick Heggø
 * @version 2025.04.29
 */
public class GroupMemberDialogController {

  @FXML private TableView<User> availableUserTable;
  @FXML private TableView<User> groupMemberTable;
  @FXML private TableColumn<User, String> availableUsernameColumn;
  @FXML private TableColumn<User, String> memberNameColumn;

  private Group group;

  /** Initializes the dialog components. */
  @FXML
  public void initialize() {
    availableUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    memberNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
    var users =
        TiedyApp.getDataAccessFacade().getUsersByIds(new ArrayList<>(group.getMembers().keySet()));
    groupMemberTable.setItems(FXCollections.observableArrayList(users));
  }

  @FXML
  public void onAddButtonPress() {
    try {
      User user = availableUserTable.getSelectionModel().getSelectedItem();
      if (user == null) {
        throw new IllegalArgumentException("Please select an user to add.");
      }
      group.addMember(user.getId(), false);
    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }

  @FXML
  public void onRemoveButtonPress() {
    try {
      User user = groupMemberTable.getSelectionModel().getSelectedItem();
      if (user == null) {
        throw new IllegalArgumentException("Please select an user to remove.");
      }
      if (group.isAdmin(user.getId())) {
        throw new IllegalArgumentException("You cannot remove an admin from the group.");
      }
      group.removeMember(user.getId());
    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }
}
