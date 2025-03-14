package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
  private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

  private final JsonService userService = new JsonService(User.class);

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;

  public LoginController() {}

  @FXML
  public void loginUser() {
    try {
      String username = usernameField.getText();
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username cannot be empty");
      }
      String password = passwordField.getText();
      if (password == null || password.isBlank()) {
        throw new IllegalArgumentException("Password cannot be empty");
      }
      Stream<User> users = userService.loadJsonAsStream();
      validateCredential(users, username, password);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Cannot load users", e);
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Error while loading users");
      alert.show();
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setContentText(e.getMessage());
      alert.show();
    }
  }

  @FXML
  public void goToRegisterPage() {
    TiedyApp.getSceneManager().switchScene(SceneName.REGISTER);
  }

  private void validateCredential(Stream<User> users, String username, String password) {
    Optional<User> foundUser =
        users.filter(user -> user.getUsername().equals(username)).findFirst();
    if (foundUser.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("User not found");
      alert.setContentText("User not found, please try again");
      alert.showAndWait();
    } else {
      foundUser.ifPresent(
          user -> {
            boolean isCorrectPassword =
                PasswordUtil.checkPassword(passwordField.getText(), user.getPassword());
            LOGGER.info("Checking password for user " + user.getUsername());
            LOGGER.info("Provided password: " + password);
            LOGGER.info("Stored password: " + user.getPassword());
            LOGGER.info("Password check result: " + isCorrectPassword);
            if (isCorrectPassword) {
              UserSession.createSession(user);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Login successful");
              alert.setContentText("Login successful");
              alert.showAndWait();
              TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
            } else {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Incorrect password");
              alert.setContentText("Incorrect password, please try again");
              alert.showAndWait();
            }
          });
    }
  }
}
