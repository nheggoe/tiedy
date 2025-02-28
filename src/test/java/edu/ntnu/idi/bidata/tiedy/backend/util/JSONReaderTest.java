package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Nick Heggø
 * @version 2025.02.27
 */

class JSONReaderTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void whenSerializeAndDeserializeUsingJackson_thenCorrect() {
    List<User> users = assertDoesNotThrow(() ->
        objectMapper.readValue(
            new File("src/main/resources/edu/ntnu/idi/bidata/tiedy/json/users.json"),
            new TypeReference<>() {}));

    List<String> names = users.stream().map(User::getUserName).toList();
    assertTrue(names.contains("John Doe"));
    assertTrue(names.contains("Jane Smith"));
    assertTrue(names.contains("Alex Johnson"));
    assertTrue(names.contains("Emily Davis"));
    assertTrue(names.contains("Michael Brown"));
  }

}
