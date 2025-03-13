package edu.ntnu.idi.bidata.tiedy;

import edu.ntnu.idi.bidata.tiedy.frontend.JavaFXApp;

/**
 *
 *
 * <h2>The entry point for the {@code Tiedy} application.</h2>
 *
 * This application uses JDK 21.
 *
 * <p>When using Java Development Kits (JDKs) that does not include the built-in JavaFX library,
 * you'll see an 'Unsupported JavaFX configuration' warning if you run this application through the
 * IntelliJ IDEA interface.
 *
 * <p>To suppress this warning, there are a few possible solutions:
 * <li>Use a JDK with builtin JavaFX library with Intellij, such as ZuluFX, or Liberica Full JDKs,
 *     which can be changed in the Project Structure setting.
 * <li>Recommended: Run the project using Maven: {@code mvn -q clean javafx:run}, either straight
 *     from the terminal or using the Maven plugin to Execute Maven Goal
 *
 * @author Nick Heggø
 * @version 2025.02.28
 */
public class Launcher {

  /**
   * On start, it will Launch the JavaFX main stage.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    JavaFXApp.main(args);
  }
}
