package edu.ntnu.idi.bidata.tiedy.backend.util;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import org.junit.jupiter.api.Test;

class PasswordUtilTest {

  @Test
  void testHashPasswordReturnsNonNullValue() {
    String plainTextPassword = "securePassword123";

    String hashedPassword = PasswordUtil.hashPassword(plainTextPassword);

    assertNotNull(hashedPassword, "The hashed password should not be null.");
  }

  @Test
  void testHashPasswordGeneratesDifferentHashesForSamePassword() {
    String plainTextPassword = "securePassword123";

    String hashedPassword1 = PasswordUtil.hashPassword(plainTextPassword);
    String hashedPassword2 = PasswordUtil.hashPassword(plainTextPassword);

    assertNotEquals(
        hashedPassword1,
        hashedPassword2,
        "Hashing the same password twice should generate different hashes due to salting.");
  }

  @Test
  void testHashPasswordAndIsPasswordCorrectMatches() {
    String plainTextPassword = "securePassword123";

    String hashedPassword = PasswordUtil.hashPassword(plainTextPassword);
    boolean passwordMatches = PasswordUtil.isPasswordCorrect(plainTextPassword, hashedPassword);

    assertTrue(
        passwordMatches,
        "The checkPassword method should return true for a correct password and its hash.");
  }

  @Test
  void testIsPasswordFailsForIncorrectPasswordCorrect() {
    String correctPassword = "securePassword123";
    String incorrectPassword = "wrongPassword";
    String hashedPassword = PasswordUtil.hashPassword(correctPassword);

    boolean passwordMatches = PasswordUtil.isPasswordCorrect(incorrectPassword, hashedPassword);

    assertFalse(
        passwordMatches, "The checkPassword method should return false for an incorrect password.");
  }

  @Test
  void testIsPasswordCorrectFailsForNullInputs() {
    String plainTextPassword = "securePassword123";
    String hashedPassword = PasswordUtil.hashPassword(plainTextPassword);

    assertFalse(
        PasswordUtil.isPasswordCorrect(null, hashedPassword),
        "The checkPassword method should return false if the plain text password is null.");

    assertFalse(
        PasswordUtil.isPasswordCorrect(plainTextPassword, null),
        "The checkPassword method should return false if the hashed password is null.");

    assertFalse(
        PasswordUtil.isPasswordCorrect(null, null),
        "The checkPassword method should return false if both inputs are null.");
  }

  @Test
  void testUserRegisterProcess() {
    String plainTextPassword = "strongPassword";
    User testUser = new User("testUser123", plainTextPassword);
    assertTrue(PasswordUtil.isPasswordCorrect(plainTextPassword, testUser.getHashedPassword()));
  }
}
