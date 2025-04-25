package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.StringChecker;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Controller class responsible for handling user registration functionality in the application.
 * This class manages the user input fields and performs validation during the registration process.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class RegisterController {

  @FXML private BorderPane root;
  @FXML private VBox innerDiv;
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField passwordRepeatField;

  @FXML
  private void initialize() {
    innerDiv.prefWidthProperty().bind(root.widthProperty().multiply(0.6));
    innerDiv.prefHeightProperty().bind(root.widthProperty().multiply(0.4));
  }

  /**
   * Handles the user registration process. This method validates the input provided by the user,
   * checks for duplicate usernames, and registers the user if all criteria are met. In case of
   * errors, appropriate alerts are displayed to the user. Successfully registered users are stored
   * in the JSON data source.
   */
  @FXML
  public void registerUser() {
    try {
      StringChecker.assertStringNotNullOrEmpty(usernameField.getText(), "username");
      StringChecker.assertStringNotNullOrEmpty(passwordField.getText(), "password");
      StringChecker.assertStringNotNullOrEmpty(passwordRepeatField.getText(), "password repeat");

      String username = usernameField.getText().strip();
      String password = passwordField.getText().strip();
      String passwordRepeat = passwordRepeatField.getText().strip();

      if (!password.equals(passwordRepeat)) {
        throw new IllegalArgumentException("Passwords do not match");
      }

      User registeredUser =
          TiedyApp.getDataAccessFacade().registerUser(new User(username, password));

      if (registeredUser == null) {
        throw new IllegalArgumentException("Username already taken");
      }

      AlertFactory.generateInfoAlert("Registration successful", "Registration successful")
          .showAndWait();

      backToLogin();

    } catch (IllegalArgumentException e) {
      AlertFactory.generateWarningAlert(e.getMessage()).showAndWait();
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
}
