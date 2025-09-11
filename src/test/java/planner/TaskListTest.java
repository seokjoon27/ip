package planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void add_increasesSizeAndStoresTask() {
        TaskList list = new TaskList();
        Task t = new Task("buy milk");
        list.add(t);

        assertEquals(1, list.size());
        assertEquals(t, list.get(0));
    }

    @Test
    public void remove_decreasesSizeAndReturnsTask() {
        TaskList list = new TaskList();
        Task t1 = new Task("task 1");
        Task t2 = new Task("task 2");
        list.add(t1);
        list.add(t2);

        Task removed = list.remove(0);

        assertEquals(t1, removed);
        assertEquals(1, list.size());
        assertEquals(t2, list.get(0));
    }
}