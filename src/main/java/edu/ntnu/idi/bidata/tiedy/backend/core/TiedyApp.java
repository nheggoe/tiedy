package edu.ntnu.idi.bidata.tiedy.backend.core;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;

public class TiedyApp {

  private User user;

  public TiedyApp() {}

  public void Login(User user) {
    if (user == null) {
      throw new IllegalArgumentException("Login failed: Please try again");
    }

    this.user = user;
    System.out.printf("Welcome to Tiedy %s%n", user.getUsername());
  }

  public void Logout() {
    user = null;
  }
}
