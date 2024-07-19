package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

class TaskQueueTest {

    @Test
    void testTaskQueue() throws InterruptedException {
        TaskQueue taskQueue = new TaskQueue();
        final StringBuilder result = new StringBuilder();

        // Add high-priority task
        taskQueue.addTask(1, () -> result.append("High priority task executed;"));

        // Add low-priority task with a delay of 1 second
        taskQueue.addTask(5, () -> result.append("Low priority task executed;"), 1, 1, TimeUnit.SECONDS);

        // Sleep for a longer time to ensure all tasks are executed
        Thread.sleep(3000);

        // Check the result to ensure both tasks are executed
        assertTrue(result.toString().contains("High priority task executed;"));
        assertTrue(result.toString().contains("Low priority task executed;"));
    }
}
