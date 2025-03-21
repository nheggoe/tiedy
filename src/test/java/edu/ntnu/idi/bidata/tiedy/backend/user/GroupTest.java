package edu.ntnu.idi.bidata.tiedy.backend.user;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class GroupTest {

  private final User user1 =
      new User("John Doe", PasswordUtil.hashPassword("safe"), "johndoe@example.com");
  private final User user2 =
      new User("Jane Smith", PasswordUtil.hashPassword("safe"), "janesmith@example.com");
  private final User user3 =
      new User("Alex Johnson", PasswordUtil.hashPassword("safe"), "alexjohnson@example.com");
  private final User user4 =
      new User("Emily Davis", PasswordUtil.hashPassword("safe"), "emilydavis@example.com");
  private final User user5 =
      new User("Michael Brown", PasswordUtil.hashPassword("safe"), "michaelbrown@example.com");

  @Test
  void testCreateAnEmptyFamily() {
    Collection<User> emptyCollection = Collections.emptyList();
    assertThrows(IllegalStateException.class, () -> new Group(emptyCollection));
  }

  @Test
  void testRemoveNullFamilyMember() {
    Group group = new Group(user1);

    assertThrows(NullPointerException.class, () -> group.removeFamilyMember(null));
  }

  @Test
  void testNullDoesNotExistInFamily() {
    Group group = new Group(user4);

    assertFalse(group.getMembers().contains(null));
  }

  @Test
  void testRemoveExistingFamilyMember() {
    Group group = new Group(List.of(user1, user2));

    group.removeFamilyMember(user1);

    assertFalse(group.getMembers().contains(user1));
    assertTrue(group.getMembers().contains(user2));
  }

  @Test
  void testRemoveNonExistentFamilyMember() {
    Group group = new Group(List.of(user1, user2, user3));

    group.removeFamilyMember(user5);

    assertEquals(3, group.getMembers().size());
    assertTrue(group.getMembers().contains(user1));
    assertTrue(group.getMembers().contains(user2));
    assertTrue(group.getMembers().contains(user3));
  }
}
