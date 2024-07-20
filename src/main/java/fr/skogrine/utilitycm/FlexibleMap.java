package fr.skogrine.utilitycm;

import java.util.HashMap;

/**
 * FlexibleMap is an extension of HashMap that provides additional utilities
 * for managing entries more efficiently and flexibly.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * <pre>
 * {@code
 * // Example usage:
 * FlexibleMap<String, Integer> map = new FlexibleMap<>();
 * map.putIfAbsent("one", 1);
 * map.putIfAbsent("two", 2);
 * map.merge("two", 3, Integer::sum);
 * System.out.println(map.get("two")); // Output: 5
 * }
 * </pre>
 */
public class FlexibleMap<K, V> extends HashMap<K, V> {

    /**
     * Adds a key-value pair to the map if the key is not already present.
     *
     * @param key the key
     * @param value the value
     * @return the previous value associated with the key, or null if there was no mapping for the key
     */
    public V putIfAbsent(K key, V value) {
        return super.putIfAbsent(key, value);
    }

    /**
     * Merges the specified value with the existing value associated with the key
     * using the given remapping function.
     *
     * @param key the key
     * @param value the value to merge
     * @param remappingFunction the remapping function to recompute a value if present
     * @return the new value associated with the specified key, or null if none
     */
    public V merge(K key, V value, java.util.function.BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return super.merge(key, value, remappingFunction);
    }

    /**
     * Replaces the entry for the specified key only if currently mapped to the specified value.
     *
     * @param key the key with which the specified value is associated
     * @param oldValue the value expected to be associated with the specified key
     * @param newValue the value to be associated with the specified key
     * @return true if the value was replaced
     */
    public boolean replace(K key, V oldValue, V newValue) {
        return super.replace(key, oldValue, newValue);
    }

    /**
     * Replaces the entry for the specified key only if it is currently mapped to some value.
     *
     * @param key the key with which the specified value is associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key
     */
    public V replace(K key, V value) {
        return super.replace(key, value);
    }

    /**
     * Computes a new value for the specified key using the given remapping function.
     *
     * @param key the key
     * @param remappingFunction the remapping function to compute a new value
     * @return the new value associated with the specified key, or null if none
     */
    public V compute(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return super.compute(key, remappingFunction);
    }

    /**
     * Computes a new value for the specified key if it is not already present using the given mapping function.
     *
     * @param key the key
     * @param mappingFunction the mapping function to compute a value
     * @return the current (existing or computed) value associated with the specified key, or null if none
     */
    public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
        return super.computeIfAbsent(key, mappingFunction);
    }

    /**
     * Computes a new value for the specified key if it is currently mapped to some value using the given remapping function.
     *
     * @param key the key
     * @param remappingFunction the remapping function to compute a new value
     * @return the new value associated with the specified key, or null if none
     */
    public V computeIfPresent(K key, java.util.function.BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return super.computeIfPresent(key, remappingFunction);
    }
}
