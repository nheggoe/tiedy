package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class JsonReaderTest {

  JsonReader taskReader = new JsonReader(Task.class, true);

  @Test
  void testReadEmptyFile() {
    try {
      assertEquals(0, taskReader.parseJsonStream().count());
    } catch (IOException e) {
      fail("IOException was thrown", e);
    }
  }
}
