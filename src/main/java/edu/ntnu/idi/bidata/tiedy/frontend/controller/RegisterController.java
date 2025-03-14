package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
  private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());
  private final JsonService userService = new JsonService(User.class);

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField passwordRepeatField;

  @FXML
  public void registerUser() {
    try {
      String username = usernameField.getText();
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username cannot be empty");
      }
      Stream<User> userStream = userService.loadJsonAsStream();
      boolean isUserNameTaken = userStream.anyMatch(user -> user.getUsername().equals(username));
      if (isUserNameTaken) {
        throw new IllegalArgumentException("Username already taken");
      }
      User user = getUser(username);
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Registration successful");
      alert.setContentText("Registration successful");
      alert.showAndWait();
      Stream<User> updatedStream = Stream.concat(userService.loadJsonAsStream(), Stream.of(user));
      new JsonService(User.class).writeCollection(updatedStream);
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setContentText(e.getMessage());
      alert.show();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Cannot save user", e);
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Error while saving user");
      alert.show();
    }
  }

  @FXML
  public void backToLogin() {
    TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
  }

  private User getUser(String username) {
    String password = passwordField.getText();
    String passwordRepeat = passwordRepeatField.getText();

    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    if (passwordRepeat == null || passwordRepeat.isBlank()) {
      throw new IllegalArgumentException("Password repeat cannot be empty");
    }
    if (!password.equals(passwordRepeat)) {
      throw new IllegalArgumentException("Passwords do not match");
    }
    return new User(username, password);
  }
}
