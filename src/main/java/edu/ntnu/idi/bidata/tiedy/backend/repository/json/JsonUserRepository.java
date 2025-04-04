package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.util.Objects;
import java.util.Optional;

/**
 * JsonUserRepository is a concrete implementation of the {@link UserRepository} interface using
 * JSON as the persistence mechanism. It extends {@link JsonRepository} to inherit common CRUD
 * operations while adding specific functionality required to manage {@link User} entities.
 *
 * <p>This repository is implemented as a singleton to ensure that only one instance exists at any
 * time, providing centralized access to user data throughout the application.
 *
 * <p>The user-specific methods support functionalities such as finding a user by their username and
 * authenticating a user based on provided credentials.
 *
 * <p>All user data is persisted in JSON format and is managed in memory for easy access. Any
 * changes to the data are written to the underlying JSON storage periodically
 *
 * @see JsonRepository
 * @see UserRepository
 */
public class JsonUserRepository extends JsonRepository<User> implements UserRepository {

  private static JsonUserRepository instance;

  private JsonUserRepository() {
    super(User.class, User::getId);
  }

  /**
   * Returns the singleton instance of the {@link JsonUserRepository} class. Ensures that only one
   * instance of the repository is created and shared throughout the application.
   *
   * @return the single instance of {@link JsonUserRepository}.
   */
  public static synchronized JsonUserRepository getInstance() {
    if (instance == null) {
      instance = new JsonUserRepository();
    }
    return instance;
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return getAll().stream()
        .filter(user -> Objects.equals(user.getUsername(), username))
        .findFirst();
  }

  @Override
  public Optional<User> authenticate(String username, String plainTextPassword) {
    return findByUsername(username)
        .filter(
            user -> PasswordUtil.isPasswordCorrect(plainTextPassword, user.getHashedPassword()));
  }
}
