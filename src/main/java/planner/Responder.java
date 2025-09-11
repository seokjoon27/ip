package planner;

/**
 * Abstraction for a component that can generate responses to user input.
 */
public interface Responder {
    /**
     * Returns a reply string for a given user input.
     *
     * @param input raw user input
     * @return reply text
     */
    String getResponse(String input);
}