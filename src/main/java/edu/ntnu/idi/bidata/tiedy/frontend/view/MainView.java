package edu.ntnu.idi.bidata.tiedy.frontend.view;

import java.io.IOException;
import java.nio.file.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainView {

  private Parent root;

  public MainView(Parent root) throws IOException {
    loadFXML();
  }

  private void loadFXML() throws IOException {
    Path path = Path.of("edu/ntnu/idi/bidata/tiedy/fxml/main-view.fxml");
    if (!path.toFile().exists()) {
      throw new IOException("main-view.fxml cannot be loaded!");
    }
    root = FXMLLoader.load(path.toUri().toURL());
  }

  public Scene getMainScene() {
    return new Scene(root);
  }
}
