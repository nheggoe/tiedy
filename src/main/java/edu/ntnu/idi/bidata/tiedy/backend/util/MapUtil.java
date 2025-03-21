package edu.ntnu.idi.bidata.tiedy.backend.util;

/**
 * Utility class for map-related operations. Provides helper methods for handling and transforming
 * map-related data.
 *
 * <p>This class is not intended to be instantiated.
 *
 * @author Nick Heggø
 * @version 2025.03.13
 */
public class MapUtil {

  private MapUtil() {}

  /**
   * Generates a standardized key for a map entry based on the provided name. Converts the input
   * string to lowercase and replaces all whitespace with underscores.
   *
   * @param mapName the name of the map to be transformed into a key; must not be null or empty
   * @return a formatted string to be used as the map key
   * @throws IllegalArgumentException if the provided mapName is null or empty
   */
  public static String generateMapKey(String mapName) {
    if (mapName == null || mapName.isEmpty()) {
      throw new IllegalArgumentException("Key cannot be null or empty");
    }
    return mapName.toLowerCase().replaceAll("\\s+", "_");
  }
}
