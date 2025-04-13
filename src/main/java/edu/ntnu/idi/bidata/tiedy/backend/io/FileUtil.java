package edu.ntnu.idi.bidata.tiedy.backend.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Utility class for handling file and directory operations. Provides methods to ensure that a
 * specified file and its parent directories exist, creating them if necessary.
 *
 * @author Nick Heggø
 * @version 2025.04.03
 */
public class FileUtil {

  private static final String FILE_PATH_TEMPLATE = "data/%s/%s.%s";
  private static final String TEST_FILE_PATH_TEMPLATE = "src/test/resources/%s/%s.%s";
  private static final Set<String> FILE_EXTENSIONS = Set.of("json", "csv");

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

  private FileUtil() {}

  /**
   * Generates a file path for a specified class and file extension, with the option to specify a
   * test or production environment.
   *
   * @param targetClass the class for which the file path is being generated; must not be null
   * @param fileExtension the file extension to use (e.g., "json", "csv"); must be a supported
   *     extension
   * @param isTest a boolean flag indicating whether the file path should correspond to a test
   *     environment (true) or a production environment (false)
   * @return the generated file path as a {@code Path} instance
   * @throws UnsupportedOperationException if the provided {@code fileExtension} is not supported
   */
  public static Path generateFilePath(Class<?> targetClass, String fileExtension, boolean isTest) {

    if (!FILE_EXTENSIONS.contains(fileExtension)) {
      throw new UnsupportedOperationException(
          ">" + fileExtension + "< is not a supported file extension");
    }

    return Path.of(
        (isTest ? TEST_FILE_PATH_TEMPLATE : FILE_PATH_TEMPLATE)
            .formatted(fileExtension, targetClass.getSimpleName(), fileExtension));
  }

  /**
   * Ensures that the specified file and its parent directories exist. If the directories do not
   * exist, they will be created. If the file does not exist, it will be created.
   *
   * @param file the file whose existence (and its parent directories) should be ensured; must not
   *     be null
   */
  public static void ensureFileAndDirectoryExists(File file) {
    if (file == null) {
      throw new IllegalStateException("The file cannot be null");
    }
    createDirectory(file);
    createFile(file);
  }

  private static void createDirectory(File file) {
    File parentDir = file.getParentFile();
    if (parentDir != null && parentDir.mkdirs()) {
      LOGGER.info(() -> "Created directory: " + parentDir.getAbsolutePath());
    }
  }

  private static void createFile(File file) {
    try {
      if (file.createNewFile()) {
        LOGGER.info(() -> "Created file: " + file.getAbsolutePath());
      }
    } catch (IOException e) {
      LOGGER.severe(() -> "Cannot create file: " + file);
    }
  }
}
