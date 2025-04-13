package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.io.IOException;
import java.nio.file.Path;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jspecify.annotations.NonNull;

public class GroupController implements DataController {

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
        .map(this::createTab)
        .forEach(groupTabPane.getTabs()::add);

    Tab newGroupTab = createNewGroupTab();
    groupTabPane.getTabs().add(newGroupTab);
    // groupTabPane
    //     .getSelectionModel()
    //     .selectedItemProperty()
    //     .addListener(
    //         (obs, oldTab, newTab) -> {
    //           if (newTab == newGroupTab) {}
    //         });
  }

  private Tab createTab(@NonNull Group group) {
    return new Tab(group.getName(), null);
  }

  private Tab createNewGroupTab() {
    try {
      return new Tab(
          "New Group",
          FXMLLoader.load(
              Path.of(
                      "src/main/resources/edu/ntnu/idi/bidata/tiedy/fxml/component/CreateGroup.fxml")
                  .toUri()
                  .toURL()));
    } catch (IOException e) {
      throw new IllegalStateException("Could not load CreateGroup.fxml", e);
    }
  }
}
