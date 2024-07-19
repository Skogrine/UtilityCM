package fr.skogrine.utilitycm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CacheManager is a utility class that provides caching functionalities with different strategies like LRU.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * CacheManager<String, String> cache = new CacheManager<>(3);
 * cache.put("a", "1");
 * cache.put("b", "2");
 * cache.put("c", "3");
 * System.out.println(cache.get("a")); // Output: 1
 * cache.put("d", "4");
 * System.out.println(cache.get("b")); // Output: null (evicted due to LRU policy)
 * }</pre>
 * @param <K> key
 * @param <V> value
 */
public class CacheManager<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    /**
     * Constructs a CacheManager with the specified capacity.
     *
     * @param capacity the maximum number of entries that the cache can hold
     */
    public CacheManager(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }

    /**
     * Puts an entry into the cache.
     *
     * @param key the key
     * @param value the value
     * @return the previous value associated with the key, or null if there was no mapping
     */
    public V put(K key, V value) {
        return super.put(key, value);
    }

    /**
     * Gets an entry from the cache.
     *
     * @param key the key
     * @return the value associated with the key, or null if there is no mapping
     */
    public V get(Object key) {
        return super.get(key);
    }
}
