package fr.skogrine.utilitycm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * EventBus is a flexible and efficient event bus system that allows decoupled components to communicate with each other through events.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * EventBus eventBus = new EventBus();
 * eventBus.subscribe("event1", data -> System.out.println("Event 1 received: " + data));
 * eventBus.publish("event1", "Hello, world!");
 * }</pre>
 */
public class EventBus {

    private final Map<String, List<Consumer<Object>>> subscribers = new HashMap<>();

    /**
     * Subscribes to a specified event type.
     *
     * @param eventType the event type
     * @param listener the event listener
     */
    public void subscribe(String eventType, Consumer<Object> listener) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Publishes an event to all subscribers.
     *
     * @param eventType the event type
     * @param data the event data
     */
    public void publish(String eventType, Object data) {
        List<Consumer<Object>> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers != null) {
            for (Consumer<Object> subscriber : eventSubscribers) {
                subscriber.accept(data);
            }
        }
    }

}
