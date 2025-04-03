package edu.ntnu.idi.bidata.tiedy.backend.repository.json;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.repository.UserRepository;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;
import java.util.Objects;
import java.util.Optional;

public class JsonUserRepository extends JsonRepository<User> implements UserRepository {

  private static JsonUserRepository instance;

  private JsonUserRepository() {
    super(User.class, User::getId);
  }

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
  public Optional<User> authenticate(String username, String password) {
    return findByUsername(username)
        .filter(user -> PasswordUtil.checkPassword(password, user.getPassword()));
  }
}
