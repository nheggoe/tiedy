package edu.ntnu.idi.bidata.tiedy.frontend.controller;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import javafx.fxml.FXML;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import javafx.scene.control.Label;


public class ProfileController {
    @FXML private Label nameLabel;

    @FXML
    public void initialize() {
        displayName();
        displayTasks();
    }

    public void displayName() {
        nameLabel.setText(UserSession.getInstance().getCurrentUser().getUsername());
    }

    /**
     * onBackButtonPress activates when the back button is pressed in the PROFILE scene.
     * It
     */
    @FXML
    public void onBackButtonPress() {
        TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
    }

    @FXML
    public void displayTasks() {
        // get amount of tasks done from json
    }

    @FXML
    public void onLogoutButtonPress() {
        UserSession.getInstance().setCurrentUser(null);
        TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
    }
}

