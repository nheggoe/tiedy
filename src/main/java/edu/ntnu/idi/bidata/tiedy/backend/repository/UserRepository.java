package edu.ntnu.idi.bidata.tiedy.backend.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Optional;

/**
 * UserRepository acts as a data-access abstraction layer specifically tailored to manage User
 * entities in the application. It extends the DataRepository interface, inheriting general CRUD and
 * data management operations, and adds extra methods for user-specific operations.
 *
 * <p>This interface encapsulates operations such as finding users by their username and user
 * authentication, ensuring that any repository implementation adheres to these requirements.
 *
 * <p>Implementations should provide the actual logic for storing, retrieving, updating, and
 * removing User objects while maintaining any persistence mechanisms such as file systems or
 * databases.
 *
 * @param <User> the type of entity managed by this repository, specifically a User
 */
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
