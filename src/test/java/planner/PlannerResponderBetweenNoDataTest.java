package planner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Integration-leaning check that querying an empty period reports no deadlines.
 */
public class PlannerResponderBetweenNoDataTest {

    /**
     * When there are no deadlines in the period, the responder should say so.
     */
    @Test
    public void between_noData_reportsNoDeadlines() {
        Responder r = new PlannerResponder();
        String reply = r.getResponse("list between 2099-01-01 2099-01-02");
        assertTrue(reply.toLowerCase().contains("no deadlines"),
                "Reply should indicate no deadlines in the period, but was: " + reply);
    }
}
