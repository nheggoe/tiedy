package edu.ntnu.idi.bidata.tiedy.backend.user;

import java.util.regex.Pattern;

/**
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.02.26
 */
public class User {

  private int userID;
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
   * @param userID       the id of the user
   */
  public User(String userName, String emailAddress, int userID) {
    setUserName(userName);
    setEmailAddress(emailAddress);
    setUserID(userID);
  }

  /**
   * Method to grant a user access to the application if the terms are met.
   *
   * @param userName     name of the user
   * @param emailAddress email address of the user
   * @param userID       internal ID of the user
   */
  public void login(String userName, String emailAddress, int userID) {
    if (isUserInformationCorrect(userName, emailAddress, userID)) {
      // grant access to the application
    }
  }

  /**
   * Method to revoke access to the application.
   */
  public void logout() {
    // revoke access to the application and
  }

  /**
   * Method to check if the user's information is correct.
   *
   * @param userName     name of the user
   * @param emailAddress email address of the user
   * @param userID       internal ID of the user
   * @return true if the information is correct, false otherwise
   */
  public boolean isUserInformationCorrect(String userName, String emailAddress, int userID) {
    return isUserNameCorrect(userName)
        && isEmailAddressCorrect(emailAddress)
        && isUserIDCorrect(userID);
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
  public boolean isUserIDCorrect(int userID) {
    return userID == getUserID();
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
    if (isEmailAddressValid(emailAddress)) {
      this.emailAddress = emailAddress;
    }
  }

  /**
   * Retrieves the user ID.
   *
   * @return the user ID
   */
  public int getUserID() {
    return userID;
  }

  /**
   * Sets the user ID.
   *
   * @param userID the user ID to be set
   */
  public void setUserID(int userID) {
    this.userID = userID;
  }

  /**
   * Validates if the provided email address adheres to a standard email format.
   *
   * @param emailAddress the email address to be validated
   * @return true if the email address is valid, false otherwise
   */
  private boolean isEmailAddressValid(String emailAddress) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (emailAddress == null) {
      throw new NullPointerException("email address cannot be null!");
    }
    if (!pattern.matcher(emailAddress).matches()) {
      throw new IllegalArgumentException("Invalid email format");
    }
    return true;
  }

  // TODO add documentation for this method.
  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) {
      return false;
    }
    return userID == user.userID && userName.equals(user.userName) && emailAddress.equals(user.emailAddress);
  }

  /**
   * Generates a hash code for the user.
   *
   * @return the hash code
   */
  @Override
  public int hashCode() {
    int result = userID;
    result = 31 * result + userName.hashCode();
    result = 31 * result + emailAddress.hashCode();
    return result;
  }
}
