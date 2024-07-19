package fr.skogrine.utilitycm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConcurrentMap is an enhanced version of ConcurrentHashMap that includes additional utilities for atomic operations and complex queries.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ConcurrentMap<String, Integer> map = new ConcurrentMap<>();
 * map.put("key1", 1);
 * map.put("key2", 2);
 * int value = map.computeIfAbsent("key3", k -> 3);
 * System.out.println("Value for key3: " + value);
 * }</pre>
 */
public class ConcurrentMap<K, V> {

    private final ConcurrentHashMap<K, V> map;

    /**
     * Constructs a ConcurrentMap.
     */
    public ConcurrentMap() {
        this.map = new ConcurrentHashMap<>();
    }

    /**
     * Puts a value into the map.
     *
     * @param key the key
     * @param value the value
     * @return the previous value associated with the key, or null if there was no mapping
     */
    public V put(K key, V value) {
        return map.put(key, value);
    }

    /**
     * Computes a value for the specified key if it is not already present.
     *
     * @param key the key
     * @param mappingFunction the function to compute the value
     * @return the value associated with the key
     */
    public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }

    /**
     * Atomically increments a value associated with the specified key by a given delta.
     *
     * @param key the key
     * @param delta the delta to add
     * @return the new value associated with the key after incrementing
     */
    public int incrementBy(K key, int delta) {
        return (int) map.compute(key, (k, v) -> {
            if (v == null) {
                return (V) Integer.valueOf(delta);
            }
            return (V) Integer.valueOf(((Integer) v) + delta);
        });
    }

    public static void main(String[] args) {
        ConcurrentMap<String, Integer> map = new ConcurrentMap<>();
        map.put("key1", 1);
        map.put("key2", 2);
        int value = map.computeIfAbsent("key3", k -> 3);
        System.out.println("Value for key3: " + value);
        map.incrementBy("key1", 5);
        System.out.println("Updated value for key1: " + map.map.get("key1"));
    }
}

