package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import javafx.fxml.FXML;

public class MenuBarController {

  @FXML
  public void onHomeButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }

  @FXML
  public void onStatisticsButtonPress() {}

  @FXML
  public void onNewTaskButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.TASK);
  }

  @FXML
  public void onGroupButtonPress() {}

  @FXML
  public void onProfileButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.PROFILE);
  }
}
