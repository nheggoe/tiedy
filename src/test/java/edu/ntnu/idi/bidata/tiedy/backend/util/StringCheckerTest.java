package edu.ntnu.idi.bidata.tiedy.backend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringCheckerTest {

  @Test
  void assertValidString_shouldThrowExceptionWhenInputIsNull() {
    String input = null;
    String fieldName = "TestField";

    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> StringChecker.assertValidString(input, fieldName));

    Assertions.assertEquals("TestField cannot be null!", exception.getMessage());
  }

  @Test
  void assertValidString_shouldThrowExceptionWhenInputIsBlank() {
    String input = "   ";
    String fieldName = "TestField";

    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> StringChecker.assertValidString(input, fieldName));

    Assertions.assertEquals("TestField cannot be empty!", exception.getMessage());
  }

  @Test
  void assertValidString_shouldNotThrowExceptionWhenInputIsValid() {
    String input = "ValidString";
    String fieldName = "TestField";

    Assertions.assertDoesNotThrow(() -> StringChecker.assertValidString(input, fieldName));
  }
}
