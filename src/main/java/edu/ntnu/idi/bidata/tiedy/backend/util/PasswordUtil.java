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
   * Hashes a plain-text password using the BCrypt algorithm. The method ensures secure hashing by
   * incorporating a generated salt and other built-in BCrypt properties to produce a hashed
   * password suitable for secure storage.
   *
   * @param plainTextPassword the plain-text password to be hashed; must not be null, blank, or fail
   *     format validation
   * @return the hashed representation of the provided plain-text password
   * @throws IllegalArgumentException if the password is null, blank, or not in the valid format
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
  public static boolean isPasswordCorrect(String plainTextPassword, String hashedPassword) {
    if ((plainTextPassword == null) || (hashedPassword == null)) {
      return false;
    }
    try {
      return BCrypt.checkpw(plainTextPassword, hashedPassword);
    } catch (IllegalArgumentException ignored) {
      return false;
    }
  }
}
