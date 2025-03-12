package edu.ntnu.idi.bidata.tiedy.backend.util.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JsonPathUtilTest {

  @Test
  void testGenerateTestPath() {
    boolean isTest = true;
    Class<?> targetClass = User.class;
    User classInstance = new User();
    Path expectedJsonPath = Path.of("src/test/resources/json/User.json");
    assertEquals(expectedJsonPath, JsonPathUtil.getJsonFilePath(targetClass, isTest));
    assertEquals(expectedJsonPath, JsonPathUtil.getJsonFilePath(classInstance, isTest));
  }

  @Test
  void testGenerateProdPath() {
    boolean isTest = false;
    Class<?> targetClass = User.class;
    User classInstance = new User();
    Path expectedJsonPath = Path.of("data/json/User.json");
    assertEquals(expectedJsonPath, JsonPathUtil.getJsonFilePath(targetClass, isTest));
    assertEquals(expectedJsonPath, JsonPathUtil.getJsonFilePath(classInstance, isTest));
  }
}
