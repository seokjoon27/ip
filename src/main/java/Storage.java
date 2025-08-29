import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Storage {
    private final Path file;

    Storage(Path file) {
        this.file = file;
    }

    int load(Task[] tasks) throws IOException {
        if (!Files.exists(file)) {
            return 0;
        }
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        int count = 0;
        for (String raw : lines) {
            if (raw == null) continue;
            String line = raw.trim();
            if (line.isEmpty()) continue;
            try {
                Task t = deserialize(line);
                if (t != null && count < tasks.length) {
                    tasks[count++] = t;
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("[WARN] Skipping corrupted line: " + line);
            }
        }
        return count;
    }

    void save(Task[] tasks, int count) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        List<String> lines = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            lines.add(serialize(tasks[i]));
        }
        Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static String serialize(Task t) {
        String done = t.IsDone() ? "1" : "0";
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
        String[] p = line.split("\\s*\\|\\s*");
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