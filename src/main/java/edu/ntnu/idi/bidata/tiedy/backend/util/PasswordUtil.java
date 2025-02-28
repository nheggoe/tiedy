package edu.ntnu.idi.bidata.tiedy.backend.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for password hashing and verification using the BCrypt algorithm.
 * Provides static methods to hash plain-text passwords and verify their validity
 * against hashed passwords securely.
 *
 * @author Nick Heggø
 * @version 2025.02.28
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
   * @param hashedPassword    the hashed password to compare against
   * @return true if the plain text password matches the hashed password, false otherwise, or if inputs are null
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
}
