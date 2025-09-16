package planner;

import java.util.ArrayList;
import java.util.List;

/**
 * A mutable list wrapper that stores {@link Task} objects
 */
public class TaskList {
    private final List<Task> tasks;

    /** Constructs an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list populated with the given tasks.
     *
     * @param initial tasks loaded from storage
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial);
    }

    /** @return current number of tasks */
    public int size() {
        return tasks.size();
    }

    /** @return the backing list. Should not modify its structure arbitrarily */
    public List<Task> asList() {
        return tasks;
    }

    /** Adds a task to the end of the list. */
    public void add(Task t) {
        assert t != null : "task to add must not be null";
        tasks.add(t);
    }

    /**
     * Returns the task with an index.
     *
     * @param i index number
     * @return the task
     */
    public Task get(int i) {
        assert i >= 0 && i < tasks.size() : "get index out of bounds";
        return tasks.get(i);
    }

    /**
     * Removes and returns the task at index.
     *
     * @param i index number
     * @return removed task
     */
    public Task remove(int i) {
        assert i >= 0 && i < tasks.size() : "remove index out of bounds";
        return tasks.remove(i);
    }
}