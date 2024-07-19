package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MetricsCollectorTest {

    @Test
    void testMetricsCollector() {
        MetricsCollector metricsCollector = new MetricsCollector();
        metricsCollector.start("task1");

        // Simulate task duration
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        metricsCollector.stop("task1");
        long duration = metricsCollector.getDuration("task1");

        assertTrue(duration >= 500); // Ensure duration is as expected
    }
}
