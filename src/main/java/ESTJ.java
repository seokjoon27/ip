import java.util.Scanner;


public class ESTJ {
    public static void main(String[] args) {
        String bar = "  ____________________________________________________________";
        System.out.println(bar);
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println(bar);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userStr = scanner.nextLine().trim();

            if (userStr.equals("bye")) {
                System.out.println(bar);
                System.out.println("     Bye. Hope to see you again soon!");
                System.out.println(bar);
                break;
            }

            System.out.println(bar);
            System.out.println("     " + userStr);
            System.out.println(bar);
        }
        scanner.close();
    }
}
