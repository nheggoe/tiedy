package edu.ntnu.idi.bidata.tiedy.frontend.navigation;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SceneManager class is responsible for managing and switching scenes in a JavaFX application.
 * It encapsulates the logic necessary to load FXML files and update the primary stage with the
 * specified scene, enabling seamless navigation between different user interface views.
 *
 * <p>This class is typically used as a singleton, allowing centralized control of scene
 * transitions.
 *
 * @author Nick Heggø
 * @version 2025.03.19
 */
public class SceneManager {

  private final Stage primaryStage;

  /**
   * Constructs a SceneManager instance with a specified primary stage. This stage is used to manage
   * and display scenes in a JavaFX application.
   *
   * @param primaryStage the primary stage of the JavaFX application that the scenes will be managed
   *     on
   */
  public SceneManager(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  /**
   * Switches the current scene of the JavaFX application to the specified scene.
   *
   * <p>This method updates the primary stage with a new scene based on the provided {@code
   * SceneName}. It loads the FXML file associated with the specified scene and applies it to the
   * primary stage. If the FXML file cannot be located or loaded, an {@code IllegalStateException}
   * is thrown.
   *
   * @param sceneName the {@link SceneName} representing the scene to switch to and providing the
   *     path to the associated FXML file
   * @throws IllegalStateException if the FXML file for the specified scene cannot be loaded or
   *     located
   */
  public void switchScene(SceneName sceneName) {
    try {
      Parent root = FXMLLoader.load(sceneName.getSceneURL());
      Scene scene = new Scene(root, primaryStage.getWidth(), primaryStage.getHeight());

      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load FXML file: " + sceneName.getSceneURL(), e);
    }
  }
}
