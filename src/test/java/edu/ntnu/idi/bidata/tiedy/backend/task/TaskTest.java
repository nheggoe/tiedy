package edu.ntnu.idi.bidata.tiedy.backend.task;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Task class Unit tests to check functionality of Task class
 *
 * @author Ida Løvås
 * @version 2025.03.03
 */
class TaskTest {

  private Task task;
  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    task =
        new Task(
            1,
            "Test Task",
            "Task used for testing",
            Status.IN_PROGRESS,
            user,
            LocalDate.now().plusDays(7),
            Priority.HIGH);
  }

  @Test
  void testGetId() {
    assertEquals(1, task.getId());
  }

  @Test
  void testSetId() {
    task.setId(2);
    assertEquals(2, task.getId());
  }

  @Test
  void testSetInvalidId() {
    assertThrows(IllegalArgumentException.class, () -> task.setId(0));
    assertThrows(IllegalArgumentException.class, () -> task.setId(-1));
  }

  @Test
  void testGetTitle() {
    assertEquals("Test Task", task.getTitle());
  }

  @Test
  void testSetTitle() {
    task.setTitle("New Task Title");
    assertEquals("New Task Title", task.getTitle());
  }

  @Test
  void testSetInvalidTitle() {
    assertThrows(IllegalArgumentException.class, () -> task.setTitle(""));
  }

  @Test
  void testGetDescription() {
    assertEquals("Task used for testing", task.getDescription());
  }

  @Test
  void testSetDescription() {
    task.setDescription("New task description");
    assertEquals("New task description", task.getDescription());
  }

  @Test
  void testInvalidDescription() {
    assertThrows(IllegalArgumentException.class, () -> task.setDescription(""));
  }

  @Test
  void testGetStatus() {
    assertEquals(Status.IN_PROGRESS, task.getStatus());
  }

  @Test
  void testSetStatus() {
    task.setStatus(Status.CLOSED);
    assertEquals(Status.CLOSED, task.getStatus());
  }

  // Add testSetAssignedTo when user class is more developed

  @Test
  void testGetDeadline() {
    assertEquals(LocalDate.now().plusDays(7), task.getDeadline());
  }

  @Test
  void testSetDeadline() {
    LocalDate newDeadline = LocalDate.now().plusDays(1);
    task.setDeadline(newDeadline);
    assertEquals(newDeadline, task.getDeadline());
  }

  @Test
  void testSetInvalidDeadline() {
    LocalDate pastDate = LocalDate.now().minusDays(3);
    assertThrows(IllegalArgumentException.class, () -> task.setDeadline(pastDate));
  }

  @Test
  void testGetPriority() {
    assertEquals(Priority.HIGH, task.getPriority());
  }

  @Test
  void testSetPriority() {
    task.setPriority(Priority.LOW);
    assertEquals(Priority.LOW, task.getPriority());
  }

  @Test
  void testSetInvalidPriority() {
    assertThrows(IllegalArgumentException.class, () -> task.setPriority(null));
  }
}
