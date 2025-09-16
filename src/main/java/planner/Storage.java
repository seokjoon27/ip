package planner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Handles reading and writing tasks to a UTF-8 text file using a simple pipe-separated format.
 */
public class Storage {
    private final Path file;
    private static final String SEP = " | ";
    private static final Pattern SEP_SPLIT = Pattern.compile("\\s*\\|\\s*");

    /**
     * Creates a storage that reads and writes at the given path.
     *
     * @param filePath path to the save file
     */
    public Storage(String filePath) {
        this(Paths.get(filePath));
    }

    public Storage(Path file) {
        assert file != null : "storage path must not be null";
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
        assert tasks != null : "tasks list must not be null";
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        List<String> lines = new ArrayList<>(tasks.size());
        for (Task t : tasks) {
            assert t != null : "tasks element must not be null";
            lines.add(serialize(t));
        }
        Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String serialize(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(SEP, "T", done, ((Todo) t).getTask());
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(SEP, "D", done, d.getTask(), d.getByIso());
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(SEP, "E", done, e.getTask(), e.getFrom(), e.getTo());
        }
        throw new IllegalArgumentException("Unknown task type: " + t.getClass());
    }

    private static Task deserialize(String line) {
        String[] p = SEP_SPLIT.split(line);
        if (p.length < 3) throw new IllegalArgumentException("Too few fields: " + line);
        String type = p[0];
        boolean done = "1".equals(p[1]);
        String desc = p[2];

        Task t = switch (type) {
            case "T" -> new Todo(desc);
            case "D" -> {
                if (p.length < 4) throw new IllegalArgumentException("Deadline missing /by");
                LocalDate by = LocalDate.parse(p[3]);
                yield new Deadline(desc, by);
            }
            case "E" -> {
                if (p.length < 5) throw new IllegalArgumentException("Event missing /from or /to");
                yield new Event(desc, p[3], p[4]);
            }
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
        if (done) t.markDone();
        return t;
    }
}