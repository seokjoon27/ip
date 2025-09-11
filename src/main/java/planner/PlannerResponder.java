package planner;

public class PlannerResponder implements Responder {
    private final Storage storage;
    private final TaskList tasks;

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