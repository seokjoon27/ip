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

        Task[] tasks = new Task[100];
        int count = 0;

        Storage storage = new Storage(Paths.get("data", "log.txt"));
        try {
            count = storage.load(tasks);
        } catch (Exception e) {
            System.err.println("[WARN] Unable to load saved tasks: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userStr = scanner.nextLine().trim();

            try {
                if (userStr.equals("bye")) {
                    System.out.println(bar);
                    System.out.println("     Bye. Hope to see you again soon!");
                    System.out.println(bar);
                    break;
                } else if (userStr.equals(("list"))) {
                    System.out.println(bar);
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < count; i++) {
                        System.out.println("     " + (i + 1) + "." + tasks[i]);
                    }
                    System.out.println(bar);
                } else if (userStr.startsWith("mark ")) {
                    Integer idx = getIndex(userStr.substring(5));
                    if (idx == null) {
                        throw new UserStrException("Invalid task number to mark.");
                    }
                    if (idx < 1 || idx > count) {
                        throw new UserStrException("Task number " + idx + " doesn't exist.");
                    }

                    Task t = tasks[idx - 1];
                    t.markDone();
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + t);
                    System.out.println(bar);
                } else if (userStr.startsWith("unmark ")) {
                    Integer idx = getIndex(userStr.substring(7));
                    if (idx == null) {
                        throw new UserStrException("Invalid task number to unmark.");
                    }
                    if (idx < 1 || idx > count) {
                        throw new UserStrException("Task number " + idx + " doesn't exist.");
                    }

                    Task t = tasks[idx - 1];
                    t.markNotDone();
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + t);
                    System.out.println(bar);
                } else if (userStr.startsWith("delete ")) {
                    Integer idx = getIndex(userStr.substring(7));
                    if (idx == null) {
                        throw new UserStrException("Invalid task number to delete.");
                    }
                    if (idx < 1 || idx > count) {
                        throw new UserStrException("Task number " + idx + " doesn't exist.");
                    }
                    Task removed = tasks[idx - 1];
                    for (int i = idx; i < count; i++) {
                        tasks[i - 1] = tasks[i];
                    }
                    tasks[count - 1] = null;
                    count--;
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     Noted. I've removed this task:");
                    System.out.println("       " + removed);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                } else if (userStr.startsWith("todo")) {
                    String desc = userStr.substring(4).trim();
                    if (desc.isEmpty()) {
                        throw new UserStrException("No description for todo task provided.");
                    }
                    if (count >= 100) {
                        throw new UserStrException("Sorry, your task list is full (max 100).");
                    }
                    tasks[count] = new Todo(desc);
                    count++;
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + tasks[count - 1]);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                } else if (userStr.startsWith("deadline")) {
                    String body = userStr.substring(8).trim();
                    String[] parts = body.split("/by", 2);
                    if (parts.length < 2) {
                        throw new UserStrException("Incorrect format. Use: deadline <description> /by <when>.");
                    }
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    if (desc.isEmpty()) {
                        throw new UserStrException("The deadline description cannot be empty.");
                    }
                    if (by.isEmpty()) {
                        throw new UserStrException("Please specify the deadline.");
                    }
                    if (count >= 100) {
                        throw new UserStrException("Sorry, your task list is full (max 100).");
                    }
                    tasks[count] = new Deadline(desc, by);
                    count++;
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + tasks[count - 1]);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                } else if (userStr.startsWith("event")) {
                    String body = userStr.substring(5).trim();
                    String[] parts = body.split("/from", 2);
                    if (parts.length < 2 || !parts[1].contains("/to")) {
                        throw new UserStrException("Incorrect format. Use: event <description> /from <start> /to <end>");
                    }
                    String desc = parts[0].trim();
                    String[] timeParts = parts[1].split("/to", 2);
                    String from = timeParts[0].trim();
                    String to = timeParts[1].trim();
                    if (desc.isEmpty()) {
                        throw new UserStrException("The event description cannot be empty.");
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new UserStrException("Please specify both start and end times.");
                    }
                    if (count >= 100) {
                        throw new UserStrException("Sorry, your task list is full (max 100).");
                    }
                    tasks[count] = new Event(desc, from, to);
                    count++;
                    try {
                        storage.save(tasks, count);
                    } catch (Exception ignore) {}

                    System.out.println(bar);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + tasks[count - 1]);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                } else if (userStr.startsWith("find ")) {
                    String keyword = userStr.substring(5).trim();
                    if (keyword.isEmpty()) {
                        throw new UserStrException("Please provide a keyword. Usage: find <keyword>");
                    }
                    String lower = keyword.toLowerCase();

                    System.out.println(bar);
                    System.out.println("     Here are the matching tasks in your list:");
                    int shown = 0;
                    for (int i = 0; i < count; i++) {
                        Task t = tasks[i];
                        if (t.getTask().toLowerCase().contains(lower)) {
                            shown++;
                            System.out.println("     " + shown + "." + t);
                        }
                    }
                    if (shown == 0) {
                        System.out.println("     (no matches)");
                    }
                    System.out.println(bar);
                } else {
                    throw new UserStrException("Invalid input. Try: todo / deadline / event / list / mark / unmark / delete / bye");
                }
            } catch (UserStrException e) {
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