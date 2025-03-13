package edu.ntnu.idi.bidata.tiedy.frontend.controller;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.json.JsonService;
import java.io.IOException;
import java.util.stream.Stream;
import javafx.fxml.FXML;

public class LoginController {
  private final JsonService userService;

  public LoginController() {
    userService = new JsonService(User.class);
  }

  @FXML
  public void registerUser(String userName, String password, String email) {
    if (userName == null || userName.isBlank()) {
      throw new IllegalArgumentException("Username cannot be empty");
    }
    if (password == null || password.isBlank()) {
      throw new IllegalArgumentException("Password cannot be empty");
    }
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email cannot be empty");
    }

    try {
      Stream<User> userStream = userService.streamUsers();
      boolean isUserNameTaken = userStream.anyMatch(user -> user.getUsername().equals(userName));
      if (isUserNameTaken) {
        throw new IllegalArgumentException("Username already taken");
      }
      User newUser = new User(userName, password, email);
      Stream<User> updatedStream = Stream.concat(userService.streamUsers(), Stream.of(newUser));
      userService.writeCollection(updatedStream);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot save user", e);
    }
  }
}
