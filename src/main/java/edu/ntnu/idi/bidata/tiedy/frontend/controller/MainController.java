package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.view.MainView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
  private User user;
  private MainView mainView;

  @FXML private Label welcomeLabel;

  public MainController() {
    this.user = new User();
  }

  private void setUser(User user) {
    this.user = user;
  }

  private void setMainView(MainView mainView) {
    this.mainView = mainView;
  }

  public void test() {
    System.out.println("test");
  }
}
