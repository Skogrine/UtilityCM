package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeSafeMapTest {

    @Test
    public void testPutAndGet() {
        TypeSafeMap<String, Integer> map = new TypeSafeMap<>(Integer.class);
        map.put("key1", 42);

        assertEquals(42, map.get("key1"));
    }

    @Test
    public void testPutInvalidType() {
        TypeSafeMap<String, Integer> map = new TypeSafeMap<>(Integer.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> map.put("key1", Integer.valueOf("string")));
        assertTrue(exception.getMessage().contains("For input string: \"string\""));
    }

    @Test
    public void testGetWithType() {
        TypeSafeMap<String, Integer> map = new TypeSafeMap<>(Integer.class);
        map.put("key1", 42);

        Integer value = map.get("key1", Integer.class);
        assertEquals(42, value);
    }

    @Test
    public void testGetWithWrongType() {
        TypeSafeMap<String, Integer> map = new TypeSafeMap<>(Integer.class);
        map.put("key1", 42);

        Exception exception = assertThrows(ClassCastException.class, () -> {
            String value = map.get("key1", String.class);
        });

        assertTrue(exception.getMessage().contains("Value is not of type java.lang.String"));
    }

    @Test
    public void testSize() {
        TypeSafeMap<String, Integer> map = new TypeSafeMap<>(Integer.class);
        map.put("key1", 42);
        map.put("key2", 99);

        assertEquals(2, map.size());
    }
}
