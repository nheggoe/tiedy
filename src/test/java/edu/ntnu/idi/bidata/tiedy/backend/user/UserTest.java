package edu.ntnu.idi.bidata.tiedy.backend.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void testUserWeakPassword() {

    assertThrows(IllegalArgumentException.class, () -> new User("user123", "weak"));
    assertDoesNotThrow(() -> new User("user1233", "StrongPassword"));
  }

  @Test
  void testPasswordValidFormat() {
    assertThrows(IllegalArgumentException.class, () -> new User("user123", "strong but invalid"));
    assertThrows(IllegalArgumentException.class, () -> new User("user123", "invalid\\passdword"));
    assertThrows(
        IllegalArgumentException.class, () -> new User("user123", "stron{dfkj}gbutinvalid"));
    assertThrows(IllegalArgumentException.class, () -> new User("user123", "str|ngb@tinvalid"));
    assertThrows(IllegalArgumentException.class, () -> new User("user123", "strngb\ttinvalid"));
  }
}
