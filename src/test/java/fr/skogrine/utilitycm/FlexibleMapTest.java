package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlexibleMapTest {

    @Test
    public void testPutIfAbsent() {
        FlexibleMap<String, String> map = new FlexibleMap<>();
        System.out.println("Putting 'key1' with value 'value1'");
        map.putIfAbsent("key1", "value1");
        System.out.println("Attempting to put 'key1' with value 'newValue1'");
        map.putIfAbsent("key1", "newValue1");
        System.out.println("Current value for 'key1': " + map.get("key1"));
        assertEquals("value1", map.get("key1"));
    }

    @Test
    public void testMerge() {
        FlexibleMap<String, Integer> map = new FlexibleMap<>();
        map.put("a", 1);
        System.out.println("Initial value for 'a': " + map.get("a"));
        map.merge("a", 2, Integer::sum);
        System.out.println("Value for 'a' after merge: " + map.get("a"));
        assertEquals(3, map.get("a"));
    }

    @Test
    public void testReplaceWithOldValue() {
        FlexibleMap<String, String> map = new FlexibleMap<>();
        map.put("key", "oldValue");
        System.out.println("Initial value for 'key': " + map.get("key"));
        boolean replaced = map.replace("key", "oldValue", "newValue");
        System.out.println("Replace successful: " + replaced);
        System.out.println("Current value for 'key': " + map.get("key"));
        assertTrue(replaced);
        assertEquals("newValue", map.get("key"));
    }

    @Test
    public void testReplace() {
        FlexibleMap<String, Integer> map = new FlexibleMap<>();
        map.put("key", 1);
        System.out.println("Initial value for 'key': " + map.get("key"));
        int oldValue = map.replace("key", 2);
        System.out.println("Old value for 'key': " + oldValue);
        System.out.println("New value for 'key': " + map.get("key"));
        assertEquals(1, oldValue);
        assertEquals(2, map.get("key"));
    }

    @Test
    public void testCompute() {
        FlexibleMap<String, Integer> map = new FlexibleMap<>();
        map.put("key", 1);
        System.out.println("Initial value for 'key': " + map.get("key"));
        map.compute("key", (k, v) -> v + 1);
        System.out.println("Value for 'key' after compute: " + map.get("key"));
        assertEquals(2, map.get("key"));
    }

    @Test
    public void testComputeIfAbsent() {
        FlexibleMap<String, Integer> map = new FlexibleMap<>();
        System.out.println("Value for 'key' before computeIfAbsent: " + map.get("key"));
        map.computeIfAbsent("key", k -> 42);
        System.out.println("Value for 'key' after computeIfAbsent: " + map.get("key"));
        assertEquals(42, map.get("key"));
    }

    @Test
    public void testComputeIfPresent() {
        FlexibleMap<String, Integer> map = new FlexibleMap<>();
        map.put("key", 1);
        System.out.println("Initial value for 'key': " + map.get("key"));
        map.computeIfPresent("key", (k, v) -> v + 1);
        System.out.println("Value for 'key' after computeIfPresent: " + map.get("key"));
        assertEquals(2, map.get("key"));
    }
}
