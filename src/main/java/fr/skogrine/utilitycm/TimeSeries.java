package fr.skogrine.utilitycm;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a time series with data points and provides methods to calculate statistics
 * and query data within a specified time range.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * TimeSeries ts = new TimeSeries();
 * ts.addDataPoint(Instant.now(), 10.0);
 * ts.addDataPoint(Instant.now().plusSeconds(60), 20.0);
 * ts.addDataPoint(Instant.now().plusSeconds(120), 15.0);
 *
 * System.out.println("Average: " + ts.getAverage());
 * System.out.println("Max: " + ts.getMax());
 * System.out.println("Min: " + ts.getMin());
 * System.out.println("Data Points: " + ts.getDataPointsInRange(Instant.now(), Instant.now().plusSeconds(90)));
 * }</pre>
 */
public class TimeSeries {
    private final List<DataPoint> dataPoints = new ArrayList<>();

    /**
     * Adds a data point to the time series.
     *
     * @param timestamp the timestamp of the data point
     * @param value the value of the data point
     */
    public void addDataPoint(Instant timestamp, double value) {
        dataPoints.add(new DataPoint(timestamp, value));
    }

    /**
     * Returns the average value of the data points.
     *
     * @return the average value of the data points
     */
    public double getAverage() {
        return dataPoints.stream()
                .mapToDouble(DataPoint::value)
                .average()
                .orElse(Double.NaN);
    }

    /**
     * Returns the maximum value among the data points.
     *
     * @return the maximum value
     */
    public double getMax() {
        return dataPoints.stream()
                .mapToDouble(DataPoint::value)
                .max()
                .orElse(Double.NaN);
    }

    /**
     * Returns the minimum value among the data points.
     *
     * @return the minimum value
     */
    public double getMin() {
        return dataPoints.stream()
                .mapToDouble(DataPoint::value)
                .min()
                .orElse(Double.NaN);
    }

    /**
     * Returns the list of data points within the specified time range.
     *
     * @param start the start of the time range
     * @param end the end of the time range
     * @return the list of data points within the range
     */
    public List<DataPoint> getDataPointsInRange(Instant start, Instant end) {
        return dataPoints.stream()
                .filter(dp -> !dp.timestamp().isBefore(start) && !dp.timestamp().isAfter(end))
                .collect(Collectors.toList());
    }

    /**
     * Represents a data point in the time series.
     *
     * @param timestamp the timestamp of the data point
     * @param value the value of the data point
     */
    public record DataPoint(Instant timestamp, double value) {}
}
