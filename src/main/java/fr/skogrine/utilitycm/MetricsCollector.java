package fr.skogrine.utilitycm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * MetricsCollector collects and aggregates various performance metrics, such as execution time, memory usage, and throughput, for monitoring and analysis.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * MetricsCollector metricsCollector = new MetricsCollector();
 * metricsCollector.start("task1");
 * // Perform some task
 * metricsCollector.stop("task1");
 * System.out.println("Task 1 duration: " + metricsCollector.getDuration("task1") + " ms");
 * }</pre>
 */
public class MetricsCollector {

    private final Map<String, Long> startTimeMap;
    private final Map<String, Long> durationMap;

    /**
     * Constructs a MetricsCollector.
     */
    public MetricsCollector() {
        this.startTimeMap = new HashMap<>();
        this.durationMap = new HashMap<>();
    }

    /**
     * Starts the timer for a specific metric.
     *
     * @param metricName the name of the metric
     */
    public void start(String metricName) {
        startTimeMap.put(metricName, System.currentTimeMillis());
    }

    /**
     * Stops the timer for a specific metric and records the duration.
     *
     * @param metricName the name of the metric
     */
    public void stop(String metricName) {
        Long startTime = startTimeMap.remove(metricName);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            durationMap.put(metricName, duration);
        }
    }

    /**
     * Gets the duration of a specific metric.
     *
     * @param metricName the name of the metric
     * @return the duration in milliseconds, or -1 if the metric has not been recorded
     */
    public long getDuration(String metricName) {
        return durationMap.getOrDefault(metricName, -1L);
    }
}
