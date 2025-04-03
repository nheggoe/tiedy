package edu.ntnu.idi.bidata.tiedy.backend.state.repository;

import edu.ntnu.idi.bidata.tiedy.backend.model.user.User;
import java.util.Optional;

public class JsonUserRepository extends JsonRepository<User> implements UserRepository {

  public JsonUserRepository() {
    super(User.class, User::getId);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.empty();
  }

  @Override
  public Optional<User> authenticate(String username, String password) {
    return Optional.empty();
  }
}
