import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ESTJCore {
    private final Task[] tasks = new Task[100];
    private int count = 0;
    private final Storage storage;

    public ESTJCore() {
        storage = new Storage(Paths.get("data", "log.txt"));
        try {
            count = storage.load(tasks);
        } catch (Exception e) {
        }
    }

    /** Process one line of user input and produce a reply string (no printing). */
    public String getResponse(String userStr) throws Exception {
        if (userStr == null) userStr = "";
        userStr = userStr.trim();

        if (userStr.equals("bye")) {
            return "Bye. Hope to see you again soon!";
        }

        if (userStr.equals("list")) {
            if (count == 0) return "Your list is empty.";
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < count; i++) {
                sb.append(i + 1).append(". ").append(tasks[i]).append('\n');
            }
            return sb.toString().strip();
        }

        if (userStr.startsWith("mark ")) {
            Integer idx = getIndex(userStr.substring(5));
            if (idx == null) throw new UserStrException("Invalid task number to mark.");
            if (idx < 1 || idx > count) throw new UserStrException("Task number " + idx + " doesn't exist.");

            Task t = tasks[idx - 1];
            t.markDone();
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "Nice! I've marked this task as done:\n  " + t;
        }

        if (userStr.startsWith("unmark ")) {
            Integer idx = getIndex(userStr.substring(7));
            if (idx == null) throw new UserStrException("Invalid task number to unmark.");
            if (idx < 1 || idx > count) throw new UserStrException("Task number " + idx + " doesn't exist.");

            Task t = tasks[idx - 1];
            t.markNotDone();
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "OK, I've marked this task as not done yet:\n  " + t;
        }

        if (userStr.startsWith("delete ")) {
            Integer idx = getIndex(userStr.substring(7));
            if (idx == null) throw new UserStrException("Invalid task number to delete.");
            if (idx < 1 || idx > count) throw new UserStrException("Task number " + idx + " doesn't exist.");

            Task removed = tasks[idx - 1];
            for (int i = idx; i < count; i++) {
                tasks[i - 1] = tasks[i];
            }
            tasks[count - 1] = null;
            count--;
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "Noted. I've removed this task:\n  " + removed + "\nNow you have " + count + " tasks in the list.";
        }

        if (userStr.startsWith("todo")) {
            String desc = userStr.substring(4).trim();
            if (desc.isEmpty()) throw new UserStrException("No description for todo task provided.");
            if (count >= 100) throw new UserStrException("Sorry, your task list is full (max 100).");

            tasks[count++] = new Todo(desc);
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "Got it. I've added this task:\n  " + tasks[count - 1] + "\nNow you have " + count + " tasks in the list.";
        }

        if (userStr.startsWith("deadline")) {
            String body = userStr.substring(8).trim();
            String[] parts = body.split("/by", 2);
            if (parts.length < 2) throw new UserStrException("Incorrect format. Use: deadline <description> /by <when>.");
            String desc = parts[0].trim();
            String by = parts[1].trim();
            if (desc.isEmpty()) throw new UserStrException("The deadline description cannot be empty.");
            if (by.isEmpty()) throw new UserStrException("Please specify the deadline.");
            if (count >= 100) throw new UserStrException("Sorry, your task list is full (max 100).");

            tasks[count++] = new Deadline(desc, by);
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "Got it. I've added this task:\n  " + tasks[count - 1] + "\nNow you have " + count + " tasks in the list.";
        }

        if (userStr.startsWith("event")) {
            String body = userStr.substring(5).trim();
            String[] parts = body.split("/from", 2);
            if (parts.length < 2 || !parts[1].contains("/to"))
                throw new UserStrException("Incorrect format. Use: event <description> /from <start> /to <end>");
            String desc = parts[0].trim();
            String[] timeParts = parts[1].split("/to", 2);
            String from = timeParts[0].trim();
            String to = timeParts[1].trim();
            if (desc.isEmpty()) throw new UserStrException("The event description cannot be empty.");
            if (from.isEmpty() || to.isEmpty()) throw new UserStrException("Please specify both start and end times.");
            if (count >= 100) throw new UserStrException("Sorry, your task list is full (max 100).");

            tasks[count++] = new Event(desc, from, to);
            try { storage.save(tasks, count); } catch (Exception ignore) {}
            return "Got it. I've added this task:\n  " + tasks[count - 1] + "\nNow you have " + count + " tasks in the list.";
        }

        throw new UserStrException("Invalid input. Try: todo / deadline / event / list / mark / unmark / delete / bye");
    }

    private static Integer getIndex(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String formatDate(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}