package planner;

public class Parser {
    public static Command parse(String fullCmd) {
        String[] parts = fullCmd.trim().split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String args = (parts.length > 1) ? parts[1] : "";

        switch (cmd) {
            case "bye":      return new ExitCommand();
            case "list":     return new ListCommand();

            case "todo":     return new AddTodoCommand(args);
            case "deadline": return new AddDeadlineCommand(args);
            case "event":    return new AddEventCommand(args);

            case "mark":     return new MarkCommand(args);
            case "unmark":   return new UnmarkCommand(args);
            case "delete":   return new DeleteCommand(args);

            case "add":      return new AddCommand(args);

            default:         return new UnknownCommand(fullCmd);
        }
    }
}