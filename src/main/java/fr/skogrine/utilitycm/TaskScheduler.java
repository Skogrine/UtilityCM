package fr.skogrine.utilitycm;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TaskScheduler is an advanced task scheduling system that supports cron-like scheduling, delayed tasks, and repeated tasks.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * TaskScheduler scheduler = new TaskScheduler();
 * scheduler.schedule(() -> System.out.println("Task executed!"), 1000);
 * scheduler.scheduleAtFixedRate(() -> System.out.println("Repeated task!"), 0, 1000);
 * }</pre>
 */
public class TaskScheduler {

    private final Timer timer;

    /**
     * Constructs a TaskScheduler.
     */
    public TaskScheduler() {
        this.timer = new Timer();
    }

    /**
     * Schedules a one-time task to be executed after a specified delay.
     *
     * @param task the task to be executed
     * @param delay the delay in milliseconds
     */
    public void schedule(Runnable task, long delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delay);
    }

    /**
     * Schedules a task to be executed repeatedly at a fixed rate.
     *
     * @param task the task to be executed
     * @param initialDelay the initial delay in milliseconds
     * @param period the period between successive executions in milliseconds
     */
    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, initialDelay, period);
    }

    /**
     * Cancels the scheduler and all scheduled tasks.
     */
    public void cancel() {
        timer.cancel();
    }
}
