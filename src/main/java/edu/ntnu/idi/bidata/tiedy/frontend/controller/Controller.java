package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import javafx.fxml.FXML;

/**
 * Represents the base structure for all controller classes in the application. Controllers are
 * responsible for managing user interface interactions and connecting them with the application's
 * backend logic.
 *
 * <p>Implementing classes are expected to handle initialization of their respective components and
 * define methods that correspond to specific user actions or application events.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 */
public interface Controller {

  /**
   * "FXMLLoader will now automatically call any suitably annotated no-arg initialize() method
   * defined by the controller. It is recommended that the injection approach be used whenever
   * possible."
   *
   * @see javafx.fxml.Initializable
   */
  @FXML
  void initialize();
}
