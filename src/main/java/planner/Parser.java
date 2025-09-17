package planner;

import java.util.Locale;

/**
 * Parses user input into executable {@link Command} objects.
 */
public class Parser {
    private static final int SPLIT_LIMIT = 2;

    private static String[] splitOnce(String s) {
        return s.trim().split("\\s+", SPLIT_LIMIT);
    }

    /**
     * Parses the given line into a {@link Command}.
     *
     * @param fullCmd raw line entered by the user
     * @return a {@link Command} instance
     */
    public static Command parse(String fullCmd) {
        assert fullCmd != null : "fullCmd must not be null";

        String[] parts = splitOnce(fullCmd);
        String cmd = parts[0].toLowerCase(Locale.ROOT);
        String args = (parts.length > 1) ? parts[1] : "";

        /*
         * AI-assisted note (A-AiAssisted):
         * Asked an AI which B/C/D extension best fits this codebase and decided on
         * B-DoWithinPeriodTasks. Implementation details and tests are by the author.
         * See AI.md for a brief log.
         */
        switch (cmd) {
            case "bye":
                return new ExitCommand();
            case "list":
                if (args != null && args.toLowerCase(java.util.Locale.ROOT).startsWith("between ")) {
                    String rest = args.substring("between".length()).trim(); // "YYYY-MM-DD YYYY-MM-DD"
                    return new ListBetweenCommand(rest);
                }
                return new ListCommand();
            case "todo":
                return new AddTodoCommand(args);
            case "deadline":
                return new AddDeadlineCommand(args);
            case "event":
                return new AddEventCommand(args);
            case "mark":
                return new MarkCommand(args);
            case "unmark":
                return new UnmarkCommand(args);
            case "delete":
                return new DeleteCommand(args);
            case "add":
                return new AddCommand(args);
            default:
                return new UnknownCommand(fullCmd);
        }
    }
}