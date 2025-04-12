package edu.ntnu.idi.bidata.tiedy.frontend.session;

public class InvalidSessionException extends RuntimeException {
  public InvalidSessionException(String message) {
    super(message);
  }

  public InvalidSessionException() {
    super("No active session found. You need to log in first.");
  }
}
