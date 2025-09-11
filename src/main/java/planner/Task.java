package planner;

/**
 * Represents a generic task with the description and a completion flag.
 */
public class Task {
    private final String tsk;
    private boolean isDone;

    /**
     * Constructs a task with the given description.
     * Newly made tasks are set not-done.
     *
     * @param tsk description of the task
     */
    public Task(String tsk) {
        this.tsk = tsk;
        this.isDone = false;
    }

    /** Marks the task as completed. */
    public void markDone() {
        this.isDone = true;
    }

    /** Marks the task as not completed. */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the String value representing whether
     * this task has been completed.
     *
     * @return "X" if completed, or a single space " " if not completed
     */
    public String getIsDone() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns whether this task has been completed.
     *
     * @return {@code true} if completed
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getTask() {
        return this.tsk;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[" + getIsDone() + "] " + tsk;
    }
}