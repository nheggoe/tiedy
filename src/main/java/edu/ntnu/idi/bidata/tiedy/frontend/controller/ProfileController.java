package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The ProfileController class controls the PROFILE scene. This scene is used to view information
 * about the current profile.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.04.09
 */
public class ProfileController {

  @FXML private Label nameLabel;
  @FXML private Label tasksLabel;

  /**
   * The initialize method is called when the PROFILE scene is loaded. It initializes the display
   * labels in the scene with information.
   */
  @FXML
  public void initialize() {
    displayName();
    displayCompletedTasks();
  }

  /**
   * The displayName method gets the name of the user that is currently logged in. It then sets it
   * to the corresponding label.
   */
  public void displayName() {
    nameLabel.setText(UserSession.getCurrentUsername());
  }

  /**
   * onBackButtonPress activates when the back button is pressed in the PROFILE scene. It switches
   * the scene to the MAIN scene.
   */
  @FXML
  public void onBackButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }

  /** The displayTasks method gets and displays the number of tasks this user has completed. */
  @FXML
  public void displayCompletedTasks() {
    tasksLabel.setText(
        String.valueOf(
            TiedyApp.getDataAccessFacade()
                .getTasksByUserAndStatus(UserSession.getCurrentUserId(), Status.CLOSED)
                .size()));
  }

  /**
   * The onLogoutButtonPress method is called when the Logout button is pressed in the PROFILE
   * scene. It ends the current session of the user and then switches the scene to the MAIN scene.
   */
  @FXML
  public void onLogoutButtonPress() {
    UserSession.destroySession();
    TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
  }
}
