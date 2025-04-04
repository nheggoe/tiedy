package edu.ntnu.idi.bidata.tiedy;

import edu.ntnu.idi.bidata.tiedy.backend.DataAccessFacade;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * <h2>The entry point for the {@code Tiedy} application.</h2>
 *
 * <p>This application uses JDK 21.
 *
 * <p>When using Java Development Kits (JDKs) that does not include the built-in JavaFX library,
 * you'll see an {@code Unsupported JavaFX configuration} warning if you run this application
 * through the IntelliJ IDEA interface.
 *
 * <p>To suppress this warning, there are a few possible solutions:
 * <li>Use a JDK with builtin JavaFX library with Intellij, such as ZuluFX, or Liberica Full JDKs,
 *     which can be changed in the {@code Project Structure} setting.
 * <li>Recommended: Run the project using Maven: either straight from the terminal with Maven
 *     Wrapper {@code ./mvnw -q} or using the Maven plugin to Execute Maven Goal {@code clean
 *     javafx:run}
 *
 * @author Nick Heggø
 * @version 2025.04.03
 */
public class Launcher {

  /**
   * On start, it will Launch the JavaFX main stage.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    try (ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor()) {
      scheduledService.scheduleWithFixedDelay(
          DataAccessFacade.getInstance(), 10, 10, TimeUnit.SECONDS);
      TiedyApp.main(args);
      DataAccessFacade.getInstance().run(); // save before shutdown
    }
  }
}
