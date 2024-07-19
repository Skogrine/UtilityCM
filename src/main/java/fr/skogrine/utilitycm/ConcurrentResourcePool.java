package fr.skogrine.utilitycm;

import fr.skogrine.utilitycm.annotation.NotFinished;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A thread-safe pool for managing reusable resources.
 *
 * @param <T> the type of resource managed by the pool
 */
@NotFinished
public class ConcurrentResourcePool<T> {

    private final ConcurrentLinkedQueue<PooledResource> pool;
    private final Supplier<T> resourceSupplier;
    private final ScheduledExecutorService expiryScheduler;
    private final long expiryDurationMillis;
    private final int maxSize;
    private final Consumer<T> disposalCallback;

    private class PooledResource {
        private final T resource;
        private volatile long lastUsed;

        PooledResource(T resource) {
            this.resource = resource;
            this.lastUsed = System.currentTimeMillis();
        }

        T getResource() {
            return resource;
        }

        void updateLastUsed() {
            this.lastUsed = System.currentTimeMillis();
        }
    }

    /**
     * Constructs a ConcurrentResourcePool with the given parameters.
     *
     * @param initialSize the initial size of the pool
     * @param resourceSupplier a supplier to create new resources
     * @param maxSize the maximum size of the pool
     * @param expiryDurationMillis the duration (in milliseconds) after which a resource is considered expired
     * @param disposalCallback a callback for disposing of excess resources
     */
    public ConcurrentResourcePool(int initialSize, Supplier<T> resourceSupplier, int maxSize, long expiryDurationMillis, Consumer<T> disposalCallback) {
        this.pool = new ConcurrentLinkedQueue<>();
        this.resourceSupplier = resourceSupplier;
        this.maxSize = maxSize;
        this.expiryDurationMillis = expiryDurationMillis;
        this.disposalCallback = disposalCallback;
        this.expiryScheduler = Executors.newScheduledThreadPool(1);

        // Pre-fill the pool with initial resources
        for (int i = 0; i < initialSize; i++) {
            pool.add(new PooledResource(resourceSupplier.get()));
        }

        // Schedule periodic task to remove expired resources
        expiryScheduler.scheduleAtFixedRate(this::removeExpiredResources, expiryDurationMillis, expiryDurationMillis, TimeUnit.MILLISECONDS);
    }


    /**
     * Acquires a resource from the pool asynchronously.
     *
     * @return a CompletableFuture that completes with a resource
     */
    public CompletableFuture<T> acquireAsync() {
        return CompletableFuture.supplyAsync(() -> {
            PooledResource pooledResource = pool.poll();
            if (pooledResource != null) {
                pooledResource.updateLastUsed();
                return pooledResource.getResource();
            }

            // Pool is empty, create a new resource
            return resourceSupplier.get();
        });
    }

    /**
     * Releases a resource back to the pool.
     *
     * @param resource the resource to be released
     */
    public void release(T resource) {
        if (pool.size() < maxSize) {
            pool.add(new PooledResource(resource));
        } else {
            if (disposalCallback != null) {
                disposalCallback.accept(resource);
            }
        }
    }

    /**
     * Removes expired resources from the pool.
     */
    private void removeExpiredResources() {
        long now = System.currentTimeMillis();
        Iterator<PooledResource> iterator = pool.iterator();
        while (iterator.hasNext()) {
            PooledResource pooledResource = iterator.next();
            if (now - pooledResource.lastUsed >= expiryDurationMillis) {
                iterator.remove();
                if (disposalCallback != null) {
                    disposalCallback.accept(pooledResource.getResource());
                }
            }
        }
    }

    /**
     * Closes the pool and releases all resources.
     */
    public void close() {
        expiryScheduler.shutdownNow();
        pool.clear();
    }
}
