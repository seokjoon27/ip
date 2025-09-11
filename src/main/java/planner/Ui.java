package planner;

import java.util.Scanner;

/**
 * Handles user interactions for the CLI
 * Reads input and prints messages.
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);

    /** Prints the welcome banner when program is started. */
    public void showWelcome() {
        System.out.println("  ____________________________________________________________");
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println("  ____________________________________________________________");
    }

    /**
     * Reads the line of user input.
     *
     * @return the line entered by the user
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /** Prints a separator line. */
    public void showLine() {
        System.out.println("  ____________________________________________________________");
    }

    /** Prints the user exit message. */
    public void showBye() {
        System.out.println("  ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("  ____________________________________________________________");
    }

    /**
     * Prints the corresponding messages.
     *
     * @param s message to print
     */
    public void show(String s) { System.out.println(s); }

    /**
     * Prints an error message.
     *
     * @param msg the error messages to show
     */
    public void showError(String msg) { System.out.println(msg); }
}