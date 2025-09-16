package planner;

/**
 * A task with a time range.
 */
public class Event extends Task {
    private final String from;

    private final String to;

    /**
     * Creates an event task with a free-form start and end text.
     *
     * @param tsk description
     * @param from start time string
     * @param to end time string
     */
    public Event(String tsk, String from, String to) {
        super(tsk);
        this.from = from;
        this.to = to;
    }

    /** @return the start time text */
    public String getFrom() {
        return this.from;
    }

    /** @return the end time text */
    public String getTo() {
        return this.to;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}