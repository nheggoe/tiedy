package edu.ntnu.idi.bidata.tiedy.backend.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringCheckerTest {

  @Test
  void checkString_NotNullOrEmpty_shouldThrowExceptionWhenInputIsNull() {
    String input = null;
    String fieldName = "TestField";

    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> StringChecker.assertStringNotNullOrEmpty(input, fieldName));

    Assertions.assertEquals("TestField cannot be null!", exception.getMessage());
  }

  @Test
  void checkString_NotNullOrEmpty_shouldThrowExceptionWhenInputIsBlank() {
    String input = "   ";
    String fieldName = "TestField";

    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> StringChecker.assertStringNotNullOrEmpty(input, fieldName));

    Assertions.assertEquals("TestField cannot be empty!", exception.getMessage());
  }

  @Test
  void checkNotNullOrEmpty() {
    String input = "ValidString";
    String fieldName = "TestField";

    Assertions.assertDoesNotThrow(() -> StringChecker.assertStringNotNullOrEmpty(input, fieldName));
  }
}
