package planner;

public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String tsk, String from, String to) {
        super(tsk);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone() ? 1 : 0) + " | " + getTask() + " | " + from + " | " + to;
    }
}