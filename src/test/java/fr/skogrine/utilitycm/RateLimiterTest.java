package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    @Test
    void testRateLimiter() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(2, 1, TimeUnit.SECONDS); // 2 requests per second

        assertTrue(rateLimiter.tryAcquire());
        assertTrue(rateLimiter.tryAcquire());
        assertFalse(rateLimiter.tryAcquire()); // Should fail since limit reached

        Thread.sleep(1000);

        assertTrue(rateLimiter.tryAcquire()); // Should pass since the rate limit resets
    }
}
