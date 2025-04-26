package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.controller.component.CreateGroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.controller.component.GroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
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

  private Tab createTab(Group group) {
    Objects.requireNonNull(group);
    return new Tab(group.getName(), null);
  }

  private Tab createNewGroupTab() {
    try {
      return new Tab("New Group", FXMLLoader.load(Path.of("src/main/resources").toUri().toURL()));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load CreateGroup.fxml", e);
    }
  }
}
