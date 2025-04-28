package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class GroupMemberDialogController {

  @FXML private TableView<User> groupMemberTable;

  private Group group;

  /** Initializes the dialog components. */
  @FXML
  public void initialize() {}

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public void validateInput() {}
}
