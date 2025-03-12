package edu.ntnu.idi.bidata.tiedy.frontend.stage;

import javafx.stage.Stage;

public class LoginPage {

  private Stage stage;

  public LoginPage() {}

  public Stage getStage() {
    return stage;
  }

  private void initStage() {
    stage = new Stage();
    stage.setTitle("Login");
    stage.setWidth(300);
    stage.setHeight(200);
  }
}
