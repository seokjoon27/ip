package planner;

public class Todo extends Task {
    public Todo(String tsk) {
        super(tsk);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toSaveFormat() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getTask();
    }
}