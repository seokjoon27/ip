package planner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import org.junit.jupiter.api.Test;

/**
 * Tests invalid date input for {@code deadline} via the Responder path.
 */
public class DeadlineParseTest {

    /**
     * Invalid ISO date should not be accepted; responder should return a Usage/Error.
     */
    @Test
    public void invalidDate_viaResponder_reportsUsageOrError() {
        Responder r = new PlannerResponder();
        String reply = r.getResponse("deadline submit report /by 2025-13-40");
        String low = reply == null ? "" : reply.toLowerCase(Locale.ROOT);
        assertTrue(
                low.startsWith("usage:") || low.startsWith("error:") || low.contains("invalid"),
                "Expected Usage/Error for invalid date, but got: " + reply
        );
    }
}