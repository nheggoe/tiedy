package edu.ntnu.idi.bidata.tiedy.frontend.navigation;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The SceneManager class is responsible for managing and switching scenes in the JavaFX
 * application. By swapping scenes, it enables us the ability to use a single application window.
 *
 * <p>This class is typically used as a singleton, allowing centralized control of scene
 * transitions.
 *
 * @author Nick Heggø
 * @version 2025.04.07
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
      Scene oldScene = primaryStage.getScene();
      double width = (oldScene != null) ? oldScene.getWidth() : primaryStage.getWidth();
      double height = (oldScene != null) ? oldScene.getHeight() : primaryStage.getHeight();

      Scene newScene = new Scene(FXMLLoader.load(sceneName.getSceneURL()), width, height);
      primaryStage.setScene(newScene);
      primaryStage.show();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load FXML file: " + sceneName.getSceneURL(), e);
    }
  }
}
