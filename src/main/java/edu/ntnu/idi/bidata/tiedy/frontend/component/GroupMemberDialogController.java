package edu.ntnu.idi.bidata.tiedy.frontend.component;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class GroupMemberDialogController {

  @FXML private TableView<User> groupMemberTable;
  @FXML private TableColumn<User, String> usernameTableColumn;
  @FXML private TableColumn<User, String> roleTableColumn;

  private Group group;

  /** Initializes the dialog components. */
  @FXML
  public void initialize() {
    usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    roleTableColumn.setCellValueFactory(
        param -> {
          var user = param.getValue();
          return new SimpleStringProperty(group.isAdmin(user.getId()) ? "Admin" : "Member");
        });
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

  public void validateInput() {}
}
