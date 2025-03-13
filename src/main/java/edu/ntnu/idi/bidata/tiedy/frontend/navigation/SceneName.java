package edu.ntnu.idi.bidata.tiedy.frontend.navigation;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * The Scene enum represents different scenes in the application. Each instance of the enum
 * corresponds to a specific user interface scene defined by an associated FXML file. The enum
 * provides the path to the FXML file as a URL for loading the scene in a JavaFX application.
 *
 * <p>Each scene is linked with a file name that determines the FXML file's location within the
 * resources folder. The file paths are formatted and resolved to URLs during initialization.
 *
 * <p>This enum is typically used in conjunction with a SceneManager class to switch between
 * different stages or views of the application.
 *
 * @author Nick Heggø
 * @version 2025.03.13
 */
public enum SceneName {
  LOGIN("Login"),
  MAIN("Main"),
  REGISTER("Register"),
  TASK("Task");

  private static final String FXML_PATH =
      "src/main/resources/edu/ntnu/idi/bidata/tiedy/fxml/%s.fxml";

  private final URL path;

  SceneName(String fileName) {
    try {
      this.path = Path.of(FXML_PATH.formatted(fileName)).toUri().toURL();
    } catch (MalformedURLException e) {
      throw new IllegalStateException("Invalid FXML path: " + FXML_PATH.formatted(fileName), e);
    }
  }

  /**
   * Retrieves the URL path associated with the FXML file for this scene.
   *
   * @return the URL path to
   */
  public URL getPath() {
    return path;
  }
}
