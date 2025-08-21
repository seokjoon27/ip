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
                        System.out.println("     " + (i + 1) + ".[" + tasks[i].getIsDone() + "] " + tasks[i].getTask());
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
