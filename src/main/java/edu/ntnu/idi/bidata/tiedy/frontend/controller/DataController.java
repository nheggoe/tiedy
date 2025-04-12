package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.frontend.util.DataChangeNotifier;

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

  /**
   * Registers the current instance of {@link DataController} as an observer to receive data update
   * notifications from the {@link DataChangeNotifier} singleton.
   *
   * <p>This method allows the implementing class to subscribe to events triggered by {@link
   * DataChangeNotifier}, enabling it to be notified whenever changes to the underlying data occur.
   * Once registered, the controller's {@code updateData} method will be invoked when a data update
   * event is emitted by the notifier.
   *
   * <p>The registration ensures that the controller can dynamically respond to data state changes,
   * which could include refreshing views, updating models, or any other operation dependent on data
   * updates.
   *
   * <p>By default, the controller is added as an observer only if it is not already present in the
   * notifier's observer list, ensuring no duplicate registrations.
   *
   * <p>Implementing controllers can call this method as part of their initialization process to
   * ensure proper synchronization with the application's data management infrastructure.
   */
  default void register() {
    DataChangeNotifier.getInstance().addObserver(this);
  }

  /**
   * Unregisters the current instance of {@link DataController} from the {@link
   * DataChangeNotifier}'s list of observers.
   *
   * <p>This method ensures that the implementing class is no longer notified of data updates by the
   * {@link DataChangeNotifier}. It can be used in scenarios where the controller is no longer
   * required to respond to data changes, such as during cleanup, shutdown, or when the controller
   * is no longer active or relevant.
   *
   * <p>Once unregistered, the {@link DataController} will not receive updates, and its {@code
   * updateData} method will not be invoked in response to data change notifications from the
   * notifier.
   *
   * <p>The method internally calls {@code DataChangeNotifier.getInstance().removeObserver(this)} to
   * remove the controller from the list of currently registered observers.
   */
  default void unregister() {
    DataChangeNotifier.getInstance().removeObserver(this);
  }
}
