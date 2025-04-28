package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.component.CreateGroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.component.GroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class GroupController implements DataController {

  private static final Logger LOGGER = Logger.getLogger(GroupController.class.getName());

  @FXML private TabPane groupTabPane;

  @FXML
  public void initialize() {
    register();
    updateData();
  }

  @Override
  public void updateData() {
    groupTabPane.getTabs().clear();
    TiedyApp.getDataAccessFacade().getGroupsByUserId(UserSession.getCurrentUserId()).stream()
        .map(GroupTab::new)
        .forEach(groupTabPane.getTabs()::addAll);

    groupTabPane.getTabs().add(new CreateGroupTab());
  }
}
