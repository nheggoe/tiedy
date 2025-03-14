package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import javafx.fxml.FXML;

public class MainController {
  @FXML
  public void onLoginButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
  }
}
