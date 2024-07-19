package fr.skogrine.utilitycm;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * TaskQueue manages a queue of tasks to be executed asynchronously, supporting prioritization, delay, and retries.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * TaskQueue taskQueue = new TaskQueue();
 * taskQueue.addTask(1, () -> System.out.println("High priority task executed"));
 * taskQueue.addTask(5, () -> System.out.println("Low priority task executed"), 3, 1, TimeUnit.SECONDS);
 * }</pre>
 */
public class TaskQueue {

    private final ScheduledExecutorService executorService;
    private final Queue<ScheduledTask> taskQueue;

    /**
     * Constructs a TaskQueue with a fixed thread pool for executing tasks.
     */
    public TaskQueue() {
        this.executorService = Executors.newScheduledThreadPool(1);
        this.taskQueue = new PriorityQueue<>();
        startProcessing();
    }

    /**
     * Adds a task to the queue with the specified priority.
     *
     * @param priority the priority of the task (lower value means higher priority)
     * @param task the task to be executed
     */
    public void addTask(int priority, Runnable task) {
        addTask(priority, task, 0, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * Adds a task to the queue with the specified priority, delay, and retry configuration.
     *
     * @param priority the priority of the task (lower value means higher priority)
     * @param task the task to be executed
     * @param retryCount the number of times to retry the task if it fails
     * @param timeUnit the time unit for delay and retry interval
     */
    public void addTask(int priority, Runnable task, int retryCount, long retryInterval, TimeUnit timeUnit) {
        taskQueue.offer(new ScheduledTask(priority, task, retryCount, retryInterval, timeUnit));
    }

    private void startProcessing() {
        executorService.scheduleAtFixedRate(() -> {
            ScheduledTask task = taskQueue.poll();
            if (task != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    // Handle task failure (e.g., log error, retry)
                    if (task.retryCount > 0) {
                        task.retryCount--;
                        taskQueue.offer(task);
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * A wrapper class for tasks with priority, retry, and delay configuration.
     */
    private static class ScheduledTask implements Comparable<ScheduledTask>, Runnable {
        private final int priority;
        private final Runnable task;
        private int retryCount;
        private final long retryInterval;
        private final TimeUnit timeUnit;

        ScheduledTask(int priority, Runnable task, int retryCount, long retryInterval, TimeUnit timeUnit) {
            this.priority = priority;
            this.task = task;
            this.retryCount = retryCount;
            this.retryInterval = retryInterval;
            this.timeUnit = timeUnit;
        }

        @Override
        public int compareTo(ScheduledTask o) {
            return Integer.compare(this.priority, o.priority);
        }

        @Override
        public void run() {
            task.run();
        }
    }
}
