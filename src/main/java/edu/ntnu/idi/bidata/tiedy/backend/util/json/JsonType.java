package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import com.google.gson.reflect.TypeToken;
import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.Group;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
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

  public static Type getType(Class<?> targetClass) {
    return TYPE_MAP.get(targetClass);
  }
}
