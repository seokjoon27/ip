package planner;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;
    public boolean isExit() { return false; }

    static int parseIndex1Based(String arg) throws Exception {
        try {
            int idx = Integer.parseInt(arg.trim());
            if (idx <= 0) throw new NumberFormatException();
            return idx;
        } catch (NumberFormatException e) {
            throw new Exception("Please provide a valid positive index.");
        }
    }
}

class ExitCommand extends Command {
    @Override public void execute(TaskList t, Ui ui, Storage s) { ui.showBye(); }
    @Override public boolean isExit() { return true; }
}

class ListCommand extends Command {
    @Override public void execute(TaskList t, Ui ui, Storage s) {
        if (t.size() == 0) { ui.show("No tasks yet."); return; }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t.size(); i++) {
            sb.append(i + 1).append(". ").append(t.get(i)).append('\n');
        }
        ui.show(sb.toString().trim());
    }
}

class UnknownCommand extends Command {
    private final String raw;
    UnknownCommand(String r) { this.raw = r; }
    @Override public void execute(TaskList t, Ui ui, Storage s) { ui.show("Unknown command: " + raw); }
}

class AddCommand extends Command {
    private final String desc;
    AddCommand(String d) { this.desc = d; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        if (desc == null || desc.isBlank()) throw new Exception("Description cannot be empty.");
        Task task = new Task(desc);
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

class AddTodoCommand extends Command {
    private final String desc;
    AddTodoCommand(String d) { this.desc = d; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        if (desc == null || desc.isBlank()) throw new Exception("The description of a todo cannot be empty.");
        Task task = new Todo(desc);
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

class AddDeadlineCommand extends Command {
    private final String args;
    AddDeadlineCommand(String a) { this.args = a; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        String[] split = args.split("\\s+/by\\s+", 2);
        if (split.length < 2 || split[0].isBlank() || split[1].isBlank()) {
            throw new Exception("Usage: deadline <description> /by <yyyy-MM-dd>");
        }
        Task task = new Deadline(split[0].trim(), split[1].trim());
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

class AddEventCommand extends Command {
    private final String args;
    AddEventCommand(String a) { this.args = a; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        String[] pFrom = args.split("\\s+/from\\s+", 2);
        if (pFrom.length < 2 || pFrom[0].isBlank()) throw new Exception("Usage: event <description> /from <start> /to <end>");
        String[] pTo = pFrom[1].split("\\s+/to\\s+", 2);
        if (pTo.length < 2 || pTo[0].isBlank() || pTo[1].isBlank()) throw new Exception("Usage: event <description> /from <start> /to <end>");
        Task task = new Event(pFrom[0].trim(), pTo[0].trim(), pTo[1].trim());
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

class MarkCommand extends Command {
    private final String arg;
    MarkCommand(String a) { this.arg = a; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task task = t.get(idx);
        task.markDone();
        s.save(t.asList());
        ui.show("Nice! I've marked this task as done:\n" + task);
    }
}

class UnmarkCommand extends Command {
    private final String arg;
    UnmarkCommand(String a) { this.arg = a; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task task = t.get(idx);
        task.markNotDone();
        s.save(t.asList());
        ui.show("OK, I've marked this task as not done yet:\n" + task);
    }
}

class DeleteCommand extends Command {
    private final String arg;
    DeleteCommand(String a) { this.arg = a; }
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task removed = t.remove(idx);
        s.save(t.asList());
        ui.show("Noted. I've removed this task:\n" + removed + "\nNow you have " + t.size() + " tasks in the list.");
    }
}