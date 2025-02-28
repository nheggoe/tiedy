package edu.ntnu.idi.bidata.tiedy.backend.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTest {

  final String userJsonFile = "src/main/resources/edu/ntnu/idi/bidata/tiedy/json/users.json";
  final ObjectMapper objectMapper = new ObjectMapper();
  final List<User> users;

  {
    try {
      users = objectMapper.readValue(new File(userJsonFile), new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testCreateAnEmptyFamily() {
    assertThrows(IllegalStateException.class, () -> new Family((Collections.emptyList())));
  }

  @Test
  void testRemoveNullFamilyMember() {
    Family family = new Family(users.getFirst());

    assertThrows(NullPointerException.class, () -> family.removeFamilyMember(null));
  }

  @Test
  void testNullDoesNotExistInFamily() {
    Family family = new Family(users.getFirst());

    assertFalse(family.getFamilyMembers().contains(null));
  }

  @Test
  void testRemoveExistingFamilyMember() {
    Family family = new Family(List.of(users.get(1), users.get(2), users.get(3)));

    family.removeFamilyMember(users.get(3));

    assertFalse(family.getFamilyMembers().contains(users.get(3)));
    assertTrue(family.getFamilyMembers().contains(users.get(2)));
    assertTrue(family.getFamilyMembers().contains(users.get(1)));
  }

  @Test
  void testRemoveNonExistentFamilyMember() {
    Family family = new Family(users);

    family.removeFamilyMember(users.get(4));

    assertEquals(4, family.getFamilyMembers().size());
    assertTrue(family.getFamilyMembers().contains(users.getFirst()));
    assertTrue(family.getFamilyMembers().contains(users.get(2)));
    assertTrue(family.getFamilyMembers().contains(users.get(3)));
  }

}
