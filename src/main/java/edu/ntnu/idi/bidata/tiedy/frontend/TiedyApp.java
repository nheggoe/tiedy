package edu.ntnu.idi.bidata.tiedy.frontend;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneManager;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * MainApplication serves as the entry point for a JavaFX application. It initializes the JavaFX
 * application stage and sets up the user interface components. This class extends the Application
 * class from the JavaFX framework, providing the necessary lifecycle methods such as start.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class TiedyApp extends Application {

  private static SceneManager sceneManager;
  private static final JsonService<User> USER_JSON_SERVICE = new JsonService<>(User.class);
  private static final JsonService<Task> TASK_JSON_SERVICE = new JsonService<>(Task.class);

  /**
   * The main entry point for the JavaFX application. This method launches the JavaFX runtime and
   * initializes the application.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Retrieves the singleton instance of the SceneManager, which is responsible for handling scene
   * transitions and managing the application's primary stage.
   *
   * @return the singleton instance of SceneManager
   */
  public static SceneManager getSceneManager() {
    return sceneManager;
  }

  public static JsonService<User> getUserJsonService() {
    return USER_JSON_SERVICE;
  }

  public static JsonService<Task> getTaskJsonService() {
    return TASK_JSON_SERVICE;
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Tiedy");
    primaryStage
        .getIcons()
        .add(new Image("edu/ntnu/idi/bidata/tiedy/images/TiedyApplicationIcon.png"));
    primaryStage.setResizable(false);
    sceneManager = new SceneManager(primaryStage);
    sceneManager.switchScene(SceneName.LOGIN);
  }
}
