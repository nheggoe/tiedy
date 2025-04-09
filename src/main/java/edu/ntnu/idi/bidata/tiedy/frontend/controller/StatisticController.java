package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class StatisticController {

  @FXML private PieChart pieChartLeft;
  @FXML private PieChart pieChartRight;

  @FXML
  public void initialize() {
    updatePieChartLeft();
    updatePieChartRight();
  }

  @FXML
  public void updatePieChartRight() {
    Status[] statuses = Status.values();
    Arrays.stream(statuses)
        .forEach(
            status ->
                pieChartRight
                    .getData()
                    .add(
                        new PieChart.Data(
                            status.toString(),
                            TiedyApp.getDataAccessFacade().findByStatus(status).size())));
  }

  public void updatePieChartLeft() {
    Priority[] priorities = Priority.values();
    Arrays.stream(priorities)
        .forEach(
            priority ->
                pieChartLeft
                    .getData()
                    .add(
                        new PieChart.Data(
                            priority.toString(),
                            TiedyApp.getDataAccessFacade().findByPriority(priority).size())));

    String[] sliceColors = {"#FF0000", "#00FF00", "#0000FF"}; // Red, Green, Blue
    int i = 0;
    for (PieChart.Data data : pieChartLeft.getData()) {
      // For each data slice, set a custom pie color
      data.getNode().setStyle("-fx-pie-color: " + sliceColors[i % sliceColors.length] + ";");
      i++;
    }
  }
}
