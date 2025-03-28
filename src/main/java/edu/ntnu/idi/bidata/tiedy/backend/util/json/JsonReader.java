package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import com.google.gson.Gson;
import edu.ntnu.idi.bidata.tiedy.backend.util.FileUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class for reading JSON files and deserializing their content into objects of a specified
 * target type. The JSON file is located dynamically based on the provided class type, with separate
 * support for test and production environments.
 *
 * <p>The class ensures that the directory structure for the JSON file is created if it does not
 * already exist. If the specified JSON file does not exist, an empty list is returned.
 *
 * <p>This class leverages a shared instance of {@link Gson} provided by the {@link CustomGson}
 * class for efficient JSON operations.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonReader<T> {

  private final Gson gson = CustomGson.getInstance();
  private final Class<T> targetClass;
  private final boolean isTest;

  /**
   * Constructs a new instance of the JsonReader class for reading JSON files and deserializing
   * their content into objects of the specified target class type. The JSON file location is
   * determined dynamically based on the provided target class and whether the operation is
   * performed in a test or production environment.
   *
   * @param targetClass the class of object that will be used to serialize
   * @param isTest a boolean flag indicating whether the JSON file should be located in the test
   *     environment (true) or the production environment (false)
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonReader(Class<T> targetClass, boolean isTest) {
    if (targetClass == null) {
      throw new IllegalArgumentException("Target class must not be null");
    }
    this.targetClass = targetClass;
    this.isTest = isTest;
  }

  /**
   * Parses a JSON file into a stream of the specified target class type. The JSON file is located
   * at a dynamically determined path based on the class name and an environment flag (test or
   * production). If the directory structure for the file does not exist, it will be created. If the
   * file does not exist, it will be created as an empty file and an empty stream is returned.
   * Otherwise, the JSON content will be deserialized into objects of the target class.
   *
   * @return a stream of objects deserialized from the JSON file; an empty stream if the file is
   *     newly created
   */
  public Stream<T> parseJsonStream() {
    File file = JsonPathUtil.generateJsonPath(targetClass, isTest).toFile();
    FileUtil.ensureFileAndDirectoryExists(file);

    if (file.length() == 0) {
      return Stream.empty();
    }

    try (FileReader reader = new FileReader(file)) {
      Set<T> set = gson.fromJson(reader, JsonType.getType(targetClass));
      return set.stream();
    } catch (IOException e) {
      throw new JsonException("Could not parse JSON file: " + file + "\n" + e.getMessage());
    }
  }
}
