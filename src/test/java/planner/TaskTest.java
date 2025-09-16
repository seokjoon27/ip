package planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for {@link Task}.
 * Covers state transitions and string representation.
 */
public class TaskTest {

    /**
     * Verifies that {@code markDone()} flips the completion state from false to true.
     */
    @Test
    public void markDone_changesStateCorrectly() {
        Task t = new Task("read book");
        assertFalse(t.isDone(), "Task should start as not done");

        t.markDone();
        assertTrue(t.isDone(), "Task should be marked as done after markDone()");
    }

    /**
     * Verifies {@code toString()} reflects the done/undone status in the output.
     */
    @Test
    public void toString_includesDoneStatus() {
        Task t = new Task("write notes");
        assertEquals("[ ] write notes", t.toString());

        t.markDone();
        assertEquals("[X] write notes", t.toString());
    }
}