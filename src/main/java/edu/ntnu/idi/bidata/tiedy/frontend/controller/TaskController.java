package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TaskController {

  private JsonService userService = new JsonService(User.class);

  @FXML private TextField taskName;
  @FXML private TextField taskDescription;

  @FXML
  public void submitTask() {
    try {
      String name = taskName.getText();
      String description = taskDescription.getText();
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Task name cannot be empty");
      }
      if (description == null || description.isBlank()) {
        throw new IllegalArgumentException("Task description cannot be empty");
      }
      User user = UserSession.getInstance().getCurrentUser();
      boolean success = user.addTask(new Task(name, description));
      /*
      FIXME need to update JSON file with the newly added task
      FIXME Currently there is no data persistence.
      */
      Stream<User> userStream = userService.loadJsonAsStream();
      var users = new ArrayList<>(userStream.toList());
      users.removeIf(u -> u.getId().equals(user.getId()));
      users.add(user);
      userService.writeCollection(users.stream());

      if (success) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task added");
        alert.setContentText("Task added successfully");
        alert.showAndWait();
        TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
      } else {
        throw new IllegalArgumentException("Task could not be added");
      }
    } catch (IllegalArgumentException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setContentText(e.getMessage());
      alert.show();
    } catch (IOException e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("Error while saving task");
    }
  }

  @FXML
  public void backToMain() {
    TiedyApp.getSceneManager().switchScene(SceneName.MAIN);
  }
}
