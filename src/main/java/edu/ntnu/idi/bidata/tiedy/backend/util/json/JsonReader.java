package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Utility class for reading JSON files and deserializing their content into objects of a specified
 * target type. The JSON file is located dynamically based on the provided class type, with separate
 * support for test and production environments.
 *
 * <p>The class ensures that the directory structure for the JSON file is created if it does not
 * already exist. If the specified JSON file does not exist, an empty list is returned.
 *
 * <p>This class leverages a shared instance of {@link ObjectMapper} provided by the {@link
 * JsonMapper} class for efficient JSON operations.
 *
 * @author Nick Heggø
 * @version 2025.03.12
 */
public class JsonReader {

  private final ObjectMapper objectMapper = JsonMapper.getInstance();
  private Class<?> targetClass;
  private final boolean isTest;

  /**
   * Constructs a new JsonReader instance for reading JSON files and deserializing their content
   * into objects of the specified target class type. The JSON file location is determined
   * dynamically based on the provided target class and is designed for use in a production
   * environment by default.
   *
   * @param <T> the type of the objects that will be deserialized
   * @param targetClass the class type of the objects to be deserialized; must not be null
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonReader(Class<T> targetClass) {
    setTargetClass(targetClass);
    this.isTest = false;
  }

  /**
   * Constructs a new instance of the JsonReader class for reading JSON files and deserializing
   * their content into objects of the specified target class type. The JSON file location is
   * determined dynamically based on the provided target class and whether the operation is
   * performed in a test or production environment.
   *
   * @param <T> the type of the objects that will be deserialized
   * @param targetClass the class type of the objects to be deserialized; must not be null
   * @param isTest a boolean flag indicating whether the JSON file should be located in the test
   *     environment (true) or the production environment (false)
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public <T> JsonReader(Class<T> targetClass, boolean isTest) {
    setTargetClass(targetClass);
    this.isTest = isTest;
  }

  /**
   * Parses a JSON file located at a path determined dynamically based on the target class type and
   * whether the operation is performed in a test or production environment. The method ensures that
   * the directory structure exists before attempting to read the file. If the file does not already
   * exist, it is created, and an empty list is returned. Otherwise, the data in the file is
   * deserialized into a list of objects of the specified target class type.
   *
   * @param <T> the type of the objects to be deserialized
   * @return a list of objects deserialized from the JSON file; an empty list if the file is newly
   *     created
   * @throws IOException if an I/O error occurs during file creation or reading
   */
  public <T> List<T> parseJsonFile() throws IOException {
    Path jsonFilePath = JsonPathUtil.getJsonFilePath(targetClass, isTest);
    File file = jsonFilePath.toFile();
    File parentDir = file.getParentFile();
    if (!parentDir.exists() && !parentDir.mkdirs()) {
      throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
    }

    boolean isNewFileCreated = file.createNewFile();
    return isNewFileCreated
        ? List.of()
        : objectMapper.readValue(
            file, objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
  }

  private void setTargetClass(Class<?> targetClass) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
  }
}
