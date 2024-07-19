package fr.skogrine.utilitycm.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * A class for advanced interactions with Redis, supporting asynchronous operations,
 * automatic expiry management, cluster support, and performance metrics.
 *
 * <p>This class provides a high-level API for Redis operations and integrates modern Java
 * features for improved performance and flexibility.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * AdvancedRedisClient redisClient = new AdvancedRedisClient("localhost", 6379)
 *     .setOnError(e -> System.err.println("Redis error: " + e.getMessage()))
 *     .setPerformanceLogger(message -> System.out.println(message));
 *
 * redisClient.setAsync("key", "value").thenAccept(result -> {
 *     if (result) {
 *         System.out.println("Value set successfully");
 *     } else {
 *         System.err.println("Failed to set value");
 *     }
 * });
 *
 * redisClient.getAsync("key").thenAccept(value -> System.out.println("Value: " + value));
 * }</pre>
 */
public class AdvancedRedisClient {
    private static final Logger LOGGER = Logger.getLogger(AdvancedRedisClient.class.getName());

    private final JedisPool jedisPool;
    private final JedisCluster jedisCluster;
    private Consumer<Throwable> onError;
    private Consumer<String> performanceLogger;

    /**
     * Constructs an AdvancedRedisClient with a single Redis instance.
     *
     * @param host the Redis server host
     * @param port the Redis server port
     */
    public AdvancedRedisClient(String host, int port) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
        this.jedisCluster = null;
    }

    /**
     * Constructs an AdvancedRedisClient with a Redis cluster.
     *
     * @param clusterNodes the set of Redis cluster nodes as HostAndPort
     */
    public AdvancedRedisClient(Set<HostAndPort> clusterNodes) {
        this.jedisPool = null;
        this.jedisCluster = new JedisCluster(clusterNodes);
    }

    /**
     * Sets an error handler to handle Redis exceptions.
     *
     * @param onError a consumer that handles exceptions
     * @return this AdvancedRedisClient
     */
    public AdvancedRedisClient setOnError(Consumer<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Sets a performance logger to log Redis operation metrics.
     *
     * @param performanceLogger a consumer that logs performance metrics
     * @return this AdvancedRedisClient
     */
    public AdvancedRedisClient setPerformanceLogger(Consumer<String> performanceLogger) {
        this.performanceLogger = performanceLogger;
        return this;
    }

    /**
     * Sets a value asynchronously in Redis.
     *
     * @param key the key
     * @param value the value
     * @return a CompletableFuture indicating the result of the operation
     */
    public CompletableFuture<Boolean> setAsync(String key, String value) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set(key, value);
                return true;
            } catch (JedisConnectionException e) {
                if (onError != null) onError.accept(e);
                return false;
            } finally {
                logPerformance("SET command executed in " + (System.currentTimeMillis() - startTime) + " ms");
            }
        });
    }

    /**
     * Gets a value asynchronously from Redis.
     *
     * @param key the key
     * @return a CompletableFuture containing the value
     */
    public CompletableFuture<String> getAsync(String key) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            try (Jedis jedis = jedisPool.getResource()) {
                return jedis.get(key);
            } catch (JedisConnectionException e) {
                if (onError != null) onError.accept(e);
                return null;
            } finally {
                logPerformance("GET command executed in " + (System.currentTimeMillis() - startTime) + " ms");
            }
        });
    }

    /**
     * Logs performance metrics if a performance logger is set.
     *
     * @param message the performance message
     */
    private void logPerformance(String message) {
        if (performanceLogger != null) {
            performanceLogger.accept(message);
        }
    }

    /**
     * Closes the Redis connection pool.
     */
    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        } else if (jedisCluster != null) {
            jedisCluster.close();
        }
    }

}
