package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @version 2025.02.28
 */
class JSONWriterTest {

  List<User> users = List.of(
      new User("John Doe", "johndoe@example.com", 1),
      new User("Jane Smith", "janesmith@example.com", 2),
      new User("Alex Johnson", "alexjohnson@example.com", 3),
      new User("Emily Davis", "emilydavis@example.com", 4),
      new User("Michael Brown", "michaelbrown@example.com", 5)
  );

  @Test
  void testWriteToResource() {
    ObjectMapper objectMapper = new ObjectMapper();
    assertDoesNotThrow(() -> {
      objectMapper.writeValue(new File("src/main/resources/edu/ntnu/idi/bidata/tiedy/json/users.json"), users);
    });
  }

}
