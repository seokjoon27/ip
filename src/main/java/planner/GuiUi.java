package planner;

public class GuiUi extends Ui {
    private final StringBuilder sb = new StringBuilder();

    @Override public void showWelcome() {}
    @Override public void showLine()    {}

    @Override public void showBye() {
        sb.append("Bye. Hope to see you again soon!\n");
    }

    @Override public void show(String s) {
        sb.append(s).append('\n');
    }

    @Override public void showError(String msg) {
        sb.append(msg).append('\n');
    }

    public String drain() {
        return sb.toString();
    }
}