package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class GroupController implements DataController {

  @FXML private BorderPane root;

  @FXML
  public void initialize() {
    // TODO check if user has a group, if yes shows the group, else a button for the user to create
    // a group
  }

  @Override
  public void updateData() {
    // observer pattern
  }
}
