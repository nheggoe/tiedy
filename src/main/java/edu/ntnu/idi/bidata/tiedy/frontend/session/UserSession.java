package edu.ntnu.idi.bidata.tiedy.frontend.session;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Optional;
import java.util.UUID;

/**
 * The UserSession class is responsible for managing a single user session in the application. This
 * class follows the singleton design pattern to ensure that only one instance of a user session
 * exists at any given time. It provides methods for creating, retrieving, and managing the current
 * session and the associated user.
 *
 * @author Nick Heggø
 * @version 2025.04.09
 */
public class UserSession {

  private static final UserSession instance = new UserSession();

  private User currentUser;

  private UserSession() {
    currentUser = null;
  }

  /**
   * Creates a new session for the specified user. This method initializes a singleton instance of
   * the UserSession class using the provided user. Only one session can exist at a time.
   *
   * @param user the User object for whom the session should be created
   */
  public static void createSession(User user) {
    instance.setCurrentUser(user);
  }

  /**
   * Terminates the current user session by clearing the information of the active user.
   *
   * <p>This method sets the active user associated with the session to {@code null}, effectively
   * destroying the session. If no session is currently active, this operation has no effect.
   */
  public static void destroySession() {
    instance.setCurrentUser(null);
  }

  /**
   * Retrieves the unique identifier (UUID) of the currently logged-in user.
   *
   * <p>This method checks if a user session is active. If there is no active user session or no
   * user is logged in, it throws an {@link InvalidSessionException}.
   *
   * @return the UUID of the currently logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static UUID getCurrentUserId() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).getId();
  }

  /**
   * Retrieves the username of the currently logged-in user.
   *
   * <p>This method fetches the username of the user associated with the active session. If no
   * session exists or no user is logged in, an {@link InvalidSessionException} will be thrown.
   *
   * @return the username of the currently logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static String getCurrentUsername() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).getUsername();
  }

  public static void completeTask() {
    instance.getCurrentUser().orElseThrow(InvalidSessionException::new).completeTask();
  }

  public static int getCurrentExperience() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).getExp();
  }

  public static int getCurrentLevel() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).getLevel();
  }

  public static int getCompletedTasks() {
    return instance
        .getCurrentUser()
        .orElseThrow(InvalidSessionException::new)
        .getCompletedTaskCount();
  }

  private Optional<User> getCurrentUser() {
    return Optional.ofNullable(currentUser);
  }

  private void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }
}
