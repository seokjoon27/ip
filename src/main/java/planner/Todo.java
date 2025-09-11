package planner;

/**
 * A task without time fields.
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param tsk description of the todo task
     */
    public Todo(String tsk) {
        super(tsk);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}