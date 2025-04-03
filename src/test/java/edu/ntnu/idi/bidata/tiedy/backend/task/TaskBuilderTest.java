package edu.ntnu.idi.bidata.tiedy.backend.task;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.model.task.TaskBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TaskBuilderTest {

  /** Test to verify that a Task object is correctly built with all required attributes. */
  @Test
  void buildShouldCreateTaskWithAllAttributes() {
    TaskBuilder taskBuilder = new TaskBuilder();

    Task task =
        taskBuilder
            .title("Test Task")
            .description("This is a test task")
            .priority(Priority.HIGH)
            .status(Status.IN_PROGRESS)
            .deadline(LocalDate.of(2023, 12, 31))
            .build();

    assertNotNull(task);
    assertEquals("Test Task", task.getTitle());
    assertEquals("This is a test task", task.getDescription());
    assertEquals(Priority.HIGH, task.getPriority());
    assertEquals(Status.IN_PROGRESS, task.getStatus());
    assertEquals(LocalDate.of(2023, 12, 31), task.getDeadline());
  }

  /**
   * Test to verify that an IllegalStateException is thrown when attempting to build a Task with a
   * null title.
   */
  @Test
  void buildShouldThrowExceptionWhenTitleIsNull() {
    TaskBuilder taskBuilder = new TaskBuilder().priority(Priority.HIGH).status(Status.IN_PROGRESS);

    IllegalStateException exception = assertThrows(IllegalStateException.class, taskBuilder::build);

    assertEquals("Task title cannot be null or blank", exception.getMessage());
  }

  /**
   * Test to verify that an IllegalStateException is thrown when attempting to build a Task with a
   * blank title.
   */
  @Test
  void buildShouldThrowExceptionWhenTitleIsBlank() {
    TaskBuilder taskBuilder =
        new TaskBuilder().title(" ").priority(Priority.HIGH).status(Status.OPEN);

    assertThrows(IllegalArgumentException.class, taskBuilder::build);
  }

  /**
   * Test to verify that multiple build calls reset the builder correctly and do not affect
   * subsequent Task objects.
   */
  @Test
  void buildShouldResetBuilderAfterEachTaskCreation() {
    TaskBuilder taskBuilder = new TaskBuilder();

    Task firstTask =
        taskBuilder.title("First Task").priority(Priority.HIGH).status(Status.OPEN).build();

    Task secondTask =
        taskBuilder.title("Second Task").priority(Priority.LOW).status(Status.CLOSED).build();

    assertNotNull(firstTask);
    assertEquals("First Task", firstTask.getTitle());
    assertEquals(Priority.HIGH, firstTask.getPriority());
    assertEquals(Status.OPEN, firstTask.getStatus());

    assertNotNull(secondTask);
    assertEquals("Second Task", secondTask.getTitle());
    assertEquals(Priority.LOW, secondTask.getPriority());
    assertEquals(Status.CLOSED, secondTask.getStatus());
  }
}
