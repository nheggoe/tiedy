package edu.ntnu.idi.bidata.tiedy.backend.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MapUtilTest {

  /**
   * Tests for the MapUtil.generateMapKey method. The generateMapKey method converts the input
   * string to lowercase and replaces all whitespace with underscores. It throws an
   * IllegalArgumentException if the input is null or empty.
   */
  @Test
  void testGenerateMapKeyWithValidString() {
    // Arrange
    String mapName = "Test Map";

    // Act
    String result = MapUtil.generateMapKey(mapName);

    // Assert
    assertEquals("test_map", result, "Expected valid key transformation for input with spaces");
  }

  @Test
  void testGenerateMapKeyWithSingleWordString() {
    // Arrange
    String mapName = "SingleWord";

    // Act
    String result = MapUtil.generateMapKey(mapName);

    // Assert
    assertEquals("singleword", result, "Expected valid key transformation for single word input");
  }

  @Test
  void testGenerateMapKeyWithAllUppercaseString() {
    // Arrange
    String mapName = "UPPER CASE";

    // Act
    String result = MapUtil.generateMapKey(mapName);

    // Assert
    assertEquals("upper_case", result, "Expected valid key transformation for uppercase input");
  }

  @Test
  void testGenerateMapKeyWithEmptyString() {
    // Arrange
    String mapName = "";

    // Act and Assert
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MapUtil.generateMapKey(mapName));
    assertEquals(
        "Key cannot be null or empty",
        exception.getMessage(),
        "Expected IllegalArgumentException for empty string");
  }

  @Test
  void testGenerateMapKeyWithNullString() {
    // Arrange
    String mapName = null;

    // Act and Assert
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MapUtil.generateMapKey(mapName));
    assertEquals(
        "Key cannot be null or empty",
        exception.getMessage(),
        "Expected IllegalArgumentException for null input");
  }

  @Test
  void testGenerateMapKeyWithLeadingAndTrailingSpaces() {
    // Arrange
    String mapName = "   Leading and Trailing Spaces   ";

    // Act
    String result = MapUtil.generateMapKey(mapName);

    // Assert
    assertEquals(
        "leading_and_trailing_spaces",
        result,
        "Expected valid key transformation for input with leading and trailing spaces");
  }

  @Test
  void testGenerateMapKeyWithMultipleConsecutiveSpaces() {
    // Arrange
    String mapName = "Multiple   Consecutive    Spaces";

    // Act
    String result = MapUtil.generateMapKey(mapName);

    // Assert
    assertEquals(
        "multiple_consecutive_spaces",
        result,
        "Expected valid key transformation for input with multiple consecutive spaces");
  }
}
