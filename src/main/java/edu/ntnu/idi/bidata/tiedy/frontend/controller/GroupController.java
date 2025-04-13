package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.fxml.FXML;
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
    Tab tab = new Tab(group.getName());
    tab.setStyle("-fx-background-color: #5596F4;");
    tab.setText(group.getName());
    tab.getTabPane().setStyle("-fx-background-color: #5596F4;");
    tab.setClosable(false);
    // tab.setTooltip(new Tooltip(group.getName()));
    // HBox hBox = new HBox();
    // TableView<User> userTableView = new TableView<>();
    return tab;
  }

  private Tab createNewGroupTab() {
    Tab tab = new Tab("New Group");
    tab.setClosable(false);
    return tab;
  }
}
