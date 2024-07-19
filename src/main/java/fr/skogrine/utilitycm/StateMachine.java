package fr.skogrine.utilitycm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * StateMachine is a generic state machine implementation that can be used to model workflows, game states, UI navigation, and more.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * StateMachine<String, String> stateMachine = new StateMachine<>("START");
 * stateMachine.addState("START", state -> System.out.println("Starting..."))
 *             .addTransition("START", "RUNNING", "run")
 *             .addState("RUNNING", state -> System.out.println("Running..."))
 *             .addTransition("RUNNING", "FINISHED", "finish")
 *             .addState("FINISHED", state -> System.out.println("Finished."));
 *
 * stateMachine.handleEvent("run");
 * stateMachine.handleEvent("finish");
 * }</pre>
 */
public class StateMachine<S, E> {

    private final Map<S, Consumer<S>> states = new HashMap<>();
    private final Map<S, Map<E, S>> transitions = new HashMap<>();
    private S currentState;

    /**
     * Constructs a StateMachine with the initial state.
     *
     * @param initialState the initial state
     */
    public StateMachine(S initialState) {
        this.currentState = initialState;
    }

    /**
     * Adds a state with an associated action to the state machine.
     *
     * @param state the state
     * @param action the action to be performed when entering the state
     * @return this StateMachine
     */
    public StateMachine<S, E> addState(S state, Consumer<S> action) {
        states.put(state, action);
        transitions.put(state, new HashMap<>());
        return this;
    }

    /**
     * Adds a transition between states triggered by an event.
     *
     * @param from the source state
     * @param to the target state
     * @param event the event that triggers the transition
     * @return this StateMachine
     */
    public StateMachine<S, E> addTransition(S from, S to, E event) {
        transitions.get(from).put(event, to);
        return this;
    }

    /**
     * Handles an event, causing a state transition if a transition exists for the current state and event.
     *
     * @param event the event to handle
     */
    public void handleEvent(E event) {
        S nextState = transitions.get(currentState).get(event);
        if (nextState != null) {
            currentState = nextState;
            states.get(currentState).accept(currentState);
        }
    }
}