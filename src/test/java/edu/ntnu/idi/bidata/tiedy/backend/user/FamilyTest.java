package edu.ntnu.idi.bidata.tiedy.backend.user;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class FamilyTest {

  User user1 = new User("John Doe", PasswordUtil.hashPassword("safe"), "johndoe@example.com");
  User user2 = new User("Jane Smith", PasswordUtil.hashPassword("safe"), "janesmith@example.com");
  User user3 =
      new User("Alex Johnson", PasswordUtil.hashPassword("safe"), "alexjohnson@example.com");
  User user4 = new User("Emily Davis", PasswordUtil.hashPassword("safe"), "emilydavis@example.com");
  User user5 =
      new User("Michael Brown", PasswordUtil.hashPassword("safe"), "michaelbrown@example.com");

  @Test
  void testCreateAnEmptyFamily() {
    assertThrows(IllegalStateException.class, () -> new Family((Collections.emptyList())));
  }

  @Test
  void testRemoveNullFamilyMember() {
    Family family = new Family(user1);

    assertThrows(NullPointerException.class, () -> family.removeFamilyMember(null));
  }

  @Test
  void testNullDoesNotExistInFamily() {
    Family family = new Family(user4);

    assertFalse(family.getFamilyMembers().contains(null));
  }

  @Test
  void testRemoveExistingFamilyMember() {
    Family family = new Family(List.of(user1, user2));

    family.removeFamilyMember(user1);

    assertFalse(family.getFamilyMembers().contains(user1));
    assertTrue(family.getFamilyMembers().contains(user2));
  }

  @Test
  void testRemoveNonExistentFamilyMember() {
    Family family = new Family(List.of(user1, user2, user3));

    family.removeFamilyMember(user5);

    assertEquals(3, family.getFamilyMembers().size());
    assertTrue(family.getFamilyMembers().contains(user1));
    assertTrue(family.getFamilyMembers().contains(user2));
    assertTrue(family.getFamilyMembers().contains(user3));
  }
}
