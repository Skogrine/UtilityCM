package fr.skogrine.utilitycm;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A class for dynamically allocating and managing system resources such as memory and CPU time.
 *
 * <p>This class allows for the dynamic allocation and deallocation of resources based on real-time
 * needs and constraints. It supports defining and applying resource policies to optimize resource
 * usage and performance.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a DynamicResourceAllocator with custom resource policies
 * DynamicResourceAllocator allocator = new DynamicResourceAllocator()
 *     .setCpuPolicy(cpuUsage -> {
 *         if (cpuUsage > 0.8) {
 *             // Reduce CPU allocation if usage is high
 *             allocator.setMaxThreads(4);
 *         } else {
 *             // Increase CPU allocation if usage is low
 *             allocator.setMaxThreads(8);
 *         }
 *     })
 *     .setMemoryPolicy(memoryUsage -> {
 *         if (memoryUsage > 0.8) {
 *             // Reduce memory allocation if usage is high
 *             allocator.setMaxMemory(512 * 1024 * 1024); // 512 MB
 *         } else {
 *             // Increase memory allocation if usage is low
 *             allocator.setMaxMemory(1024 * 1024 * 1024); // 1 GB
 *         }
 *     });
 *
 * // Start the resource allocator
 * allocator.startMonitoring();
 * }</pre>
 */
public class DynamicResourceAllocator {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final AtomicInteger maxThreads = new AtomicInteger(Runtime.getRuntime().availableProcessors());
    private final AtomicLong maxMemory = new AtomicLong(Runtime.getRuntime().maxMemory());
    private Consumer<Double> cpuPolicy;
    private Consumer<Double> memoryPolicy;

    /**
     * Sets the CPU policy for dynamic allocation.
     *
     * @param cpuPolicy a function that takes the current CPU usage and adjusts resource allocation
     * @return this DynamicResourceAllocator
     */
    public DynamicResourceAllocator setCpuPolicy(Consumer<Double> cpuPolicy) {
        this.cpuPolicy = cpuPolicy;
        return this;
    }

    /**
     * Sets the memory policy for dynamic allocation.
     *
     * @param memoryPolicy a function that takes the current memory usage and adjusts resource allocation
     * @return this DynamicResourceAllocator
     */
    public DynamicResourceAllocator setMemoryPolicy(Consumer<Double> memoryPolicy) {
        this.memoryPolicy = memoryPolicy;
        return this;
    }

    /**
     * Starts monitoring system resources and applying the defined policies.
     */
    public void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            double cpuUsage = getCpuUsage();
            double memoryUsage = getMemoryUsage();

            if (cpuPolicy != null) {
                cpuPolicy.accept(cpuUsage);
            }

            if (memoryPolicy != null) {
                memoryPolicy.accept(memoryUsage);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Stops the monitoring of system resources.
     */
    public void stopMonitoring() {
        scheduler.shutdown();
    }

    /**
     * Returns the current CPU usage as a percentage.
     *
     * @return the CPU usage as a percentage
     */
    private double getCpuUsage() {
        // Placeholder for actual CPU usage calculation
        // Implement logic to retrieve system CPU usage
        return Math.random(); // Replace with actual implementation
    }

    /**
     * Returns the current memory usage as a percentage.
     *
     * @return the memory usage as a percentage
     */
    private double getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        long maxMemory = runtime.maxMemory();
        return (double) usedMemory / maxMemory;
    }

    /**
     * Sets the maximum number of threads that can be used.
     *
     * @param threads the maximum number of threads
     */
    public void setMaxThreads(int threads) {
        // Implement logic to adjust thread pool size based on maxThreads
        // For example, use ThreadPoolExecutor to adjust the number of threads
        System.out.println("Setting max threads to " + threads);
    }

    /**
     * Sets the maximum memory available to the JVM.
     *
     * @param memory the maximum memory in bytes
     */
    public void setMaxMemory(long memory) {
        // Implement logic to adjust maximum memory available to JVM
        System.out.println("Setting max memory to " + memory);
    }
}
