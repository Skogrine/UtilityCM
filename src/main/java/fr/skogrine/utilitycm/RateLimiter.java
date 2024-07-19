package fr.skogrine.utilitycm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RateLimiter is a utility class for rate limiting actions, supporting different strategies such as token bucket and leaky bucket.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * RateLimiter limiter = new RateLimiter(5, 1, TimeUnit.SECONDS);
 * for (int i = 0; i < 10; i++) {
 *     if (limiter.tryAcquire()) {
 *         System.out.println("Action allowed");
 *     } else {
 *         System.out.println("Rate limit exceeded");
 *     }
 *     Thread.sleep(200);
 * }
 * }</pre>
 */
public class RateLimiter {

    private final int maxRequests;
    private final long timePeriod;
    private final TimeUnit timeUnit;
    private final AtomicInteger requestCount;
    private long lastResetTime;

    /**
     * Constructs a RateLimiter with the specified rate limit.
     *
     * @param maxRequests the maximum number of requests allowed in the time period
     * @param timePeriod the time period in the specified time unit
     * @param timeUnit the time unit for the time period
     */
    public RateLimiter(int maxRequests, long timePeriod, TimeUnit timeUnit) {
        this.maxRequests = maxRequests;
        this.timePeriod = timePeriod;
        this.timeUnit = timeUnit;
        this.requestCount = new AtomicInteger(0);
        this.lastResetTime = System.nanoTime();
    }

    /**
     * Attempts to acquire permission to perform an action.
     *
     * @return true if the action is allowed, false if the rate limit is exceeded
     */
    public boolean tryAcquire() {
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastResetTime;

        if (elapsedTime > timeUnit.toNanos(timePeriod)) {
            requestCount.set(0);
            lastResetTime = currentTime;
        }

        return requestCount.incrementAndGet() <= maxRequests;
    }
}
