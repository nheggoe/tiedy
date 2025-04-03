package edu.ntnu.idi.bidata.tiedy.backend.io.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.io.FileUtil;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.state.JsonService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  @BeforeEach
  void cleanUpTestFiles() {
    var userJsonFile = FileUtil.generateFilePath(User.class, "json", true).toFile();
    var taskJsonFile = FileUtil.generateFilePath(Task.class, "json", true).toFile();
    FileUtil.ensureFileAndDirectoryExists(userJsonFile);
    FileUtil.ensureFileAndDirectoryExists(taskJsonFile);
    new JsonWriter<>(User.class, true).writeJsonFile(new HashSet<>()); // Clear user data.
    new JsonWriter<>(Task.class, true).writeJsonFile(new HashSet<>()); // Clear task data.
  }

  @Test
  void testLoadJsonFromEmptySource() {
    var testUserService = new JsonService<>(User.class, true);
    assertTrue(
        testUserService.loadJsonAsStream().toList().isEmpty(),
        "Stream should be empty for no JSON data");
  }

  @Test
  void testLoadJsonWithSpecificData() {
    var testUserService = new JsonService<>(User.class, true);

    var user1 = new User("JohnDoe", "securePassword123");
    var user2 = new User("JaneDoe", "securePassword456");
    Set<User> users = Set.of(user1, user2);
    new JsonWriter<>(User.class, true).writeJsonFile(users);

    var loadedUsers = testUserService.loadJsonAsStream().toList();

    assertTrue(loadedUsers.contains(user1), "Loaded data should contain JohnDoe");
    assertTrue(loadedUsers.contains(user2), "Loaded data should contain JaneDoe");
  }
}
