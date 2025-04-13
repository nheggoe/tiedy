package edu.ntnu.idi.bidata.tiedy.backend.io.json;

/**
 * Custom exception used to indicate errors related to JSON handling operations. This exception is
 * typically thrown to encapsulate issues encountered during JSON parsing, serialization, or
 * deserialization processes.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class JsonException extends RuntimeException {
  /**
   * Constructs a new JsonException instance with the specified detail message.
   *
   * @param message the detail message providing further information about the exception
   */
  public JsonException(String message) {
    super(message);
  }
}
