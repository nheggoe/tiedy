package edu.ntnu.idi.bidata.tiedy.frontend.session;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.util.Optional;

/**
 * The UserSession class is responsible for managing a single user session in the application. This
 * class follows the singleton design pattern to ensure that only one instance of a user session
 * exists at any given time. It provides methods for creating, retrieving, and managing the current
 * session and the associated user.
 *
 * @author Nick Heggø
 * @version 2025.03.13
 */
public class UserSession {
  private static UserSession instance;
  private User currentUser;

  private UserSession(User user) {
    this.currentUser = user;
  }

  /**
   * Creates a new session for the specified user. This method initializes a singleton instance of
   * the UserSession class using the provided user. Only one session can exist at a time.
   *
   * @param user the User object for whom the session should be created
   */
  public static void createSession(User user) {
    instance = new UserSession(user);
  }

  /**
   * Retrieves the singleton instance of the UserSession class. If no session has been created, this
   * method will return null. To initialize a session, use the createSession method.
   *
   * @return the singleton instance of UserSession if a session exists; otherwise, returns null.
   */
  public static UserSession getInstance() {
    return instance;
  }

  /**
   * Retrieves the currently active user associated with the UserSession.
   *
   * @return the User object representing the current user, or null if no user is set.
   */
  public Optional<User> getCurrentUser() {
    return Optional.of(currentUser);
  }

  /**
   * Sets the currently active user for the session. This method updates the `currentUser` field in
   * the `UserSession` class with the provided `User` object. The active user represents the user
   * currently associated with the session.
   *
   * @param currentUser the User object to set as the currently active user in the session
   */
  public void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }
}
