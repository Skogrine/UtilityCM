package fr.skogrine.utilitycm;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * SystemCalculator is a utility class that provides methods to measure various system resources such as CPU usage, memory limits/usage, and disk space usage.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SystemCalculator calculator = new SystemCalculator();
 * System.out.println("CPU Usage: " + calculator.getCpuUsage() + "%");
 * System.out.println("Total Memory: " + calculator.getTotalMemory() + " bytes");
 * System.out.println("Used Memory: " + calculator.getUsedMemory() + " bytes");
 * System.out.println("Free Disk Space: " + calculator.getFreeDiskSpace("/") + " bytes");
 * System.out.println("Total Disk Space: " + calculator.getTotalDiskSpace("/") + " bytes");
 * }</pre>
 */
public class SystemCalculator {
    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;
    private final RuntimeMXBean runtimeBean;

    /**
     * Constructs a SystemCalculator.
     */
    public SystemCalculator() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        this.runtimeBean = ManagementFactory.getRuntimeMXBean();
    }

    /**
     * Returns the current CPU usage of the system.
     *
     * @return the CPU usage as a percentage
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("CPU Usage: " + calculator.getCpuUsage() + "%");
     * }</pre>
     */
    public double getCpuUsage() {
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad() * 100;
        }
        return -1;
    }

    /**
     * Returns the total memory available to the JVM.
     *
     * @return the total memory in bytes
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("Total Memory: " + calculator.getTotalMemory() + " bytes");
     * }</pre>
     */
    public long getTotalMemory() {
        return memoryBean.getHeapMemoryUsage().getMax();
    }

    /**
     * Returns the memory currently used by the JVM.
     *
     * @return the used memory in bytes
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("Used Memory: " + calculator.getUsedMemory() + " bytes");
     * }</pre>
     */
    public long getUsedMemory() {
        return memoryBean.getHeapMemoryUsage().getUsed();
    }

    /**
     * Returns the amount of free memory in the JVM.
     *
     * @return the free memory in bytes
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("Free Memory: " + calculator.getFreeMemory() + " bytes");
     * }</pre>
     */
    public long getFreeMemory() {
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        return heapMemoryUsage.getMax() - heapMemoryUsage.getUsed();
    }

    /**
     * Returns the amount of free disk space on the specified path.
     *
     * @param path the file path to check
     * @return the free disk space in bytes
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("Free Disk Space: " + calculator.getFreeDiskSpace("/") + " bytes");
     * }</pre>
     */
    public long getFreeDiskSpace(String path) {
        File file = new File(path);
        return file.getFreeSpace();
    }

    /**
     * Returns the total disk space on the specified path.
     *
     * @param path the file path to check
     * @return the total disk space in bytes
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("Total Disk Space: " + calculator.getTotalDiskSpace("/") + " bytes");
     * }</pre>
     */
    public long getTotalDiskSpace(String path) {
        File file = new File(path);
        return file.getTotalSpace();
    }

    /**
     * Returns the JVM uptime in milliseconds.
     *
     * @return the JVM uptime in milliseconds
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * SystemCalculator calculator = new SystemCalculator();
     * System.out.println("JVM Uptime: " + calculator.getJvmUptime() + " milliseconds");
     * }</pre>
     */
    public long getJvmUptime() {
        return runtimeBean.getUptime();
    }
}
