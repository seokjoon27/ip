import java.util.Scanner;


public class ESTJ {
    public static void main(String[] args) {
        String bar = "  ____________________________________________________________";
        System.out.println(bar);
        System.out.println("     Hello! I'm ESTJ");
        System.out.println("     What can I do for you?");
        System.out.println(bar);

        String[] tasks = new String[100];
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
                for (int i = 0; i < count; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println(bar);
            } else if (!userStr.isEmpty()) {
                tasks[count] = userStr;
                count++;
                System.out.println(bar);
                System.out.println("     added: " +userStr);
                System.out.println(bar);
            }
        }
        scanner.close();
    }
}
