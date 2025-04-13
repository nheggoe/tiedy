package edu.ntnu.idi.bidata.tiedy.backend.model.task;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents the status of a task. The status can be set to one of the predefined states: OPEN,
 * IN_PROGRESS, POSTPONED, CLOSED. This enum will be used to provide information about the state of
 * a task.
 *
 * @author Odin Arvhage and Nick Heggø
 * @version 2025.04.09
 */
public enum Status {
  OPEN,
  IN_PROGRESS,
  POSTPONED,
  CLOSED;

  private final String displayName;

  Status() {
    this.displayName =
        Arrays.stream(name().split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
  }

  public String getDisplayName() {
    return displayName;
  }
}
