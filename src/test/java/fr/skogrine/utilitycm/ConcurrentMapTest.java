package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConcurrentMapTest {

    @Test
    void testConcurrentMap() {
        ConcurrentMap<String, Integer> map = new ConcurrentMap<>();
        map.put("key1", 1);

        int incrementedValue = map.incrementBy("key1", 5);
        assertEquals(6, incrementedValue);
    }
}