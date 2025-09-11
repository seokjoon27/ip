package planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void markDone_changesStateCorrectly() {
        Task t = new Task("read book");
        assertFalse(t.isDone(), "Task should start as not done");

        t.markDone();
        assertTrue(t.isDone(), "Task should be marked as done after markDone()");
    }

    @Test
    public void toString_includesDoneStatus() {
        Task t = new Task("write notes");
        assertEquals("[ ] write notes", t.toString());

        t.markDone();
        assertEquals("[X] write notes", t.toString());
    }
}