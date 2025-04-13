package edu.ntnu.idi.bidata.tiedy.frontend.util;

import edu.ntnu.idi.bidata.tiedy.frontend.controller.DataController;
import java.util.ArrayList;
import java.util.List;

/**
 * A singleton class responsible for managing and notifying registered observers about data updates.
 *
 * <p>This class implements the Observer design pattern, allowing registered observers (instances of
 * {@link DataController}) to be notified when a data update event occurs.
 *
 * @author Nick Heggø
 * @version 2025.04.12
 */
public class DataChangeNotifier {
  private static DataChangeNotifier instance;
  private final List<DataController> observers = new ArrayList<>();

  private DataChangeNotifier() {}

  /**
   * Provides a singleton instance of the DataChangeNotifier class.
   *
   * <p>This method ensures that only one instance of DataChangeNotifier is created and shared
   * across the application. The singleton pattern is implemented using synchronized access to
   * ensure thread safety during instance initialization.
   *
   * @return the singleton instance of DataChangeNotifier
   */
  public static synchronized DataChangeNotifier getInstance() {
    if (instance == null) {
      instance = new DataChangeNotifier();
    }
    return instance;
  }

  /**
   * Adds a {@link DataController} observer to the list of observers if it is not already present.
   * The system will notify observers registered with this method of data updates.
   *
   * @param observer the {@link DataController} instance to be added as an observer
   */
  public void addObserver(DataController observer) {
    if (!observers.contains(observer)) {
      observers.add(observer);
    }
  }

  /**
   * Removes a {@link DataController} observer from the list of registered observers, if it is
   * currently present in the list. Observers removed using this method will no longer receive
   * updates about data changes.
   *
   * @param observer the {@link DataController} instance to be removed as an observer
   */
  public void removeObserver(DataController observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all registered observers about a data update event.
   *
   * <p>This method iterates over the list of registered {@link DataController} observers and
   * invokes their {@code updateData} method. It ensures that each observer is informed of changes
   * to the underlying data and can handle the update accordingly.
   *
   * <p>Observers must be instances of {@link DataController}, and they are expected to implement
   * the {@code updateData} method to define specific behavior upon receiving the notification.
   *
   * <p>This functionality supports the Observer design pattern by decoupling the notification of
   * events (in this case, data updates) from the specific actions that observers take in response.
   */
  public void notifyObservers() {
    observers.forEach(DataController::updateData);
  }
}
