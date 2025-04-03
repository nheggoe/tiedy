package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.util.Optional;

public interface UserRepository extends DataRepository<User> {
  /**
   * Finds a user by username.
   *
   * @param username the username to search for
   * @return an Optional containing the user if found.
   */
  Optional<User> findByUsername(String username);

  /**
   * Authenticates a user with the provided credentials.
   *
   * @param username the username for authentication
   * @param password the password for authentication
   * @return an Optional containing the authentication user if successful.
   */
  Optional<User> authenticate(String username, String password);
}
