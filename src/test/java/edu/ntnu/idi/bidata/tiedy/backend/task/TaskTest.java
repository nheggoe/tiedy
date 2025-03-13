package edu.ntnu.idi.bidata.tiedy.backend.task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class TaskTest {

  private Task task;

  @BeforeEach
  void setUp() {
    task = new Task("test task", "This is a test task");
  }
}
