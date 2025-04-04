package edu.ntnu.idi.bidata.tiedy.frontend;

import edu.ntnu.idi.bidata.tiedy.backend.DataAccessFacade;
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
import java.util.logging.Logger;


/**
 * MainApplication serves as the entry point for a JavaFX application. It initializes the JavaFX
 * application stage and sets up the user interface components. This class extends the Application
 * class from the JavaFX framework, providing the necessary lifecycle methods such as start.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class TiedyApp extends Application {

  private static final DataAccessFacade dataAccessFacade = DataAccessFacade.getInstance();
  private static final Logger LOGGER = Logger.getLogger(TiedyApp.class.getName());
  private static SceneManager sceneManager;

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

  public static DataAccessFacade getDataAccessFacade() {
    return dataAccessFacade;
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Tiedy");
    primaryStage
        .getIcons()
        .add(new Image("edu/ntnu/idi/bidata/tiedy/images/TiedyApplicationIcon.png"));
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
