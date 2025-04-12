package edu.ntnu.idi.bidata.tiedy.backend.model.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

  @Test
  void testCopyConstructor() {
    User user = new User("user123", "StrongPassword");
    User copy = new User(user);
    user.setUsername("newUsername");

    assertThat(user.getUsername())
        .withFailMessage("Mutation of copy should not affect the original")
        .isNotEqualTo(copy.getUsername());

    assertThat(user)
        .withFailMessage("equals() should return true when comparing copy with original")
        .isEqualTo(copy);
  }

  @Test
  void testUserWeakPassword() {

    assertThrows(
        IllegalArgumentException.class,
        () -> new User("user123", "weak"),
        "Weak password should throw exception");
    assertDoesNotThrow(
        () -> new User("user1233", "StrongPassword"), "Strong password should not throw exception");
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
