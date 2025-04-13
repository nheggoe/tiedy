package edu.ntnu.idi.bidata.tiedy.frontend.session;

/**
 * The InvalidSessionException is a custom runtime exception that indicates the absence of an active
 * user session. It is typically thrown in scenarios where session-dependent operations are
 * attempted, but no valid session is found.
 *
 * <p>This exception is used in the context of the {@link UserSession} class and its methods, which
 * manage user session data such as user IDs and usernames. Methods in the UserSession class that
 * require an active user session can throw this exception to signal that a login or session
 * creation is required before performing the operation.
 *
 * @author Nick Heggø
 * @version 2025.04.13
 */
public class InvalidSessionException extends RuntimeException {
  public InvalidSessionException() {
    super("No active session found. You need to log in first.");
  }
}
