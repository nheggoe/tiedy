package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserJsonService {
  private final String userJsonFile;
  private static final ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public UserJsonService() {
    this("src/main/resources/edu/ntnu/idi/bidata/tiedy/json/user.json");
  }

  public UserJsonService(String userJsonFile) {
    this.userJsonFile = userJsonFile;
  }

  /**
   * Saves a user to the JSON file. The user is added to the existing list of users, which is loaded
   * from the file. The modified list is then written back to the file.
   *
   * @param user the user to be saved
   * @throws IOException if an error occurs during file reading or writing
   */
  public void saveUser(User user) throws IOException {
    List<User> users = loadUser();
    users.add(user);
    objectMapper.writeValue(new File(userJsonFile), users);
  }

  /**
   * Checks if the given username is valid by verifying that it does not already exist among the
   * usernames of the existing users loaded from the JSON file.
   *
   * @param username the username to be validated
   * @return true if the username is unique and does not already exist, false otherwise
   * @throws IOException if an error occurs while loading the users from the JSON file
   */
  public boolean isUsernameValid(String username) throws IOException {
    List<User> users = loadUser();
    return users.stream().map(User::getUsername).noneMatch(username::equals);
  }

  /**
   * Loads the list of users from a JSON file. If the file exists, it parses the JSON content into a
   * list of User objects. If the file does not exist, it returns an empty list.
   *
   * @return a list of User objects loaded from the JSON file, or an empty list if the file does not
   *     exist
   * @throws IOException if an error occurs while reading the file or parsing the JSON content
   */
  public List<User> loadUser() throws IOException {
    File file = new File(userJsonFile);
    if (file.exists()) {
      return objectMapper.readValue(
          file, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
    } else {
      file.createNewFile();
      return new ArrayList<>();
    }
  }
}
