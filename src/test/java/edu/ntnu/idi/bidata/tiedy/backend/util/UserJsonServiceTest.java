package edu.ntnu.idi.bidata.tiedy.backend.util;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserJsonServiceTest {

  static final String USER_JSON_FILE = "src/test/resources/userTest.json";
  static final UserJsonService USER_JSON_SERVICE = new UserJsonService(USER_JSON_FILE);

  @Test
  void testAddUser() throws IOException {
    User user = new User("test", "test", "test");
    USER_JSON_SERVICE.saveUser(user);
    assertFalse(USER_JSON_SERVICE.isUsernameValid("test"));
  }
}
