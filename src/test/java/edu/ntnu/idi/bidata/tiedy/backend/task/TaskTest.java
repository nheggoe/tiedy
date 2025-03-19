package edu.ntnu.idi.bidata.tiedy.backend.task;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

  private Task task;

  @BeforeEach
  void setUp() {
    task = new Task("test task", "This is a test task");
  }

  @Test
  void testDefaultTaskValue() {
    assertEquals(Priority.NONE, task.getPriority());
    assertEquals(Status.OPEN, task.getStatus());
    assertTrue(task.getCreatedAt().isBefore(LocalDateTime.now()));
  }
}
