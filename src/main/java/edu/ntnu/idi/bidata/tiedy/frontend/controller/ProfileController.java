package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * The ProfileController class controls the PROFILE scene. This scene is used to view information
 * about the current profile.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.04.09
 */
public class ProfileController implements DataController {

  @FXML private Label usernameLabel;
  @FXML private Label taskCountLabel;
  @FXML private Label currentLevelLabel;
  @FXML private ProgressBar expBar;
  @FXML private Label currentExpLabel;

  /**
   * The initialize method is called when the PROFILE scene is loaded. It initializes the display
   * labels in the scene with information.
   */
  @Override
  public void initialize() {
    register();
    updateData();
  }

  @Override
  public void updateData() {
    updateUsernameLabel();
    updateTaskCountLabel();
    updateCurrentLevelLabel();
    updateCurrentExpLabel();
    updateExpBar();
  }

  /**
   * onBackButtonPress activates when the back button is pressed in the PROFILE scene. It switches
   * the scene to the MAIN scene.
   */
  @FXML
  public void onBackButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }

  /**
   * The displayName method gets the name of the user that is currently logged in. It then sets it
   * to the corresponding label.
   */
  private void updateUsernameLabel() {
    usernameLabel.setText(UserSession.getCurrentUsername());
  }

  /** The displayTasks method gets and displays the number of tasks this user has completed. */
  private void updateTaskCountLabel() {
    taskCountLabel.setText(String.valueOf(UserSession.getCompletedTaskCount()));
  }

  private void updateCurrentLevelLabel() {
    currentLevelLabel.setText(String.valueOf(UserSession.getCurrentLevel()));
  }

  private void updateCurrentExpLabel() {
    currentExpLabel.setText(
        "%d XP / %d XP"
            .formatted(UserSession.getCurrentExperience(), UserSession.getExperienceThreshold()));
  }

  private void updateExpBar() {
    expBar.setProgress(
        (double) UserSession.getCurrentExperience() / UserSession.getExperienceThreshold());
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
