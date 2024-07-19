package fr.skogrine.utilitycm;

import java.util.HashMap;
import java.util.Map;

/**
 * A type-safe map that ensures values are of the expected type.
 *
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of values in the map
 */
public class TypeSafeMap<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final Class<V> valueType;

    /**
     * Constructs a TypeSafeMap with the specified value type.
     *
     * @param valueType The expected type of values in the map.
     */
    public TypeSafeMap(Class<V> valueType) {
        this.valueType = valueType;
    }

    /**
     * Puts a key-value pair into the map, ensuring the value is of the correct type.
     *
     * @param key The key.
     * @param value The value.
     * @throws IllegalArgumentException If the value is not of the expected type.
     */
    public void put(K key, V value) {
        if (!valueType.isInstance(value)) {
            throw new IllegalArgumentException("Value must be of type " + valueType.getName());
        }
        map.put(key, value);
    }

    /**
     * Retrieves a value by key.
     *
     * @param key The key.
     * @return The value associated with the key.
     */
    public V get(K key) {
        return map.get(key);
    }

    /**
     * Retrieves a value by key and casts it to the expected type.
     *
     * @param key The key.
     * @param type The expected type.
     * @return The value associated with the key.
     * @throws ClassCastException If the value is not of the expected type.
     */
    public <T> T get(K key, Class<T> type) {
        Object value = map.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        } else {
            throw new ClassCastException("Value is not of type " + type.getName());
        }
    }

    /**
     * Removes a key-value pair from the map.
     *
     * @param key The key.
     * @return The removed value.
     */
    public V remove(K key) {
        return map.remove(key);
    }

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return The number of key-value pairs.
     */
    public int size() {
        return map.size();
    }

}
