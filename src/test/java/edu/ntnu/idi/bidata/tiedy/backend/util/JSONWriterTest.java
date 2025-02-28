package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class JSONWriterTest {

  final String userJsonFile = "src/test/resources/edu/ntnu/idi/bidata/tiedy/json/users.json";
  final ObjectMapper objectMapper = new ObjectMapper();

  final List<User> users = List.of(
      new User("John Doe", PasswordUtil.hashPassword("verysafe"), "johndoe@example.com"),
      new User("Jane Smith", PasswordUtil.hashPassword("OdinIsBest"), "janesmith@example.com"),
      new User("Alex Johnson", PasswordUtil.hashPassword("nice"), "alexjohnson@example.com"),
      new User("Emily Davis", PasswordUtil.hashPassword("strongPassword"), "emilydavis@example.com"),
      new User("Michael Brown", PasswordUtil.hashPassword("notPlainText"), "michaelbrown@example.com"));

  @Test
  void testWriteToResource() {
    assertDoesNotThrow(() -> objectMapper.writeValue(new File(userJsonFile), users));
  }
}
