package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

/**
 * This class serves solely as a demonstration of the FXML action listening feature.
 * MainController is responsible for managing the movement of a Circle object in a graphical user interface.
 * This class interacts with JavaFX application components using the @FXML annotation and processes
 * ActionEvent triggers to manipulate the Circle's position, as a reference example.
 */
public class MainController {

  @FXML
  private Circle myCircle;
  private double x;
  private double y;
  private final int pixelToMove = 4;

  @FXML
  public void up(ActionEvent e) {
    myCircle.setCenterY(y -= pixelToMove);
  }

  @FXML
  public void down(ActionEvent e) {
    myCircle.setCenterY(y += pixelToMove);
  }

  @FXML
  public void left(ActionEvent e) {
    myCircle.setCenterX(x -= pixelToMove);
  }

  @FXML
  public void right(ActionEvent e) {
    myCircle.setCenterX(x += pixelToMove);
  }

  @FXML
  public void upLeft(ActionEvent e) {
    up(e);
    left(e);
  }

  @FXML
  public void upRight(ActionEvent e) {
    up(e);
    right(e);
  }

  @FXML
  public void downLeft(ActionEvent e) {
    down(e);
    left(e);
  }

  @FXML
  public void downRight(ActionEvent e) {
    down(e);
    right(e);
  }

}
