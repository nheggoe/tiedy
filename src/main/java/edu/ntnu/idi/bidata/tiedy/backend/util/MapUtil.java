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

  public static String generateMapKey(String mapName) {
    if (mapName == null || mapName.isEmpty()) {
      throw new IllegalArgumentException("Key cannot be null or empty");
    }
    return mapName.toLowerCase().replaceAll("\\s+", "_");
  }
}
