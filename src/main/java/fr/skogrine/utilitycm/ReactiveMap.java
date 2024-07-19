package fr.skogrine.utilitycm;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * A reactive map that allows asynchronous operations and provides notifications
 * for changes in the map entries.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class ReactiveMap<K, V> {

    private final ConcurrentMap<K, V> map = new ConcurrentHashMap<>();
    private final List<Consumer<Change<K, V>>> listeners = new ArrayList<>();

    /**
     * Represents a change in the map.
     *
     * @param <K> the type of keys
     * @param <V> the type of values
     */
    public static class Change<K, V> {
        private final K key;
        private final V value;
        private final Operation operation;

        public enum Operation {
            PUT, REMOVE
        }

        public Change(K key, V value, Operation operation) {
            this.key = key;
            this.value = value;
            this.operation = operation;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Operation getOperation() {
            return operation;
        }
    }

    /**
     * Adds a listener that will be notified of changes to the map.
     *
     * @param listener the listener to add
     */
    public void addListener(Consumer<Change<K, V>> listener) {
        listeners.add(listener);
    }

    /**
     * Removes a listener from receiving notifications.
     *
     * @param listener the listener to remove
     */
    public void removeListener(Consumer<Change<K, V>> listener) {
        listeners.remove(listener);
    }

    /**
     * Asynchronously puts a value in the map.
     *
     * @param key the key
     * @param value the value
     * @return a CompletableFuture that completes when the operation is finished
     */
    public CompletableFuture<Void> putAsync(K key, V value) {
        return CompletableFuture.runAsync(() -> {
            map.put(key, value);
            notifyListeners(new Change<>(key, value, Change.Operation.PUT));
        });
    }

    /**
     * Asynchronously removes a value from the map.
     *
     * @param key the key
     * @return a CompletableFuture that completes when the operation is finished
     */
    public CompletableFuture<Void> removeAsync(K key) {
        return CompletableFuture.runAsync(() -> {
            V value = map.remove(key);
            notifyListeners(new Change<>(key, value, Change.Operation.REMOVE));
        });
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the key
     */
    public V get(K key) {
        return map.get(key);
    }

    /**
     * Notifies all listeners of a change.
     *
     * @param change the change
     */
    private void notifyListeners(Change<K, V> change) {
        for (Consumer<Change<K, V>> listener : listeners) {
            listener.accept(change);
        }
    }


}
