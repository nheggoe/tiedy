package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonServiceTest {
  private static final JsonService testPlayerJsonService = new JsonService(User.class, true);

  @BeforeAll
  static void setUp() throws IOException {
    var users =
        List.of(
            new User("User1", "strongPassword", "email@example.com"),
            new User("User2", "strongPassword", "email@example.com"),
            new User("User3", "strongPassword", "email@example.com"));
    testPlayerJsonService.writeCollection(users);
  }

  @Test
  void testReadPlayers() {
    List<User> users;
    try {
      users = testPlayerJsonService.loadCollection();
      assertFalse(users.isEmpty());
      assertEquals(3, users.size());
    } catch (IOException e) {
      fail(e);
    }
  }
}
