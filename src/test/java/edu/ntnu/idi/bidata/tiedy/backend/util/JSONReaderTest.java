package edu.ntnu.idi.bidata.tiedy.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Nick Heggø
 * @version 2025.02.27
 */
class JSONReaderTest {
  @Test
  void whenSerializeAndDeserializeUsingJackson_thenCorrect()
      throws IOException {
    User originalOdin = new User("Odin", "odin@email.com", 3);
    ObjectMapper mapper = new ObjectMapper();

    String jsonStr = mapper.writeValueAsString(originalOdin);
    User parsedOdin = mapper.readValue(jsonStr, User.class);
    assertEquals(originalOdin.getUserName(), parsedOdin.getUserName());
  }
}
