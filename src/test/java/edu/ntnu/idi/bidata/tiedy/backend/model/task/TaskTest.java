package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void testCopyConstructor() {
    Task originalTask = new Task();
    originalTask.setTitle("Original title");
    originalTask.setDescription("Original description");

    Task copyTask = new Task(originalTask);
    copyTask.setTitle("New title");
    copyTask.setDescription("New description");

    assertThat(originalTask.getTitle())
        .withFailMessage("Modification to the copy object should not affect the original one")
        .isNotEqualTo(copyTask.getTitle());

    assertThat(originalTask.getDescription())
        .withFailMessage("Modification to the copy object should not affect the original one")
        .isNotEqualTo(copyTask.getDescription());

    assertThat(originalTask)
        .withFailMessage("Copy object should still return true when using equals()")
        .isEqualTo(copyTask);
  }

  @Test
  void testAddAssignedUser() {
    Task task = new Task();
    User user = new User("testUser", "StrongPass123");

    task.addAssignedUser(user);

    assertTrue(task.getAssignedUsers().contains(user.getId()));
    assertEquals(1, task.getAssignedUsers().size());
  }

  @Test
  void testAddDuplicateUser() {
    Task task = new Task();
    User user = new User("testUser", "StrongPass123");

    task.addAssignedUser(user);
    task.addAssignedUser(user);

    assertEquals(1, task.getAssignedUsers().size());
    Set<UUID> assignedUserIds = task.getAssignedUsers();
    assertTrue(assignedUserIds.contains(user.getId()));
  }

  @Test
  void testAddNullUser() {
    Task task = new Task();

    assertThrows(IllegalArgumentException.class, () -> task.addAssignedUser(null));
    assertEquals(0, task.getAssignedUsers().size());
  }

  @Test
  void testAddUserWithSameUUID() {
    Task task = new Task();
    UUID sharedId = UUID.randomUUID();
    User user1 =
        new User("testUser1", "StrongPass123") {
          @Override
          public UUID getId() {
            return sharedId;
          }
        };
    User user2 =
        new User("testUser2", "StrongPass123") {
          @Override
          public UUID getId() {
            return sharedId;
          }
        };

    task.addAssignedUser(user1);
    task.addAssignedUser(user2);

    assertEquals(1, task.getAssignedUsers().size());
    assertTrue(task.getAssignedUsers().contains(sharedId));
  }
}
