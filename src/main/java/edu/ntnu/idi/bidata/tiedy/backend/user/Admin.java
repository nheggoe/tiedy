package edu.ntnu.idi.bidata.tiedy.backend.user;

import java.util.List;

public class Admin extends User {

  public Admin(String userName, String emailAddress) {
    super(userName, emailAddress);
  }

  public void manageUsers(List<User> users) {
    listUsers(users);
  }

  private void listUsers(List<User> users) {
    for (User user : users) {
      System.out.println(user.getUserName());
    }
  }
}
