import java.util.Scanner;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ESTJ {
    private static final String bar = "  ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(bar);
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println(bar);

        ESTJCore core = new ESTJCore();   // reuse the same logic as the GUI
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String userStr = scanner.nextLine().trim();
            try {
                String reply = core.getResponse(userStr);
                System.out.println(bar);
                System.out.println("     " + reply.replace("\n", "\n     "));
                System.out.println(bar);
                if ("bye".equals(userStr)) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(bar);
                System.out.println("     " + e.getMessage());
                System.out.println(bar);
            }
        }
        scanner.close();
    }

    private static Integer getIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

class Task {
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


    public boolean IsDone() {
        return this.isDone;
    }

    public String getTask() {
        return this.tsk;
    }

    @Override
    public String toString() {
        return "[" + getIsDone() + "] " + tsk;
    }
}

class Todo extends Task {
    public Todo(String tsk) {
        super(tsk);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
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
}

class Event extends Task {
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
}

class UserStrException extends Exception {
    public UserStrException(String message) {
        super(message);
    }
}