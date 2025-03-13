package edu.ntnu.idi.bidata.tiedy.frontend.navigation;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

  private final Stage primaryStage;

  public SceneManager(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void switchScene(SceneName sceneName) {
    try {
      Parent root = FXMLLoader.load(sceneName.getPath());
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load FXML file: " + sceneName.getPath(), e);
    }
  }
}
