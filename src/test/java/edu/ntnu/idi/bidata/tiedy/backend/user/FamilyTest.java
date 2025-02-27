package edu.ntnu.idi.bidata.tiedy.backend.user;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nick Heggø
 * @version 2025.02.27
 */
class FamilyTest {

  // switch to JSON when it is fully implemented
  User user1 = new User("John Doe", "johndoe@example.com", 1);
  User user2 = new User("Jane Smith", "janesmith@example.com", 2);
  User user3 = new User("Alex Johnson", "alexjohnson@example.com", 3);
  User user4 = new User("Emily Davis", "emilydavis@example.com", 4);
  User user5 = new User("Michael Brown", "michaelbrown@example.com", 5);

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
    Family family = new Family(user1);

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