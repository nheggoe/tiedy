package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  @BeforeAll
  static void setUp() throws IOException {
    User user1 = new User("User1", "strongPassword", "email@example.com");
    var users =
        Stream.of(
            user1,
            new User("User2", "strongPassword", "email@example.com"),
            new User("User3", "strongPassword", "email@example.com"));
    new JsonWriter<>(User.class, true).writeJsonFile(users);
  }

  @Test
  void testReadPlayers() {
    var testUserService = new JsonService<>(User.class, true);

    assertTrue(testUserService.loadJsonAsStream().findAny().isPresent());
    assertEquals(3, testUserService.loadJsonAsStream().count());
  }

  @Test
  void testWriteTaskToJson() throws IOException {
    var taskService = new JsonService<>(Task.class, true);

    User user = new User("Test", "123123123u23");
    var task1 = new Task(user, "Test task1", "Task 1");
    task1.addAssignedUser(user);
    var task2 = new Task(user, "Test task 2", "The description of test task 2");
    task2.addAssignedUser(user);
    var tasks = Stream.of(task1, task2);
    taskService.writeCollection(tasks);
    assertTrue(taskService.loadJsonAsStream().anyMatch(task -> task.equals(task1)));
    assertTrue(
        taskService
            .loadJsonAsStream()
            .anyMatch(task -> task.getAssignedUsers().contains(user.getId())));
  }
}
