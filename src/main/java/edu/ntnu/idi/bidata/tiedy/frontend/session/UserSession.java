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

  /**
   * This method retrieves the current user from the active session and attempts to mark a task as
   * completed. If no active session exists, an {@link InvalidSessionException} is thrown.
   * Completion of the task may also trigger level progression for the user, depending on their
   * current experience and level system settings.
   *
   * @return {@code true} if the task completion resulted in leveling up, {@code false} otherwise
   * @throws InvalidSessionException if no active session exists or no user is currently logged in
   */
  public static boolean completeTask() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).completeTask();
  }

  /**
   * Retrieves the current experience points of the user associated with the active session.
   *
   * @return the current experience points of the logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static int getCurrentExperience() {
    return instance
        .getCurrentUser()
        .orElseThrow(InvalidSessionException::new)
        .getCurrentExperience();
  }

  /**
   * Retrieves the current level of the user associated with the active session.
   *
   * <p>This method fetches the level of the logged-in user from the session. If no session is
   * active, it throws an {@link InvalidSessionException}.
   *
   * @return the current level of the logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static int getCurrentLevel() {
    return instance.getCurrentUser().orElseThrow(InvalidSessionException::new).getCurrentLevel();
  }

  /**
   * Retrieves the number of tasks completed by the currently logged-in user.
   *
   * <p>This method fetches the completed task count by accessing the active user session. If no
   * active session exists or no user is logged in, an {@link InvalidSessionException} will be
   * thrown.
   *
   * @return the count of tasks completed by the currently logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static int getCompletedTaskCount() {
    return instance
        .getCurrentUser()
        .orElseThrow(InvalidSessionException::new)
        .getCompletedTaskCount();
  }

  /**
   * Retrieves the experience threshold required for the currently logged-in user to reach the next
   * level. This method fetches the threshold from the user's level system. If no active session
   * exists or no user is logged in, an {@link InvalidSessionException} will be thrown.
   *
   * @return the experience threshold for the logged-in user
   * @throws InvalidSessionException if no active session exists or no user is logged in
   */
  public static int getExperienceThreshold() {
    return instance
        .getCurrentUser()
        .orElseThrow(InvalidSessionException::new)
        .getExperienceThreshold();
  }

  private Optional<User> getCurrentUser() {
    return Optional.ofNullable(currentUser);
  }

  private void setCurrentUser(User currentUser) {
    this.currentUser = currentUser;
  }
}
