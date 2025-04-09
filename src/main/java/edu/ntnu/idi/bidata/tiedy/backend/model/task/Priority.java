package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import java.util.Arrays;

/**
 * Represents the priority level of a task. The priority can be set to one of the predefined levels:
 * HIGH, MEDIUM, or LOW. This enum is used to classify and manage tasks based on their relative
 * importance or urgency.
 *
 * @author Nick Heggø
 * @version 2025.03.13
 */
public enum Priority {
  HIGH,
  MEDIUM,
  LOW,
  NONE;

  private final String displayName;

  Priority() {
    displayName =
        Arrays.stream(name().split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(java.util.stream.Collectors.joining(" "));
  }

  public String getDisplayName() {
    return displayName;
  }
}
