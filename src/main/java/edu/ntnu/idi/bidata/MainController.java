package edu.ntnu.idi.bidata;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

/**
 * @author Nick Heggø
 * @version 2025.02.04
 */
public class MainController {


    @FXML
    private Circle myCircle;
    private double x;
    private double y;
    private int pixelToMove = 4;

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
