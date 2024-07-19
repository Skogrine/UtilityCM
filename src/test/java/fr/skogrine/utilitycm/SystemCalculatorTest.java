package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.MemoryUsage;

/**
 * Unit tests for the {@link SystemCalculator} class.
 */
class SystemCalculatorTest {

    private final SystemCalculator calculator = new SystemCalculator();

    /**
     * Tests the CPU usage retrieval method.
     */
    @Test
    void testGetCpuUsage() {
        double cpuUsage = calculator.getCpuUsage();
        assertTrue(cpuUsage >= 0 && cpuUsage <= 100, "CPU usage should be between 0 and 100 percent");
    }

    /**
     * Tests the total memory retrieval method.
     */
    @Test
    void testGetTotalMemory() {
        long totalMemory = calculator.getTotalMemory();
        assertTrue(totalMemory > 0, "Total memory should be greater than 0 bytes");
    }

    /**
     * Tests the used memory retrieval method.
     */
    @Test
    void testGetUsedMemory() {
        long usedMemory = calculator.getUsedMemory();
        assertTrue(usedMemory >= 0, "Used memory should be non-negative");
    }

    /**
     * Tests the free memory retrieval method.
     */
    @Test
    void testGetFreeMemory() {
        long freeMemory = calculator.getFreeMemory();
        assertTrue(freeMemory >= 0, "Free memory should be non-negative");
    }

    /**
     * Tests the free disk space retrieval method.
     */
    @Test
    void testGetFreeDiskSpace() {
        // Use the root directory as a test path
        long freeDiskSpace = calculator.getFreeDiskSpace("/");
        assertTrue(freeDiskSpace >= 0, "Free disk space should be non-negative");
    }

    /**
     * Tests the total disk space retrieval method.
     */
    @Test
    void testGetTotalDiskSpace() {
        // Use the root directory as a test path
        long totalDiskSpace = calculator.getTotalDiskSpace("/");
        assertTrue(totalDiskSpace >= 0, "Total disk space should be non-negative");
    }

    /**
     * Tests the JVM uptime retrieval method.
     */
    @Test
    void testGetJvmUptime() {
        long uptime = calculator.getJvmUptime();
        assertTrue(uptime >= 0, "JVM uptime should be non-negative");
    }
}
