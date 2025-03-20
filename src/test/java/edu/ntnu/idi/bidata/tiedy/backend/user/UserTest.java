package edu.ntnu.idi.bidata.tiedy.backend.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void testUserWeakPassword() {

    assertThrows(
        IllegalArgumentException.class, () -> new User("user123", "weak", "valid@email.com"));
    assertDoesNotThrow(() -> new User("user1233", "StrongPassword", "valid@email.com"));
  }

  @Test
  void testPasswordValidFormat() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "strong but invalid", "valid@email.com"));
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "invalid\\passdword", "valid@email.com"));
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "stron{dfkj}gbutinvalid", "valid@email.com"));
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "str|ngb@tinvalid", "valid@email.com"));
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "strngb\ttinvalid", "valid@email.com"));
  }

  @Test
  void testUserInvalidEmail() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "strongPassword", "notvalid@.com"));
    assertDoesNotThrow(() -> new User("user1233", "strongPassword", "example@valid.com"));
  }
}
