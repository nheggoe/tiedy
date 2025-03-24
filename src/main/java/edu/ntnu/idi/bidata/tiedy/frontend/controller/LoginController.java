package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.StringChecker;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller class responsible for the login functionality of the application. This class handles
 * user authentication by verifying the entered credentials against a persisted JSON user dataset.
 * It provides methods to navigate between the login and registration pages and manages the user
 * session upon successful authentication.
 *
 * <p>This controller relies on JavaFX components for handling user input and displaying information
 * such as error or success messages. Authentication involves validation of both the username and
 * password fields.
 *
 * @author Nick Heggø
 * @version 2025.03.24
 */
public class LoginController {
  private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

  private final JsonService<User> userService = new JsonService<>(User.class);

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;

  /**
   * Handles the user login process. This method retrieves the username and password inputs,
   * validates them, and checks the provided credentials against the stored users. If the login
   * fails due to invalid input or credential verification, appropriate alerts are displayed.
   */
  @FXML
  public void loginUser() {
    try {
      String username = usernameField.getText();
      StringChecker.assertValidString(username, "username");
      assertValidUserName(username);
      String password = passwordField.getText();
      if (password == null || password.isBlank()) {
        throw new IllegalArgumentException("Password cannot be empty");
      }
      Stream<User> users = userService.loadJsonAsStream();
      validateCredential(users, username, password);
    } catch (IllegalArgumentException e) {
      JavaFxFactory.generateWarningAlert(e.getMessage()).showAndWait();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Cannot load users", e);
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Error while loading users");
      alert.show();
    }
  }

  private static void assertValidUserName(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException("Username cannot be empty");
    }
  }

  /**
   * Navigates the user to the register page.
   *
   * <p>This method triggers the scene transition to the register page by using the SceneManager
   * from the TiedyApp class. The navigation process involves switching the current scene to the one
   * associated with the {@code SceneName.REGISTER} enumeration value.
   *
   * <p>It is designed to be invoked from the frontend layer, typically by an event listener tied to
   * the user interface, allowing users to access the registration functionality of the application.
   */
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
