package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.component.CreateGroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.component.GroupTab;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/**
 * The {@code GroupController} class is responsible for managing and updating the UI related to user
 * groups. It extends functionality provided by {@code DataController}, subscribing to data change
 * events and updating the display accordingly.
 *
 * <p>This controller handles the initialization and management of the {@code groupTabPane},
 * ensuring that the UI reflects the current state of user group data retrieved from the
 * application's backend.
 *
 * @author Nick Heggø
 * @version 2025.04.26
 */
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
        .map(GroupTab::new)
        .forEach(groupTabPane.getTabs()::addAll);

    groupTabPane.getTabs().add(new CreateGroupTab());
  }
}
