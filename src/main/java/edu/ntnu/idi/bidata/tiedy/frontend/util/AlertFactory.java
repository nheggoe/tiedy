package edu.ntnu.idi.bidata.tiedy.frontend.util;

import javafx.scene.control.Alert;

/**
 * A utility class designed for generating JavaFX alert dialogs of various types, such as
 * information, warning, error, and confirmation. It provides static methods to simplify the
 * creation of alerts with predefined or custom titles and messages.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 */
public class AlertFactory {
  private AlertFactory() {}

  /**
   * Generates a JavaFX information alert with a predefined title ("Info") and the provided content
   * message. This method is intended to streamline the creation of information alert dialogs in
   * JavaFX applications.
   *
   * @param title the title of the alert to be displayed
   * @param message the content message to be displayed in the alert dialog
   * @return an Alert object of type INFORMATION with the specified content message
   */
  public static Alert generateInfoAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    return alert;
  }

  /**
   * Creates and returns a JavaFX warning alert with a predefined title ("Warning") and the provided
   * content message. This method is designed to simplify the generation of warning alert dialogs in
   * JavaFX applications.
   *
   * @param title the title of the alert to be displayed
   * @param message the content message to be displayed in the warning alert
   * @return an Alert object of type WARNING with the specified message as content
   */
  public static Alert generateWarningAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setContentText(message);
    return alert;
  }

  /**
   * Creates and returns a JavaFX warning alert with a predefined title ("Warning") and the provided
   * content message. This method is designed to simplify the creation of warning alert dialogs in
   * JavaFX applications.
   *
   * @param message the content message to be displayed in the warning alert
   * @return an Alert object of type WARNING with the specified message as content
   */
  public static Alert generateWarningAlert(String message) {
    return generateWarningAlert("Warning", message);
  }

  /**
   * Creates and returns a JavaFX error alert with a predefined title ("Error") and the provided
   * content message. This method is designed to simplify the creation of error alert dialogs in
   * JavaFX applications.
   *
   * @param title the title of the alert to be displayed
   * @param message the content message to be displayed in the error alert
   * @return an Alert object of type ERROR with the specified message as content
   */
  public static Alert generateErrorAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(message);
    return alert;
  }

  /**
   * Creates and returns a JavaFX error alert with a predefined title ("Error") and the provided
   * content message. This method simplifies the creation of error alert dialogs in JavaFX
   * applications.
   *
   * @param message the content message to be displayed in the error alert
   * @return an Alert object of type ERROR with a predefined title and the specified message as
   *     content
   */
  public static Alert generateErrorAlert(String message) {
    return generateErrorAlert("Error", message);
  }

  /**
   * Creates and returns a JavaFX confirmation alert with the specified title and message. This
   * method is designed to simplify the creation of confirmation alert dialogs in JavaFX
   * applications.
   *
   * @param title the title of the alert to be displayed
   * @param message the content message to be displayed in the confirmation alert
   * @return an Alert object of type CONFIRMATION with the specified title and message
   */
  public static Alert generateConfirmationAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setContentText(message);
    return alert;
  }
}
