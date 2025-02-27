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


  public User(String userName, String emailAddress, int userID) {
    setUserName(userName);
    setEmailAddress(emailAddress);
    setUserID(userID);
  }

  public void login(String userName, String emailAddress, int userID) {
    if (isUserInformationCorrect(userName, emailAddress, userID)) {
      // grant access to the application
    }
  }

  public void logout() {
    // revoke access to the application and
  }

  public boolean isUserInformationCorrect(String userName, String emailAddress, int userID) {
    return isUserNameCorrect(userName)
        && isEmailAddressCorrect(emailAddress)
        && isUserIDCorrect(userID);
  }

  public boolean isEmailAddressCorrect(String emailAddress) {
    return emailAddress.equals(getEmailAddress());
  }

  public boolean isUserIDCorrect(int userID) {
    return userID == getUserID();
  }

  public boolean isUserNameCorrect(String userName) {
    return userName.equals(getUserName());
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    if (userName == null || userName.isBlank()) {
      throw new IllegalArgumentException("User name cannot be empty!");
    }
    this.userName = userName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    if (isEmailAddressValid(emailAddress)) {
      this.emailAddress = emailAddress;
    }
  }

  public int getUserID() {
    return userID;
  }

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

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof User user)) {
      return false;
    }
    return userID == user.userID && userName.equals(user.userName) && emailAddress.equals(user.emailAddress);
  }

  @Override
  public int hashCode() {
    int result = userID;
    result = 31 * result + userName.hashCode();
    result = 31 * result + emailAddress.hashCode();
    return result;
  }
}


