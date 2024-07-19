package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ConcurrentResourcePool} class.
 */
class ConcurrentResourcePoolTest {

    /**
     * Tests acquiring and releasing resources from the pool asynchronously.
     */
    @Test
    void testAcquireAndReleaseAsync() throws InterruptedException {
        Supplier<String> resourceSupplier = () -> "resource";
        CountDownLatch latch = new CountDownLatch(2);

        // Mock disposal callback
        Consumer<String> disposalCallback = resource -> {
            // Verify disposal callback is not triggered in this test
            assertFalse(true, "Disposal callback should not be triggered in this test.");
        };

        ConcurrentResourcePool<String> pool = new ConcurrentResourcePool<>(2, resourceSupplier, 5, 1000, disposalCallback);

        // Acquire resources asynchronously
        pool.acquireAsync().thenAccept(resource1 -> {
            pool.acquireAsync().thenAccept(resource2 -> {
                assertNotNull(resource1);
                assertNotNull(resource2);
                assertNotEquals(resource1, resource2); // Resources should be distinct

                pool.release(resource1);
                pool.release(resource2);
                latch.countDown();
            });
        });

        // Wait for operations to complete
        boolean completed = latch.await(5, TimeUnit.SECONDS);
        assertTrue(completed, "Both operations should be completed within the timeout.");
    }

    /**
     * Tests resource expiry mechanism and disposal callback.
     */
    @Test
    void testResourceExpiryAndDisposal() throws InterruptedException {
        Supplier<String> resourceSupplier = () -> "resource";
        CountDownLatch disposalLatch = new CountDownLatch(1);

        // Mock disposal callback
        Consumer<String> disposalCallback = resource -> {
            System.out.println("Disposing of resource: " + resource);
            disposalLatch.countDown(); // Indicate that disposal callback has been triggered
        };

        ConcurrentResourcePool<String> pool = new ConcurrentResourcePool<>(2, resourceSupplier, 2, 500, disposalCallback);

        String resource1 = pool.acquireAsync().join();
        TimeUnit.MILLISECONDS.sleep(600); // Wait for resource to expire

        String resource2 = pool.acquireAsync().join();

        assertNotEquals(resource1, resource2); // Resource should have expired
        assertTrue(disposalLatch.await(5, TimeUnit.SECONDS), "Disposal callback should be triggered within the timeout.");
    }

    /**
     * Tests the pool closing mechanism and resource disposal.
     */
    @Test
    void testCloseAndDisposal() throws InterruptedException {
        Supplier<String> resourceSupplier = () -> "resource";
        CountDownLatch disposalLatch = new CountDownLatch(1);

        // Mock disposal callback
        Consumer<String> disposalCallback = resource -> {
            System.out.println("Disposing of resource: " + resource);
            disposalLatch.countDown(); // Indicate that disposal callback has been triggered
        };

        ConcurrentResourcePool<String> pool = new ConcurrentResourcePool<>(2, resourceSupplier, 2, 1000, disposalCallback);

        pool.acquireAsync().join();
        pool.close();

        // Ensure disposal callback is triggered upon closing
        assertTrue(disposalLatch.await(5, TimeUnit.SECONDS), "Disposal callback should be triggered within the timeout.");
    }
}
