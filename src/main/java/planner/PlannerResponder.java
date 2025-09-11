package planner;

/**
 * Bridges the GUI with the ESTJ core logic.
 */
public class PlannerResponder implements Responder {
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a responder backed by a task list persisted in {@code data/tasks.txt}.
     */
    public PlannerResponder() {
        this.storage = new Storage("data/tasks.txt");
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /**
     * Parses and executes a command, returning the resulting output text.
     *
     * @param input raw user input
     * @return reply string
     */
    @Override
    public synchronized String getResponse(String input) {
        GuiUi ui = new GuiUi();
        try {
            Command c = Parser.parse(input == null ? "" : input);
            c.execute(tasks, ui, storage);
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
        return ui.drain().strip();
    }
}