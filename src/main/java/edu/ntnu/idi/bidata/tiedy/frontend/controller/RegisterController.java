package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class responsible for handling user registration functionality in the application.
 * This class manages the user input fields and performs validation during the registration process.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class RegisterController {

  private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField passwordRepeatField;

  /**
   * Handles the user registration process. This method validates the input provided by the user,
   * checks for duplicate usernames, and registers the user if all criteria are met. In case of
   * errors, appropriate alerts are displayed to the user. Successfully registered users are stored
   * in the JSON data source.
   */
  @FXML
  public void registerUser() {
    try {
      String username = usernameField.getText();
      if (username == null || username.isBlank()) {
        throw new IllegalArgumentException("Username cannot be empty");
      }
      boolean isUserNameTaken =
          TiedyApp.getUserJsonService()
              .loadJsonAsStream()
              .anyMatch(user -> Objects.equals(user.getUsername(), username));
      if (isUserNameTaken) {
        throw new IllegalArgumentException("Username already taken");
      }
      User user = validateAndCreateUser(username);
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Registration successful");
      alert.setContentText("Registration successful");
      alert.showAndWait();
      new JsonService<>(User.class).addItem(user);
      backToLogin();
    } catch (IllegalArgumentException e) {
      JavaFxFactory.generateWarningAlert(e.getMessage()).showAndWait();
    }
  }

  /**
   * Navigates the user back to the login scene.
   *
   * <p>This method switches the application's current scene to the login interface, enabling the
   * user to return to the login page. It uses the application's SceneManager to handle the scene
   * transition and applies the pre-defined FXML configuration for the login scene.
   */
  @FXML
  public void backToLogin() {
    TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
  }

  private User validateAndCreateUser(String username) {
    String password = passwordField.getText().strip();
    String passwordRepeat = passwordRepeatField.getText().strip();

    if (password.isBlank()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    if (passwordRepeat.isBlank()) {
      throw new IllegalArgumentException("Password repeat cannot be empty");
    }

    PasswordUtil.validatePasswordStrength(password);
    PasswordUtil.validatePasswordFormat(password);

    if (!password.equals(passwordRepeat)) {
      throw new IllegalArgumentException("Passwords do not match");
    }
    return new User(username, PasswordUtil.hashPassword(password));
  }
}
