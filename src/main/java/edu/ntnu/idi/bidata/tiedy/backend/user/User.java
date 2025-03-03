package edu.ntnu.idi.bidata.tiedy.backend.user;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.02.28
 */
public class User {

  private UUID userID;
  private String userName;
  private String emailAddress;

  private User() {
    // a default constructor is needed for the jackson JSON library
  }

  /**
   * Constructor to create users with the given arguments
   *
   * @param userName     the name of the user
   * @param emailAddress the email address of the user
   */
  public User(String userName, String emailAddress) {
    setUserName(userName);
    setEmailAddress(emailAddress);
    userID = UUID.randomUUID();
  }

  /**
   * Method to grant a user access to the application if the terms are met.
   */
  public void login() {
    // TODO grant access to the application
  }

  /**
   * Method to revoke access to the application.
   */
  public void logout() {
    // TODO revoke access to the application and
  }

  /**
   * Method to check if the user's information is correct.
   *
   * @param userName     name of the user
   * @param emailAddress email address of the user
   * @param userID       internal ID of the user
   * @return true if the information is correct, false otherwise
   */
  public boolean isUserInformationCorrect(String userName, String emailAddress) {
    return isUserNameCorrect(userName)
        && isEmailAddressCorrect(emailAddress);
  }

  /**
   * Method to check if the email address is correct.
   *
   * @param emailAddress email address to check
   * @return true if the email address is correct, false otherwise
   */
  public boolean isEmailAddressCorrect(String emailAddress) {
    return emailAddress.equals(getEmailAddress());
  }

  /**
   * Method to check if the user ID is correct.
   *
   * @param userID the user ID to check
   * @return true if the user ID is correct, false otherwise
   */
  public boolean isUserIDCorrect(UUID userID) {
    return userID.equals(getUserID());
  }

  /**
   * Method to check if the username is correct.
   *
   * @param userName the username to check
   * @return true if the username is correct, false otherwise
   */
  public boolean isUserNameCorrect(String userName) {
    return userName.equals(getUserName());
  }

  /**
   * Retrieves the username of the user.
   *
   * @return the username of the user
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the username of the user.
   *
   * @param userName the username to be set
   */
  public void setUserName(String userName) {
    if (userName == null || userName.isBlank()) {
      throw new IllegalArgumentException("User name cannot be empty!");
    }
    this.userName = userName;
  }

  /**
   * Retrieves the email address of the user.
   *
   * @return the email address of the user
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * Sets the email address of the user.
   *
   * @param emailAddress the email address to be set
   */
  public void setEmailAddress(String emailAddress) {
    validateEmail(emailAddress);
    this.emailAddress = emailAddress;
  }

  /**
   * Retrieves the user ID.
   *
   * @return the user ID
   */
  public UUID getUserID() {
    return userID;
  }

  /**
   * Generates a random UUID
   */
  public void setUserID() {
    this.userID = UUID.randomUUID();
  }

  /**
   * Validates if the provided email address adheres to a standard email format.
   *
   * @param emailAddress the email address to be validated
   */
  private void validateEmail(String emailAddress) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (emailAddress == null) {
      throw new NullPointerException("email address cannot be null!");
    }
    if (!pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
  }

  /**
   * Compares this User object to the specified object for equality.
   *
   * @param o the object to be compared for equality with this User
   * @return true if the specified object is equal to this User, false otherwise
   */
  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) return false;

    return userID.equals(user.userID) && userName.equals(user.userName) && emailAddress.equals(user.emailAddress);
  }

  /**
   * Computes the hash code for the User object based on its fields.
   * <p>
   * The hash code is calculated using the hash codes of the userID, userName,
   * and emailAddress fields, combined with a multiplier to ensure a unique
   * and consistent hash code for each object state.
   *
   * @return an integer value representing the hash code of the User object
   */
  @Override
  public int hashCode() {
    int result = userID.hashCode();
    result = 31 * result + userName.hashCode();
    result = 31 * result + emailAddress.hashCode();
    return result;
  }
}
