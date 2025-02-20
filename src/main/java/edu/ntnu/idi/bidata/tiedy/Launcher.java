package edu.ntnu.idi.bidata.tiedy;

import edu.ntnu.idi.bidata.tiedy.frontend.stage.JavaFXApp;

/**
 * For some reason, for JDKs that came without the built-in JavaFX library
 * will have a problem when running directly from the {@link JavaFXApp} class.
 * Running from the {@link Launcher} class will bypass this mystery.
 * It is recommended to use {@code mvn -q clean javafx:run} to start the program.
 *
 * @author Nick Heggø
 * @version 2025.02.08
 */
public class Launcher {

  /**
   * The main method serves as the entry point for the Java application, redirecting
   * execution to the {@code main} method of the {@link JavaFXApp} class.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    JavaFXApp.main(args);
  }
}
