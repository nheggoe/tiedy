package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.frontend.TiedyApp;
import edu.ntnu.idi.bidata.tiedy.frontend.session.UserSession;
import edu.ntnu.idi.bidata.tiedy.frontend.util.DataChangeNotifier;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

/**
 * The StatisticController class is responsible for updating the value of StatisticView.fxml. It
 * fetches the data and updates the graph to make it dynamic.
 *
 * @author Nick Heggø
 * @version 2025.04.12
 */
public class StatisticController implements DataController {

  @FXML private PieChart pieChartLeft;
  @FXML private PieChart pieChartRight;

  /**
   * Initializes the {@link StatisticController} by registering it as an observer for data updates
   * and refreshing the UI components with the latest data.
   *
   * <p>This method is invoked automatically by the JavaFX framework after the controller is
   * instantiated and its annotated fields, such as FXML elements, are loaded.
   *
   * <p>Responsibilities of this method include: - Registering the controller with the {@link
   * DataChangeNotifier} singleton to ensure it receives notifications of data changes. - Triggering
   * an initial data update to populate the {@link PieChart} components with the current state of
   * the underlying data source.
   *
   * <p>By performing these actions during initialization, the controller ensures that the user
   * interface is synchronized with the latest available data and is ready for interaction once it
   * is displayed.
   */
  @FXML
  public void initialize() {
    register();
    updateData();
  }

  @Override
  public void updateData() {
    updatePieChartLeft();
    updatePieChartRight();
  }

  private void updatePieChartRight() {
    pieChartRight.getData().clear();

    Arrays.stream(Status.values())
        .forEach(
            status -> {
              int count =
                  TiedyApp.getDataAccessFacade()
                      .getActiveTasksByUserIdAndStatus(UserSession.getCurrentUserId(), status)
                      .size();
              if (count > 0) {
                pieChartRight.getData().add(new PieChart.Data(status.toString(), count));
              }
            });

    String[] statusColors = {
      "#4682B4", "#FF6347", "#32CD32", "#FFD700"
    }; // Steel Blue, Tomato, Lime Green, Gold
    applyColorsToChart(pieChartRight, statusColors);
  }

  private void updatePieChartLeft() {
    pieChartLeft.getData().clear();

    Arrays.stream(Priority.values())
        .forEach(
            priority -> {
              int count =
                  TiedyApp.getDataAccessFacade()
                      .getActiveTasksByUserIdAndPriority(UserSession.getCurrentUserId(), priority)
                      .size();
              if (count > 0) {
                pieChartLeft.getData().add(new PieChart.Data(priority.toString(), count));
              }
            });

    // Colors for priority pie chart
    String[] priorityColors = {"#FF0000", "#FFA500", "#32CD32"}; // Red, Orange, Green
    applyColorsToChart(pieChartLeft, priorityColors);
  }

  private void applyColorsToChart(PieChart chart, String[] colors) {
    chart.setStyle("-fx-background-color: transparent;");
    chart.setLabelsVisible(true);
    chart.setLegendVisible(true);
    chart.setAnimated(false); // Disable animation to ensure consistent size

    chart.setMinSize(300, 300);
    chart.setPrefSize(300, 300);
    chart.setMaxSize(300, 300);

    // Apply colors to each slice
    int i = 0;
    for (PieChart.Data data : chart.getData()) {
      if (data.getNode() != null) {
        String color = colors[i % colors.length];
        // Use a more specific selector with !important to override any conflicting styles
        String style =
            String.format(
                "-fx-pie-color: %s !important; -fx-background-color: %s !important;", color, color);
        data.getNode().setStyle(style);
        i++;
      }
    }

    // Make sure slices are visible
    chart.getStyleClass().add("colored-pie-chart");
  }
}
