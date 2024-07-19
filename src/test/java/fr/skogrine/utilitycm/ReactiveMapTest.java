package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ReactiveMap} class.
 */
class ReactiveMapTest {

    /**
     * Tests asynchronous put and remove operations.
     */
    @Test
    void testPutAndRemoveAsync() throws InterruptedException {
        ReactiveMap<String, String> reactiveMap = new ReactiveMap<>();
        CountDownLatch latch = new CountDownLatch(2); // Wait for 2 operations

        // Add listeners
        reactiveMap.addListener(change -> {
            if (change.getOperation() == ReactiveMap.Change.Operation.PUT) {
                assertEquals("key1", change.getKey());
                assertEquals("value1", change.getValue());
            } else if (change.getOperation() == ReactiveMap.Change.Operation.REMOVE) {
                assertEquals("key1", change.getKey());
                assertEquals("value1", change.getValue());
            }
            latch.countDown();
        });

        // Perform operations
        reactiveMap.putAsync("key1", "value1").join();
        reactiveMap.removeAsync("key1").join();

        // Wait for operations to complete
        boolean completed = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completed, "Both operations should be completed within the timeout.");
    }

    /**
     * Tests getting a value from the map.
     */
    @Test
    void testGet() {
        ReactiveMap<String, String> reactiveMap = new ReactiveMap<>();
        reactiveMap.putAsync("key1", "value1").join();

        // Retrieve the value
        String value = reactiveMap.get("key1");
        assertEquals("value1", value, "The retrieved value should match the expected value.");
    }
}