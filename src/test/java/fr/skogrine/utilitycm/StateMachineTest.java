package fr.skogrine.utilitycm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    private StateMachine<String, String> stateMachine;
    private StringBuilder log;

    @BeforeEach
    void setUp() {
        log = new StringBuilder();

        // Initialize the StateMachine with the initial state
        stateMachine = new StateMachine<>("START");

        // Define states and actions
        stateMachine.addState("START", state -> log.append("Entering START;"));
        stateMachine.addState("MIDDLE", state -> log.append("Entering MIDDLE;"));
        stateMachine.addState("END", state -> log.append("Entering END;"));

        // Define transitions
        stateMachine.addTransition("START", "MIDDLE", "toMiddle");
        stateMachine.addTransition("MIDDLE", "END", "toEnd");
        stateMachine.addTransition("END", "START", "reset");
    }

    @Test
    void testInitialState() {
        // Since we can't access the current state directly, we'll test state transitions
        // Start in "START" state and handle an event to verify state transitions
        stateMachine.handleEvent("toMiddle");
        assertTrue(log.toString().contains("Entering MIDDLE;"));
    }

    @Test
    void testStateTransition() {
        // Reset log for fresh test
        log.setLength(0);

        // Perform transitions and verify actions
        stateMachine.handleEvent("toMiddle");
        assertTrue(log.toString().contains("Entering MIDDLE;"));

        // Clear log and perform next transition
        log.setLength(0);
        stateMachine.handleEvent("toEnd");
        assertTrue(log.toString().contains("Entering END;"));

        // Clear log and perform final transition
        log.setLength(0);
        stateMachine.handleEvent("reset");
        assertTrue(log.toString().contains("Entering START;"));
    }

    @Test
    void testInvalidTransition() {
        // Clear log before handling invalid event
        log.setLength(0);

        // Handle an event that has no valid transition
        stateMachine.handleEvent("invalidEvent");

        // Ensure no state changes (no additional log entries should be present)
        assertEquals("", log.toString());
    }
}
