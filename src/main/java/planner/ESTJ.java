package planner;

public class ESTJ {
    private static final String bar = "  ____________________________________________________________";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

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

    public static void main(String[] args) {
        new ESTJ().run();
    }
}