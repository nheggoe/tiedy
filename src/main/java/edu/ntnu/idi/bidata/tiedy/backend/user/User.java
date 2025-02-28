package edu.ntnu.idi.bidata.tiedy.backend.user;

import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * The object that represents the customers of the application.
 *
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.02.28
 */
public class User {

  private int id;
  private String username;
  private String password;
  private String email;
  private LocalDateTime createdAt;

  /**
   * Default constructor
   */
  public User() {}

  /**
   * Constructor to create users with the given arguments
   *
   * @param username the name of the user
   * @param email    the email address of the user
   */
  public User(String username, String plainTextPassword, String email) {
    setUsername(username);
    setPassword(plainTextPassword);
    setEmail(email);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String plainTextPassword) {
    checkPasswordStrength(plainTextPassword);
    this.password = PasswordUtil.hashPassword(plainTextPassword);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Validates if the provided email address adheres to a standard email format.
   *
   * @param emailAddress the email address to be validated
   */
  private void validateEmailFormat(String emailAddress) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (emailAddress == null) {
      throw new NullPointerException("email address cannot be null!");
    }
    if (!pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }

  private void checkPasswordStrength(String plainTextPassword) {
    // TODO add a specification for the password strength
  }

}
