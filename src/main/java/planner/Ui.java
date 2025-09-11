package planner;

import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("  ____________________________________________________________");
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println("  ____________________________________________________________");
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void showLine() {
        System.out.println("  ____________________________________________________________");
    }

    public void showBye() {
        System.out.println("  ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("  ____________________________________________________________");
    }

    public void show(String s) { System.out.println(s); }
    public void showError(String msg) { System.out.println(msg); }
}