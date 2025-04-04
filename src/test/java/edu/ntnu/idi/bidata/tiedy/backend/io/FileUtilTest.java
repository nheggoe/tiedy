package edu.ntnu.idi.bidata.tiedy.backend.io;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  @Test
  void testGenerateFilePathWithValidInputsForTestFile() {
    boolean isTest = true;
    String fileExtension = "json";
    Class<?> targetClass = FileUtilTest.class;

    Path result = FileUtil.generateFilePath(targetClass, fileExtension, isTest);

    Path expectedPath = Path.of("src/test/resources/json/FileUtilTest.json");
    assertEquals(expectedPath, result);
  }

  @Test
  void testGenerateFilePathWithValidInputsForProductionFile() {
    boolean isTest = false;
    String fileExtension = "csv";
    Class<?> targetClass = FileUtil.class;

    Path result = FileUtil.generateFilePath(targetClass, fileExtension, isTest);

    Path expectedPath = Path.of("data/csv/FileUtil.csv");
    assertEquals(expectedPath, result);
  }

  @Test
  void testGenerateFilePathWithUnsupportedFileExtension() {
    boolean isTest = true;
    String fileExtension = "xml";
    Class<?> targetClass = FileUtil.class;

    UnsupportedOperationException exception =
        assertThrows(
            UnsupportedOperationException.class,
            () -> FileUtil.generateFilePath(targetClass, fileExtension, isTest));
    assertEquals(">xml< is not a supported file extension", exception.getMessage());
  }

  @Test
  void testGenerateFilePathWithInvalidClass() {
    boolean isTest = true;
    String fileExtension = "csv";
    Class<?> targetClass = null; // This should not happen in normal use

    assertThrows(
        NullPointerException.class,
        () -> FileUtil.generateFilePath(targetClass, fileExtension, isTest));
  }

  @Test
  void testGenerateFilePathSupportsAllDefinedExtensions() {
    Class<?> targetClass = FileUtil.class;
    boolean isTest = false;
    Set<String> supportedExtensions = Set.of("json", "csv");

    for (String extension : supportedExtensions) {
      Path result = FileUtil.generateFilePath(targetClass, extension, isTest);

      Path expectedPath = Path.of("data/" + extension + "/FileUtil." + extension);
      assertEquals(expectedPath, result);
    }
  }
}
