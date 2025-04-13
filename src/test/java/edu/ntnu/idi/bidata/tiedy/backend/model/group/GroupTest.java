package edu.ntnu.idi.bidata.tiedy.backend.model.group;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GroupTest {

  @Test
  void testCopyConstructor() {
    User user1 = new User("John_Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user1.getId());
    group.setName("Original name");

    Group copyGroup = new Group(group);
    copyGroup.setName("New name");

    assertThat(group.getName())
        .withFailMessage("Mutation of the copy object should not affect the original object")
        .isNotEqualTo(copyGroup.getName());

    assertThat(group)
        .withFailMessage("equals() should return true when comparing original and copy")
        .isEqualTo(copyGroup);

    User user2 = new User("David_S", "safePassword");
    copyGroup.addMember(user2.getId(), false);

    assertThat(group.getMembers())
        .withFailMessage("Mutation of the copy object should not affect the original object")
        .isNotEqualTo(copyGroup.getMembers());
  }

  @Test
  void testGetName() {
    User user = new User("John_Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    assertEquals("Test Group", group.getName());
  }

  @Test
  void testAddMember() {
    User user = new User("John_Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    User newUser = new User("Jane_Doe", "securePassword");
    group.addMember(newUser.getId(), false);

    assertTrue(group.getMembers().containsKey(newUser.getId()));
    assertFalse(group.getMembers().get(newUser.getId()));
  }

  @Test
  void testAddMemberWithAdminStatus() {
    User user = new User("John_Doe", "safePassword");
    Group group = new Group("Test Group", "This is a test group", user.getId());

    User newAdmin = new User("Admin_User", "strongPassword");
    group.addMember(newAdmin.getId(), true);

    Map<UUID, Boolean> members = group.getMembers();
    assertTrue(members.containsKey(newAdmin.getId()));
    assertTrue(members.get(newAdmin.getId()));
  }
}
