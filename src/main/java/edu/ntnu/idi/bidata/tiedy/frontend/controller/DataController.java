package edu.ntnu.idi.bidata.tiedy.frontend.controller;

/**
 * Defines a specialized controller that manages data-related operations in the application.
 * Implementing classes are expected to provide specific implementations for handling and updating
 * data within their respective contexts.
 *
 * <p>This interface extends the base {@link Controller} interface, inheriting the responsibility of
 * managing user interactions and initializing components. It adds the ability to update data
 * dynamically through the {@code updateData} method.
 *
 * @author Nick Heggø
 * @version 2025.04.11
 */
public interface DataController extends Controller {
  /**
   * Updates the data managed by the controller. This method is expected to trigger a refresh of the
   * current user interface or state with the latest data available in the backend or the data
   * source.
   *
   * <p>Implementing classes should define specific behavior for how and what data is updated, such
   * as reloading tasks, updating filters, or reflecting changes in the user interface.
   */
  void updateData();
}
