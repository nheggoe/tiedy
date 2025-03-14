package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.navigation.SceneName;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import java.util.List;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MainController {

  private static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

  @FXML private FlowPane flowPane;
  @FXML private Label info;
  @FXML private Button newTaskButton;

  @FXML
  public void initialize() {
    UserSession session = UserSession.getInstance();
    if (session == null) {
      newTaskButton.setDisable(true);
      info.setText("No user logged in");
    } else {
      User user = session.getCurrentUser();
      flowPane.getChildren().clear();
      List<Task> tasks = user.getTaskLists("reminders");
      LOGGER.info("Found " + tasks.size() + " tasks for user " + user.getUsername());
      tasks.stream().map(this::createTaskPane).forEach(flowPane.getChildren()::add);
    }
  }

  @FXML
  public void onLoginButtonPress() {
    TiedyApp.getSceneManager().switchScene(SceneName.LOGIN);
  }

  private Pane createTaskPane(Task task) {
    Pane cardPane = new Pane();
    cardPane.setPrefSize(120, 80);

    Rectangle taskBg = new Rectangle(0, 0, 120, 80);
    taskBg.setFill(Color.WHITE);
    taskBg.setStroke(Color.BLACK);
    taskBg.setArcWidth(10);
    taskBg.setArcHeight(10);

    Text rankText = new Text(10, 30, task.getTitle());
    rankText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

    cardPane.getChildren().addAll(taskBg, rankText);
    return cardPane;
  }

  @FXML
  public void addTask() {
    TiedyApp.getSceneManager().switchScene(SceneName.TASK);
  }
}
