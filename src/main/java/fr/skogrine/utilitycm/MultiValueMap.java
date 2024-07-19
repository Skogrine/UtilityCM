package fr.skogrine.utilitycm;

import java.util.*;

/**
 * MultiValueMap is a map that allows multiple values for a single key.
 * It provides easy and efficient ways to manipulate the values (add, remove, retrieve).
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class MultiValueMap<K, V> {
    private final Map<K, List<V>> map;

    /**
     * Constructs an empty MultiValueMap.
     */
    public MultiValueMap() {
        this.map = new HashMap<>();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the new value is added to the list of values.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void put(K key, V value) {
        this.map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    /**
     * Returns the list of values to which the specified key is mapped,
     * or an empty list if this map contains no mapping for the key.
     *
     * @param key the key whose associated values are to be returned
     * @return the list of values to which the specified key is mapped, or an empty list if this map contains no mapping for the key
     */
    public List<V> get(K key) {
        return this.map.getOrDefault(key, Collections.emptyList());
    }

    /**
     * Removes the specified value for the specified key in this map.
     * If the last value for the key is removed, the key is also removed from the map.
     *
     * @param key key whose mapping is to be removed from the map
     * @param value value to be removed from the list of values for the specified key
     * @return true if the value was successfully removed, false otherwise
     */
    public boolean remove(K key, V value) {
        List<V> values = this.map.get(key);
        if (values != null) {
            boolean removed = values.remove(value);
            if (values.isEmpty()) {
                this.map.remove(key);
            }
            return removed;
        }
        return false;
    }

    /**
     * Removes all mappings for the specified key from this map.
     *
     * @param key key whose mappings are to be removed from the map
     * @return the list of values that were associated with the key, or null if the key was not found
     */
    public List<V> removeAll(K key) {
        return this.map.remove(key);
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    /**
     * Returns true if this map contains the specified value for the specified key.
     *
     * @param key key whose mapping is to be tested for the specified value
     * @param value value whose presence in the list of values for the specified key is to be tested
     * @return true if this map contains the specified value for the specified key
     */
    public boolean containsValue(K key, V value) {
        List<V> values = this.map.get(key);
        return values != null && values.contains(value);
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        return this.map.keySet();
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return this.map.size();
    }

    /**
     * Removes all the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        this.map.clear();
    }

    /**
     * Returns a string representation of this map. The string representation consists of a list of key-value mappings in the order returned by the map's entry set view.
     *
     * @return a string representation of this map
     */
    @Override
    public String toString() {
        return map.toString();
    }
}
