package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

  final String userJsonFile = "src/test/resources/edu/ntnu/idi/bidata/tiedy/json/users.json";
  final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void verifiesUserNamesFromJSONFile() {

    List<User> users = assertDoesNotThrow(() -> objectMapper.readValue(
        new File(userJsonFile),
        new TypeReference<>() {}));

    List<String> userNames = users.stream()
        .map(User::getUsername)
        .toList();

    List<String> expectedUserNames = List.of(
        "John Doe",
        "Jane Smith",
        "Alex Johnson",
        "Emily Davis",
        "Michael Brown");

    expectedUserNames.forEach(expectedName ->
        assertTrue(userNames.contains(expectedName), "Expected user name not found: " + expectedName));

    assertFalse(userNames.contains("Odin"), "User list should not contain 'Odin'");
  }

}
