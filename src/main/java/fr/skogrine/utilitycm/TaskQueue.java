package fr.skogrine.utilitycm;

import fr.skogrine.utilitycm.annotation.NotFinished;

import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class that manages and schedules tasks based on priority and delay.
 * Tasks with higher priority are executed before tasks with lower priority.
 * Tasks can be scheduled with a delay and a periodic interval.
 *
 * @hidden
 * @implNote Don't use it right now
 */
@NotFinished
public class TaskQueue {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>();

    /**
     * Adds a task to the queue with the specified priority.
     * The task will be executed immediately without any delay.
     *
     * @param priority the priority of the task; higher values indicate higher priority
     * @param task     the task to be executed
     */
    public synchronized void addTask(int priority, Runnable task) {
        addTask(priority, task, 0, 1, TimeUnit.MILLISECONDS);
    }

    /**
     * Adds a task to the queue with the specified priority, delay, and periodic interval.
     * The task will be executed after the specified delay and then periodically
     * based on the specified period.
     *
     * @param priority the priority of the task; higher values indicate higher priority
     * @param task     the task to be executed
     * @param delay    the delay before the task is first executed
     * @param period   the period between successive executions of the task
     * @param timeUnit the time unit for delay and period
     */
    public synchronized void addTask(int priority, Runnable task, long delay, long period, TimeUnit timeUnit) {
        if (delay < 0 || period < 0) {
            throw new IllegalArgumentException("Delay and period must be non-negative");
        }
        if (period == 0) {
            throw new IllegalArgumentException("Period must be positive if specified");
        }

        ScheduledTask scheduledTask = new ScheduledTask(priority, task, delay, period, timeUnit);
        taskQueue.add(scheduledTask);
        scheduleNextTask();
    }

    /**
     * Schedules the next task in the queue to be executed.
     * The task with the highest priority is selected and scheduled.
     */
    private synchronized void scheduleNextTask() {
        ScheduledTask task = taskQueue.poll();
        if (task != null) {
            executorService.scheduleAtFixedRate(task, task.getDelay(), task.getPeriod(), task.getTimeUnit());
        }
    }

    /**
     * A private inner class representing a task with a specified priority,
     * delay, and periodic interval.
     */
    private static class ScheduledTask extends java.util.TimerTask implements Comparable<ScheduledTask> {
        private final int priority;
        private final Runnable task;
        private final long delay;
        private final long period;
        private final TimeUnit timeUnit;

        /**
         * Constructs a ScheduledTask with the specified priority, task, delay, period, and time unit.
         *
         * @param priority the priority of the task; higher values indicate higher priority
         * @param task     the task to be executed
         * @param delay    the delay before the task is first executed
         * @param period   the period between successive executions of the task
         * @param timeUnit the time unit for delay and period
         */
        public ScheduledTask(int priority, Runnable task, long delay, long period, TimeUnit timeUnit) {
            this.priority = priority;
            this.task = task;
            this.delay = delay;
            this.period = period;
            this.timeUnit = timeUnit;
        }

        /**
         * Gets the delay before the task is first executed, in milliseconds.
         *
         * @return the delay in milliseconds
         */
        public long getDelay() {
            return timeUnit.toMillis(delay);
        }

        /**
         * Gets the period between successive executions of the task, in milliseconds.
         *
         * @return the period in milliseconds
         */
        public long getPeriod() {
            return timeUnit.toMillis(period);
        }

        /**
         * Gets the time unit used for delay and period.
         *
         * @return the time unit
         */
        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        /**
         * Executes the task.
         */
        @Override
        public void run() {
            task.run();
        }

        /**
         * Compares this task with another based on priority.
         *
         * @param other the other task to compare with
         * @return a negative integer, zero, or a positive integer as this task's priority
         *         is greater than, equal to, or less than the other task's priority
         */
        @Override
        public int compareTo(ScheduledTask other) {
            return Integer.compare(other.priority, this.priority);
        }
    }
}

