import java.util.Scanner;


public class ESTJ {
    private static final String bar = "  ____________________________________________________________";

    public static void main(String[] args) {
        System.out.println(bar);
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println(bar);

        Task[] tasks = new Task[100];
        int count = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userStr = scanner.nextLine().trim();

            if (userStr.equals("bye")) {
                System.out.println(bar);
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(bar);
                break;
            } else if (userStr.equals(("list"))) {
                System.out.println(bar);
                if (count == 0) {
                    System.out.println("     Here are the tasks in your list:");
                } else {
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < count; i++) {
                        System.out.println("     " + (i + 1) + "." + tasks[i]);
                    }
                }
                System.out.println(bar);
            } else if (userStr.startsWith("mark ")) {
                Integer idx = getIndex(userStr.substring(5));
                if (idx == null || idx < 1 || idx > count) {
                    break;
                } else {
                    Task t = tasks[idx - 1];
                    t.markDone();
                    System.out.println(bar);
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + t);
                    System.out.println(bar);
                }
            } else if (userStr.startsWith("unmark ")) {
                Integer idx = getIndex(userStr.substring(7));
                if (idx == null || idx < 1 || idx > count) {
                    break;
                } else {
                    Task t = tasks[idx - 1];
                    t.markNotDone();
                    System.out.println(bar);
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + t);
                    System.out.println(bar);
                }
            } else if (userStr.startsWith("todo ")) {
                String desc = userStr.substring(5).trim();
                tasks[count] = new Todo(desc);
                count++;
                System.out.println(bar);
                System.out.println("     Got it. I've added this task:");
                System.out.println("       " + tasks[count - 1]);
                System.out.println("     Now you have " + count + " tasks in the list.");
                System.out.println(bar);
            } else if (userStr.startsWith("deadline ")) {
                String[] parts = userStr.substring(9).split("/by", 2);
                if (parts.length < 2) {
                    break;
                } else {
                    String desc = parts[0].trim();
                    String by = parts[1].trim();
                    tasks[count] = new Deadline(desc, by);
                    count++;
                    System.out.println(bar);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + tasks[count - 1]);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                }
            } else if (userStr.startsWith("event ")) {
                String[] parts = userStr.substring(6).split("/from", 2);
                if (parts.length < 2 || !parts[1].contains("/to")) {
                    break;
                } else {
                    String desc = parts[0].trim();
                    String[] timeParts = parts[1].split("/to", 2);
                    String from = timeParts[0].trim();
                    String to = timeParts[1].trim();
                    tasks[count] = new Event(desc, from, to);
                    count++;
                    System.out.println(bar);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + tasks[count - 1]);
                    System.out.println("     Now you have " + count + " tasks in the list.");
                    System.out.println(bar);
                }
            } else if (!userStr.isEmpty()) {
                tasks[count] = new Task(userStr);
                count++;
                System.out.println(bar);
                System.out.println("     added: " +userStr);
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

    public String getTask() {
        return tsk;
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
    private final String by;

    public Deadline(String tsk, String by) {
        super(tsk);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
