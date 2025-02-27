package edu.ntnu.idi.bidata.tiedy.backend.user;

/**
 * @author Nick Heggø
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
    if(isUserInformationCorrect(userName, emailAddress, userID)){
      System.out.println("You have successfully logged in.");
    }
  }

  public boolean isUserInformationCorrect(String userName, String emailAddress, int userID) {
    if (isUserNameCorrect(userName) && isEmailAddressCorrect(emailAddress)) {
      return isUserIDCorrect(userID);
    }
    return false;
  }

  public boolean isEmailAddressCorrect(String emailAddress) {
    return emailAddress.equals(getEmailAddress());
  }

  public boolean isUserIDCorrect(int userID) {
    return(userID == getUserID());
  }

  public boolean isUserNameCorrect(String userName) {
    return(userName.equals(getUserName()));
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }
}


