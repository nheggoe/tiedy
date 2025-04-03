package edu.ntnu.idi.bidata.tiedy.backend.io.json;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JsonPathUtilTest {

  @Test
  void testGenerateTestPath() {
    boolean isTest = true;
    Class<?> targetClass = User.class;
    User classInstance = new User("test", "test1234");
    Path expectedJsonPath = Path.of("src/test/resources/json/User.json");
    assertEquals(expectedJsonPath, JsonPathUtil.generateJsonPath(targetClass, isTest));
    assertEquals(expectedJsonPath, JsonPathUtil.generateJsonPath(classInstance, isTest));
  }

  @Test
  void testGenerateProdPath() {
    boolean isTest = false;
    Class<?> targetClass = User.class;
    User classInstance = new User("test", "test1234");
    Path expectedJsonPath = Path.of("data/json/User.json");
    assertEquals(expectedJsonPath, JsonPathUtil.generateJsonPath(targetClass, isTest));
    assertEquals(expectedJsonPath, JsonPathUtil.generateJsonPath(classInstance, isTest));
  }
}
