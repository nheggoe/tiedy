package edu.ntnu.idi.bidata.tiedy.backend.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using the BCrypt algorithm. Provides static
 * methods to hash plain-text passwords and verify their validity against hashed passwords securely.
 *
 * @author Nick Heggø
 * @version 2025.03.25
 */
public class PasswordUtil {

  private static final int LOG_ROUNDS = 12;

  private PasswordUtil() {}

  /**
   * Hashes a plain-text password using the BCrypt algorithm.
   *
   * @param plainTextPassword the plain-text password to be hashed
   * @return the hashed representation of the password
   */
  public static String hashPassword(String plainTextPassword) {
    String salt = BCrypt.gensalt(LOG_ROUNDS);

    return BCrypt.hashpw(plainTextPassword, salt);
  }

  /**
   * Verifies if the provided plain text password matches the given hashed password.
   *
   * @param plainTextPassword the plain-text password to be verified
   * @param hashedPassword the hashed password to compare against
   * @return true if the plain text password matches the hashed password, false otherwise, or if
   *     inputs are null
   */
  public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
    if (plainTextPassword == null || hashedPassword == null) {
      return false;
    }

    try {
      return BCrypt.checkpw(plainTextPassword, hashedPassword);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Validates the format of a provided plain-text password to ensure it does not contain any
   * special characters and is not blank or null.
   *
   * @param plainTextPassword the plain-text password to be validated
   * @throws IllegalArgumentException if the password is null, blank or contains invalid special
   *     characters
   */
  public static void validatePasswordFormat(String plainTextPassword) {
    String invalidChars = "(){}[]|`¬¦'£%^&*\"<>:;#~\\_-+=,@ \t";
    if (plainTextPassword == null || plainTextPassword.isBlank()) {
      throw new IllegalArgumentException("Password cannot be blank!");
    }
    for (char c : plainTextPassword.toCharArray()) {
      if (invalidChars.contains(String.valueOf(c))) {
        throw new IllegalArgumentException("Password cannot contain special characters!");
      }
    }
  }

  /**
   * Validates the strength of a given plain-text password. Ensures the password meets certain
   * criteria, including being non-null, not blank, at least 8 characters long and containing at
   * least one lowercase letter.
   *
   * @param plainTextPassword the plain-text password to be validated
   * @throws IllegalArgumentException if the password is null, blank, shorter than 8 characters or
   *     does not contain at least one lowercase letter
   */
  public static void validatePasswordStrength(String plainTextPassword) {
    if (plainTextPassword == null || plainTextPassword.isBlank()) {
      throw new IllegalArgumentException("Password cannot be blank!");
    }
    if (plainTextPassword.length() < 8) {
      throw new IllegalArgumentException("Password must be at least 8 characters long!");
    }
    if (!plainTextPassword.matches(".*[a-z].*")) {
      throw new IllegalArgumentException("Password must contain at least one lowercase letter!");
    }
  }
}
