package planner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate by;

    public Deadline(String tsk, String byStr) {
        super(tsk);
        this.by = LocalDate.parse(byStr);
    }

    public Deadline(String tsk, LocalDate by) {
        super(tsk);
        this.by = by;
    }

    public String getByIso() {
        return by.toString();
    }

    public String getBy() {
        return by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getBy() + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone() ? 1 : 0) + " | " + getTask() + " | " + getByIso();
    }
}