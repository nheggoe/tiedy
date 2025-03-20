package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import javafx.fxml.FXML;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.scene.control.Label;


/**
 * The ProfileController class controls the PROFILE scene. This scene is used to view information about the current profile.
 * @author Odin Arvhage
 * @version 20.03.2025
 */
public class ProfileController {
    @FXML private Label nameLabel;

    /**
     * The initalize method is called when the PROFILE scene is loaded.
     * It initializes the display labels in the scene with information.
     */
    @FXML
    public void initialize() {
        displayName();
        displayTasks();
    }

    /**
     * The displayName method gets the name of the user that is currently logged in.
     * It then sets it to the corresponding label.
     */
    public void displayName() {
        nameLabel.setText(UserSession.getInstance().getCurrentUser().getUsername());
    }

    /**
     * onBackButtonPress activates when the back button is pressed in the PROFILE scene.
     * It switches the scene to the MAIN scene.
     */
    @FXML
    public void onBackButtonPress() {
        TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
    }

    /**
     * The displayTasks method gets and displays the amount of tasks this user has completed.
     */
    @FXML
    public void displayTasks() {
        // get amount of tasks done from json
    }

    /**
     * The onLogoutButtonPress method is called when the Logout button is pressed in the PROFILE scene.
     * It ends the current session of the user, and then switches the scene to the MAIN scene.
     */
    @FXML
    public void onLogoutButtonPress() {
        UserSession.getInstance().setCurrentUser(null);
        TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
    }
}

