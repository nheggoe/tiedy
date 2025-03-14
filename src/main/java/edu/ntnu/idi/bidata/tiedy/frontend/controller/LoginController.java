package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
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
  public void registerUser(String userName, String password, String email) {
    if (userName == null || userName.isBlank()) {
      throw new IllegalArgumentException("Username cannot be empty");
    }
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email cannot be empty");
    }

    try {
      Stream<User> userStream = userService.loadJsonAsStream();
      boolean isUserNameTaken = userStream.anyMatch(user -> user.getUsername().equals(userName));
      if (isUserNameTaken) {
        throw new IllegalArgumentException("Username already taken");
      }
      User newUser = new User(userName, password, email);
      Stream<User> updatedStream =
          Stream.concat(userService.loadJsonAsStream(), Stream.of(newUser));
      userService.writeCollection(updatedStream);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot save user", e);
    }
  }

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
            if (isCorrectPassword) {
              UserSession.createSession(user);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Login successful");
              alert.setContentText("Login successful");
              alert.showAndWait();
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
