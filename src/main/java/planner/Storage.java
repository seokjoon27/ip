package planner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * Handles reading and writing tasks to a UTF-8 text file using a simple pipe-separated format.
 */
public class Storage {
    private final Path file;

    /**
     * Creates a storage that reads and writes at the given path.
     *
     * @param filePath path to the save file
     */
    public Storage(String filePath) {
        this(Paths.get(filePath));
    }

    public Storage(Path file) {
        this.file = file;
    }

    /**
     * Loads tasks from disk.
     *
     * @return tasks read from the file
     * @throws java.io.IOException if reading fails
     */
    public List<Task> load() throws IOException {
        List<Task> list = new ArrayList<>();
        if (!Files.exists(file)) return list;

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        for (String raw : lines) {
            if (raw == null) continue;
            String line = raw.trim();
            if (line.isEmpty()) continue;
            try {
                Task t = deserialize(line);
                if (t != null) list.add(t);
            } catch (IllegalArgumentException ex) {
                System.err.println("[WARN] Skipping corrupted line: " + line);
            }
        }
        return list;
    }

    /**
     * Saves the given tasks to disk, overwriting existing content.
     *
     * @param tasks tasks to save
     * @throws java.io.IOException if writing fails
     */
    public void save(List<Task> tasks) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        List<String> lines = new ArrayList<>(tasks.size());
        for (Task t : tasks) {
            lines.add(serialize(t));
        }
        Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String serialize(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, ((Todo) t).getTask());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, d.getTask(), d.getByIso());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done, e.getTask(), e.getFrom(), e.getTo());
        }
        throw new IllegalArgumentException("Unknown task type: " + t.getClass());
    }

    private static Task deserialize(String line) {
        String[] PIPELINE = line.split("\\s*\\|\\s*");
        if (PIPELINE.length < 3) throw new IllegalArgumentException("Too few fields: " + line);
        String type = PIPELINE[0];
        boolean done = "1".equals(PIPELINE[1]);
        String desc = PIPELINE[2];

        Task t = switch (type) {
            case "T" -> new Todo(desc);
            case "D" -> {
                if (PIPELINE.length < 4) throw new IllegalArgumentException("Deadline missing /by");
                LocalDate by = LocalDate.parse(PIPELINE[3]);
                yield new Deadline(desc, by);
            }
            case "E" -> {
                if (PIPELINE.length < 5) throw new IllegalArgumentException("Event missing /from or /to");
                yield new Event(desc, PIPELINE[3], PIPELINE[4]);
            }
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
        if (done) t.markDone();
        return t;
    }
}