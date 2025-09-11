package planner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task with a specific date that needs to be completed by.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Creates a deadline task.
     *
     * @param tsk description
     * @param byStr due date
     * @throws java.time.format.DateTimeParseException if {@code byStr} is invalid
     */
    public Deadline(String tsk, String byStr) {
        super(tsk);
        this.by = LocalDate.parse(byStr);
    }

    /**
     * Creates a deadline task.
     *
     * @param tsk description of the deadline
     * @param by  due date as a LocalDate instance
     */
    public Deadline(String tsk, LocalDate by) {
        super(tsk);
        this.by = by;
    }

    /**
     * Returns the due date in ISO-8601 format.
     *
     * @return {@code yyyy-MM-dd}
     */
    public String getByIso() {
        return by.toString();
    }

    /** Human-readable date. */
    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + ")";
    }

}