package edu.ntnu.idi.bidata.tiedy.frontend.view;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginView {

  private final URL fxmlURL =
      getClass().getResource("/edu/ntnu/idi/bidata/tiedy/fxml/login-view.fxml");
  private Parent root;

  public LoginView() {
    loadFXML();
  }

  private void loadFXML() {
    try {
      root = FXMLLoader.load(fxmlURL);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot load login-view.fxml", e);
    }
  }

  /**
   * Provides access to the root node of the scene graph loaded from the FXML file.
   *
   * @return the root node of the scene graph
   */
  public Parent getRoot() {
    return root;
  }
}
