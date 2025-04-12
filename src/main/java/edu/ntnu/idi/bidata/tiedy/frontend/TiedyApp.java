package edu.ntnu.idi.bidata.tiedy.frontend;

import edu.ntnu.idi.bidata.tiedy.backend.DataAccessFacade;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneManager;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.InvalidSessionException;
import edu.ntnu.idi.bidata.tiedy.frontend.util.AlertFactory;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DataChangeNotifier;
import java.awt.*;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * MainApplication serves as the entry point for a JavaFX application. It initializes the JavaFX
 * application stage and sets up the user interface components. This class extends the Application
 * class from the JavaFX framework, providing the necessary lifecycle methods such as start.
 *
 * @author Nick Heggø
 * @version 2025.04.07
 */
public class TiedyApp extends Application {

  private static final Logger LOGGER = Logger.getLogger(TiedyApp.class.getName());

  private static final DataAccessFacade dataAccessFacade = DataAccessFacade.getInstance();
  private static final DataChangeNotifier dataChangeNotifier = DataChangeNotifier.getInstance();
  private static final SceneManager sceneManager = SceneManager.getInstance();

  /**
   * The main entry point for the JavaFX application. This method launches the JavaFX runtime and
   * initializes the application.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    try {
      launch(args);
    } catch (InvalidSessionException e) {
      AlertFactory.generateErrorAlert(e.getMessage()).showAndWait();
      sceneManager.switchScene(SceneName.LOGIN);
    }
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

  public static DataChangeNotifier getDataChangeNotifier() {
    return dataChangeNotifier;
  }

  @Override
  public void start(Stage primaryStage) {
    init(primaryStage);
  }

  public void init(Stage primaryStage) {
    if (Taskbar.isTaskbarSupported()) {
      var taskbar = Taskbar.getTaskbar();

      if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        var dockIcon =
            defaultToolkit.getImage(
                getClass().getResource("/edu/ntnu/idi/bidata/tiedy/images/icon_512@2x.png"));
        taskbar.setIconImage(dockIcon);
      }
    }
    primaryStage.setTitle("Tiedy");
    primaryStage
        .getIcons()
        .add(new Image("edu/ntnu/idi/bidata/tiedy/images/TiedyApplicationIcon.png"));
    primaryStage.setMinWidth(700);
    primaryStage.setMinHeight(500);
    primaryStage.setOnCloseRequest(unused -> onClose());
    sceneManager.setPrimaryStage(primaryStage);
    sceneManager.switchScene(SceneName.LOGIN);
  }

  public static void onClose() {
    AlertFactory.generateConfirmationAlert(
        "Exit", "Are you sure you want to exit the application?");
    try {
      Alert exitConfirmation =
          AlertFactory.generateConfirmationAlert(
              "Exit", "Are you sure you want to exit the application?");
      Optional<ButtonType> result = exitConfirmation.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        LOGGER.info("Thank you for using Tiedy!");
        LOGGER.info("Exiting program...");
        Platform.exit();
      } else {
        exitConfirmation.close();
      }

    } catch (IllegalStateException e) {
      AlertFactory.generateErrorAlert(e.getMessage()).showAndWait();
    }
  }
}
