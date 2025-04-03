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
   * Generates a file path for a JSON resource based on the simple name of the specified class. The
   * file path is determined by whether the operation targets test resources or production
   * resources.
   *
   * @param <T> the type of the class used for generating the file path
   * @param targetClass the class whose simple name is used to generate the file path; must not be
   *     null
   * @param isTest a boolean indicating whether the file path should target test resources (true) or
   *     production resources (false)
   * @return the file path to the JSON resource associated with the specified class name
   */
  public static <T> Path generateFilePath(
      Class<T> targetClass, String fileExtension, boolean isTest) {

    if (!FILE_EXTENSIONS.contains(fileExtension)) {
      throw new UnsupportedOperationException(
          ">" + fileExtension + "< is not a supported file extension");
    }

    return isTest
        ? Path.of(
            TEST_FILE_PATH_TEMPLATE.formatted(
                fileExtension, targetClass.getSimpleName(), fileExtension))
        : Path.of(
            FILE_PATH_TEMPLATE.formatted(
                fileExtension, targetClass.getSimpleName(), fileExtension));
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
