package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import javafx.fxml.FXML;


/**
 * The ExitController class is used to manage the pop-up exit window that appears when the exit button is pressed.
 * @author odinarvhage
 * @version 31-03-2025
 */
public class ExitController {

    /**
     * This method is called when the "Yes" button is pressed. The application will then terminate.
     */
    @FXML
    public void onYesPressed() {
    System.exit(0);
}
    /**
     * This method is called when the "No" button is pressed. The pop-up window will then close.
     */
@FXML
    public void onNoPressed() {

}
}
