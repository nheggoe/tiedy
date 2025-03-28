package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.task.TaskBuilder;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  @BeforeAll
  static void setUp() {
    User user1 = new User("User1", "strongPassword");
    var users =
        Stream.of(user1, new User("User2", "strongPassword"), new User("User3", "strongPassword"));
    new JsonWriter<>(User.class, true).writeJsonFile(users.collect(Collectors.toSet()));
  }

  @Test
  void testReadPlayers() {
    var testUserService = new JsonService<>(User.class, true);

    assertTrue(testUserService.loadJsonAsStream().findAny().isPresent());
    assertEquals(3, testUserService.loadJsonAsStream().count());
  }

  @Test
  @Disabled("Work in progress") // TODO
  void testWriteTaskToJson() {
    var taskService = new JsonService<>(Task.class, true);
    var taskBuilder = new TaskBuilder();

    var task1 = taskBuilder.title("test task 1").description("task1 description").build();
    var task2 = taskBuilder.title("test task 2").description("task2 description").build();

    User user = new User("Test", "123123123u23");
    task1.addAssignedUser(user);
    task2.addAssignedUser(user);

    var tasks = Stream.of(task1, task2);
    taskService.writeCollection(tasks.collect(Collectors.toSet()));
    System.out.println(taskService.loadJsonAsStream().toList());
    assertFalse(
        taskService
            .loadJsonAsStream()
            .filter(task -> task.getId().equals(task1.getId()))
            .findFirst()
            .isEmpty());
    assertTrue(
        taskService
            .loadJsonAsStream()
            .anyMatch(task -> task.getAssignedUsers().contains(user.getId())));
  }
}
