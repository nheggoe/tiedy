package edu.ntnu.idi.bidata.tiedy.backend.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling file and directory operations. Provides methods to ensure that a
 * specified file and its parent directories exist, creating them if necessary.
 *
 * @author Nick Heggø
 * @version 2025.03.13
 */
public class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

  private FileUtil() {}

  /**
   * Ensures that the specified file and its parent directories exist. If the directories do not
   * exist, they will be created. If the file does not exist, it will be created.
   *
   * @param file the file whose existence (and its parent directories) should be ensured; must not
   *     be null
   */
  public static void ensureFileAndDirectoryExists(File file) {
    try {
      if (file == null) {
        throw new IllegalStateException("The file cannot be null");
      }
      createDirectory(file);
      createFile(file);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, () -> "Cannot create file: " + file);
    }
  }

  private static void createDirectory(File file) {
    File parentDir = file.getParentFile();
    if (parentDir.mkdirs()) {
      LOGGER.info("Created directory: " + parentDir.getAbsolutePath());
    }
  }

  private static void createFile(File file) throws IOException {
    if (file.createNewFile()) {
      LOGGER.info("Created file: " + file.getAbsolutePath());
    }
  }
}
