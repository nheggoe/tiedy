package edu.ntnu.idi.bidata.tiedy.backend.model.group;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GroupTest {

  @Test
  void testGetName() {
    User user = new User("John Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    assertEquals("Test Group", group.getName());
  }

  @Test
  void testAddMember() {
    User user = new User("John Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    User newUser = new User("Jane Doe", "securePassword");
    group.addMember(newUser.getId(), false);

    assertTrue(group.getMembers().containsKey(newUser.getId()));
    assertFalse(group.getMembers().get(newUser.getId()));
  }

  @Test
  void testAddMemberWithAdminStatus() {
    User user = new User("John Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    User newAdmin = new User("Admin User", "strongPassword");
    group.addMember(newAdmin.getId(), true);

    Map<UUID, Boolean> members = group.getMembers();
    assertTrue(members.containsKey(newAdmin.getId()));
    assertTrue(members.get(newAdmin.getId()));
  }
}
