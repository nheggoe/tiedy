package edu.ntnu.idi.bidata.tiedy.frontend;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneManager;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.util.JavaFxFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

import static edu.ntnu.idi.bidata.tiedy.backend.util.FileUtil.LOGGER;

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
    primaryStage.getIcons().add(new Image("images/TiedyApplicationIcon.png"));
    primaryStage.setResizable(false);
    primaryStage.setOnCloseRequest(event -> onClose());
    sceneManager = new SceneManager(primaryStage);
    sceneManager.switchScene(SceneName.LOGIN);
  }

  public static void onClose() {
    JavaFxFactory.generateConfirmationAlert("Exit","Are you sure you want to exit the application?");
      try {
        Alert exitConfirmation = JavaFxFactory.generateConfirmationAlert("Exit","Are you sure you want to exit the application?");
        Optional<ButtonType> result = exitConfirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
          LOGGER.info("Thank you for using Tiedy!");
          LOGGER.info("Exiting program...");
          Platform.exit();
        }
        else {
          exitConfirmation.close();
        }

      }
      catch (IllegalStateException e) {
        JavaFxFactory.generateErrorAlert(e.getMessage()).showAndWait();
      }
    }

}
