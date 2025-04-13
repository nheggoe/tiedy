package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.DataAccessFacade;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.StringChecker;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import javafx.fxml.FXML;
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
 * @version 2025.03.28
 */
public class LoginController {

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
      String username = usernameField.getText().strip();
      String plainTextPassword = passwordField.getText().strip();

      validateCredential(username, plainTextPassword);
    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
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

  private void validateCredential(String username, String plainTextPassword) {
    try {
      StringChecker.assertStringNotNullOrEmpty(username, "username");
      StringChecker.assertStringNotNullOrEmpty(plainTextPassword, "password");

      User foundUser =
          DataAccessFacade.getInstance()
              .authenticate(username, plainTextPassword)
              .orElseThrow(() -> new IllegalStateException("Invalid username or password"));

      UserSession.createSession(foundUser);

      AlertFactory.generateInfoAlert(
              "Login successful", "You are now logged in as %s".formatted(username))
          .showAndWait();

      TiedyApp.getSceneManager().switchScene(SceneName.MAIN);

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
    } catch (IllegalStateException e) {
      AlertFactory.generateErrorAlert(e.getMessage()).showAndWait();
    }
  }

  @FXML
  public void exit() {
    TiedyApp.onClose();
  }
}
