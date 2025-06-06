package edu.ntnu.idi.bidata.tiedy.backend.io.json;

import edu.ntnu.idi.bidata.tiedy.backend.io.FileUtil;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Service class for reading from and writing to JSON files. This class utilizes {@link JsonReader}
 * for deserializing JSON content into objects and {@link JsonWriter} for serializing objects into
 * JSON files. It offers functionality to handle collections of specified types.
 *
 * <p>The behavior of the JSON operations is determined by the associated class type and an optional
 * flag for distinguishing between test and production environments.
 *
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonDAO<T> {

  private final JsonReader<T> jsonReader;
  private final JsonWriter<T> jsonWriter;

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This lightweight constructor only requires the target class type and defaults the environment
   * to production.
   *
   * @throws UnsupportedOperationException if the {@link JsonType} does not contain the necessary
   *     type for serialization
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonDAO(Class<T> targetClass) {
    this(targetClass, false);
  }

  /**
   * Constructs a new instance of the JsonService class for reading from and writing to JSON files.
   * This service is designed to handle collections of specified types. It utilizes {@link
   * JsonReader} to deserialize JSON content into objects and {@link JsonWriter} to serialize
   * objects into JSON files.
   *
   * @param isTest a boolean flag indicating whether the operations should be performed in the test
   *     environment (true) or the production environment (false)
   * @throws UnsupportedOperationException if the {@link JsonType} does not contain the necessary
   *     type for serialization
   * @throws IllegalArgumentException if the targetClass parameter is null
   */
  public JsonDAO(Class<T> targetClass, boolean isTest) {
    if (JsonType.getType(targetClass) == null) {
      throw new UnsupportedOperationException("Unsupported target class type: " + targetClass);
    }
    jsonReader = new JsonReader<>(targetClass, isTest);
    jsonWriter = new JsonWriter<>(targetClass, isTest);
  }

  /**
   * Loads a collection of objects from a JSON file. The JSON file is dynamically located based on
   * the target class type and whether the operation is performed in a test or production
   * environment. If the file does not exist, it is created and an empty list is returned. If the
   * file exists, the data in the file is deserialized into a list.
   *
   * @see FileUtil
   * @return a stream of objects deserialized from the JSON file, or an empty stream if the file is
   *     newly created
   */
  public Stream<T> loadJsonAsStream() {
    return jsonReader.parseJsonStream();
  }

  /**
   * Serializes and writes the provided set of objects into a JSON file. The JSON file is
   * dynamically located based on the class type and whether the operation is performed in a test or
   * production environment.
   *
   * @see FileUtil
   * @param set the set of objects to be serialized and written into the JSON file
   */
  public void writeCollection(Set<T> set) {
    jsonWriter.writeJsonFile(set);
  }
}
