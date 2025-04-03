package edu.ntnu.idi.bidata.tiedy.backend.user;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.group.Group;
import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import org.junit.jupiter.api.Test;

class GroupTest {

  @Test
  void testGetName() {
    User user = new User("John Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user);

    assertEquals("Test Group", group.getName());
  }
}
