package planner;

/**
 * Represents an executable user command.
 * Subclasses implement the behavior in {@link #execute(TaskList, Ui, Storage)}.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks task list to operate on
     * @param ui user interface to show output
     * @param storage storage to persist changes
     * @throws Exception if the command cannot be executed
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws Exception;

    /**
     * Returns whether this command should terminate the program loop.
     *
     * @return true if the program should exit
     */
    public boolean isExit() { return false; }

    /**
     * Parses a 1-based integer index from user input.
     *
     * @param arg argument string
     * @return parsed index
     * @throws Exception if parsing fails or index is negative
     */
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

/**
 * Command that ends the program.
 */
class ExitCommand extends Command {
    /** {@inheritDoc} */
    @Override public void execute(TaskList t, Ui ui, Storage s) { ui.showBye(); }

    /** {@inheritDoc} */
    @Override public boolean isExit() { return true; }
}

/**
 * Command that lists all current tasks.
 */
class ListCommand extends Command {
    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) {
        if (t.size() == 0) { ui.show("No tasks yet."); return; }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t.size(); i++) {
            sb.append(i + 1).append(". ").append(t.get(i)).append('\n');
        }
        ui.show(sb.toString().trim());
    }
}

/**
 * Command returned when user input does not match a known command.
 */
class UnknownCommand extends Command {
    private final String raw;

    /**
     * Creates an unknown command wrapper.
     *
     * @param r the raw user input
     */
    UnknownCommand(String r) { this.raw = r; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) {
        ui.show("Unknown command: " + raw);
    }
}

/**
 * Command that adds a generic task.
 */
class AddCommand extends Command {
    private final String desc;

    /**
     * @param d description of the task
     */
    AddCommand(String d) { this.desc = d; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        if (desc == null || desc.isBlank()) throw new Exception("Description cannot be empty.");
        Task task = new Task(desc);
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

/**
 * Command that adds a {@link Todo}.
 */
class AddTodoCommand extends Command {
    private final String desc;

    /**
     * @param d description of the todo
     */
    AddTodoCommand(String d) { this.desc = d; }

    /** {@inheritDoc} */
    @Override public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        if (desc == null || desc.isBlank()) throw new Exception("The description of a todo cannot be empty.");
        Task task = new Todo(desc);
        t.add(task);
        s.save(t.asList());
        ui.show("added: " + task);
    }
}

/**
 * Command that adds a {@link Deadline}.
 */
class AddDeadlineCommand extends Command {
    private final String args;

    /**
     * @param a raw argument string
     */
    AddDeadlineCommand(String a) { this.args = a; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
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

/**
 * Command that adds an {@link Event}.
 */
class AddEventCommand extends Command {
    private final String args;

    /**
     * @param a raw argument string
     */
    AddEventCommand(String a) { this.args = a; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
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

/**
 * Command that marks a task as completed.
 */
class MarkCommand extends Command {
    private final String arg;
    /**
     * @param a 1-based index argument
     */
    MarkCommand(String a) { this.arg = a; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task task = t.get(idx);
        task.markDone();
        s.save(t.asList());
        ui.show("Nice! I've marked this task as done:\n" + task);
    }
}

/**
 * Command that unmarks a task as completed.
 */
class UnmarkCommand extends Command {
    private final String arg;

    /**
     * @param a 1-based index argument
     */
    UnmarkCommand(String a) { this.arg = a; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task task = t.get(idx);
        task.markNotDone();
        s.save(t.asList());
        ui.show("OK, I've marked this task as not done yet:\n" + task);
    }
}

/**
 * Command that deletes a task by index.
 */
class DeleteCommand extends Command {
    private final String arg;

    /**
     * @param a 1-based index argument
     */
    DeleteCommand(String a) { this.arg = a; }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList t, Ui ui, Storage s) throws Exception {
        int idx = parseIndex1Based(arg) - 1;
        Task removed = t.remove(idx);
        s.save(t.asList());
        ui.show("Noted. I've removed this task:\n" + removed
                + "\nNow you have " + t.size() + " tasks in the list.");
    }
}