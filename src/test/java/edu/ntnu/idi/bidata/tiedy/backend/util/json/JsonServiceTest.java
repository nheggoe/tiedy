package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JsonServiceTest {

  private static final JsonService testPlayerJsonService = new JsonService(User.class, true);

  @BeforeAll
  static void setUp() throws IOException {
    User user1 = new User("User1", "strongPassword", "email@example.com");
    var users =
        Stream.of(
            user1,
            new User("User2", "strongPassword", "email@example.com"),
            new User("User3", "strongPassword", "email@example.com"));
    testPlayerJsonService.writeCollection(users);
  }

  @Test
  void testReadPlayers() {
    try {
      assertTrue(testPlayerJsonService.loadJsonAsStream().findAny().isPresent());
      assertEquals(3, testPlayerJsonService.loadJsonAsStream().count());
    } catch (IOException e) {
      fail(e);
    }
  }

  /*
    @Test
    void testRetrieveTaskFromUser(){

      try {

      } catch (IOException e){
        fail(e);
      }

    }
  */
}
