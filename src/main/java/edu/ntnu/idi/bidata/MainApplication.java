package edu.ntnu.idi.bidata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * MainApplication serves as the entry point for a JavaFX application.
 * It initializes the JavaFX application stage and sets up the user interface components.
 * This class extends the Application class from the JavaFX framework, providing
 * the necessary lifecycle methods such as start.
 *
 * @author Nick Heggø
 * @version 2025.02.04
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("core/main-view.fxml"));
            String javaVersion = System.getProperty("java.version");
            String javafxVersion = System.getProperty("javafx.version");
            Label label = new Label("Hello world!");
            Group group = new Group();
            group.getChildren().add(label);
            var idk = fxmlLoader.load();
            stage.setScene(new Scene((Parent) idk));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
