package planner;

/**
 * Entry point for the text-based interface of the app.
 * Wires {@link Ui}, {@link Parser}, {@link Command}, {@link TaskList} and {@link Storage}.
 * Runs the loop until the user exits.
 */
public class ESTJ {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a CLI instance that persists tasks to the given filepath.
     */
    public ESTJ() {
        this.ui = new Ui();
        this.storage = new Storage("data/tasks.txt");
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            ui.show("Loading error -> start with empty list");
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /** Starts the loop: Loop reads, parses, then executes until the exit command. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String full = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(full);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (Exception e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /** Launches CLI. */
    public static void main(String[] args) {
        new ESTJ().run();
    }
}