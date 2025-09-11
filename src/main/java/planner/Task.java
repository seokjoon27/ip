package planner;

public class Task {
    private final String tsk;
    private boolean isDone;

    public Task(String tsk) {
        this.tsk = tsk;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getIsDone() {
        return (isDone ? "X" : " ");
    }


    public boolean isDone() {
        return this.isDone;
    }

    public String getTask() {
        return this.tsk;
    }

    @Override
    public String toString() {
        return "[" + getIsDone() + "] " + tsk;
    }

    public String toSaveFormat() {
        return "T | " + (isDone ? 1 : 0) + " | " + tsk;
    }
}