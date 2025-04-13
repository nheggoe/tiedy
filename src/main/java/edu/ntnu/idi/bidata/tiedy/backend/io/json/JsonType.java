package edu.ntnu.idi.bidata.tiedy.backend.io.json;

import com.google.gson.reflect.TypeToken;
import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for managing and retrieving type information used in JSON serialization and
 * deserialization.
 *
 * @see <a href="https://github.com/google/gson/blob/main/UserGuide.md#collections-examples">GSON
 *     collection examples</a>
 * @author Nick Heggø
 * @version 2025.03.28
 */
public class JsonType {
  private static final Map<Class<?>, Type> TYPE_MAP =
      Map.of(
          User.class, new TypeToken<Set<User>>() {}.getType(),
          Task.class, new TypeToken<Set<Task>>() {}.getType(),
          Group.class, new TypeToken<Set<Group>>() {}.getType());

  private JsonType() {}

  /**
   * Retrieves the {@link Type} associated with the specified class from the predefined type map.
   *
   * @param targetClass the class for which the associated {@link Type} is to be retrieved
   * @return the {@link Type} mapped to the specified class, or null if the class is not found in
   *     the map
   */
  public static Type getType(Class<?> targetClass) {
    return TYPE_MAP.get(targetClass);
  }
}
