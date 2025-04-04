package edu.ntnu.idi.bidata.tiedy.backend.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating strings. This class is designed to ensure strings are neither null
 * nor empty. It is typically used to validate method parameters or other input values.
 *
 * <p>This class is not meant to be instantiated and contains only static utility methods.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class StringChecker {

  private StringChecker() {}

  /**
   * Validates a string by ensuring it is neither null nor empty. If the validation fails, an {@code
   * IllegalArgumentException} is thrown with an appropriate message.
   *
   * @param input the string to validate
   * @param fieldName the name of the string being validated, used in the error message
   * @throws IllegalArgumentException if the string is null or empty
   */
  public static void assertStringNotNullOrEmpty(String input, String fieldName) {
    if (input == null) {
      throw new IllegalArgumentException(fieldName + " cannot be null!");
    }
    if (input.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be empty!");
    }
  }

  /**
   * Validates if the provided email address adheres to a standard email format.
   *
   * @param emailAddress the email address to be validated
   */
  private void validateEmailFormat(String emailAddress) {
    String emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (emailAddress != null && !pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }
}
