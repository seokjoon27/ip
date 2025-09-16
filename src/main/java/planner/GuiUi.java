package planner;

/**
 * A variant of {@link Ui} used for the GUI.
 */
public class GuiUi extends Ui {
    private final StringBuilder sb = new StringBuilder();

    @Override public void showWelcome() {

    }

    @Override public void showLine()    {

    }

    @Override public void showBye() {
        sb.append("Bye. Hope to see you again soon!\n");
    }

    @Override public void show(String s) {
        sb.append(s).append('\n');
    }

    @Override public void showError(String msg) {
        sb.append(msg).append('\n');
    }

    /**
     * Returns the accumulated messages and leaves the buffer intact.
     *
     * @return concatenated output
     */
    public String drain() {
        return sb.toString();
    }
}