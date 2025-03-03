package edu.ntnu.idi.bidata.tiedy.backend.level;

import edu.ntnu.idi.bidata.tiedy.backend.task.Priority;
import edu.ntnu.idi.bidata.tiedy.backend.task.Status;
import edu.ntnu.idi.bidata.tiedy.backend.task.Task;
import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        task = new Task(1,"Test Task","Task used for testing"
                , Status.IN_PROGRESS, user, LocalDate.now().plusDays(7), Priority.HIGH);
    }

    @Test
    void testGetId() {
        assertEquals(1, task.getId());
    }

    @Test
    void testSetId() {
        task.setId(2);
        assertEquals(2, task.getId());
    }

    @Test
    void testSetInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> task.setId(0));
        assertThrows(IllegalArgumentException.class, () -> task.setId(-1));
    }

    @Test
    void testGetTitle() {
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    void testSetTitle() {
        task.setTitle("New Test Title");
        assertEquals("New Task Title", task.getTitle());
    }

    @Test
    void testSetInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> task.setTitle(""));
    }

    @Test
    void testGetDescription() {
        assertEquals("Task used for testing", task.getDescription());
    }

    @Test
    void testSetDescription() {
        task.setDescription("New task description");
        assertEquals("New task description", task.getDescription());
    }

    @Test
    void testInvalidDescription() {
        assertThrows(IllegalArgumentException.class, () -> task.setDescription(""));
    }

    @Test
    void testGetStatus() {
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void testSetStatus() {
        task.setStatus(Status.CLOSED);
        assertEquals(Status.CLOSED, task.getStatus());
    }

    @Test
    void testGetAssignedTo() {
        assertEquals(user, task.getAssignedTo());
    }

    //Add testSetAssignedTo when user class is more developed
}
