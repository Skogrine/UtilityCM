package fr.skogrine.utilitycm;

import java.util.LinkedList;

/**
 * SafeQueue is a thread-safe implementation of a queue using LinkedList.
 * It provides synchronized methods to ensure safe concurrent access.
 *
 * @param <E> the type of elements in this queue
 *
 * <pre>
 * {@code
 * // Example usage:
 * SafeQueue<Integer> queue = new SafeQueue<>();
 * queue.enqueue(1);
 * queue.enqueue(2);
 * System.out.println(queue.dequeue()); // Output: 1
 * System.out.println(queue.dequeue()); // Output: 2
 * }
 * </pre>
 */
public class SafeQueue<E> extends LinkedList<E> {
    /**
     * Adds an element to the end of the queue in a thread-safe manner.
     *
     * @param element the element to add
     * <pre>
     * {@code
     * // Example usage:
     * SafeQueue<String> queue = new SafeQueue<>();
     * queue.enqueue("first");
     * queue.enqueue("second");
     * System.out.println(queue); // Output: [first, second]
     * }
     * </pre>
     */
    public synchronized void enqueue(E element) {
        this.addLast(element);
    }

    /**
     * Removes and returns the first element of the queue in a thread-safe manner.
     *
     * @return the first element of the queue
     * @throws IllegalStateException if the queue is empty
     * <pre>
     * {@code
     * // Example usage:
     * SafeQueue<Integer> queue = new SafeQueue<>();
     * queue.enqueue(10);
     * queue.enqueue(20);
     * System.out.println(queue.dequeue()); // Output: 10
     * System.out.println(queue.dequeue()); // Output: 20
     * }
     * </pre>
     */
    public synchronized E dequeue() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return this.removeFirst();
    }

    /**
     * Returns the first element of the queue without removing it in a thread-safe manner.
     *
     * @return the first element of the queue
     * @throws IllegalStateException if the queue is empty
     * <pre>
     * {@code
     * // Example usage:
     * SafeQueue<Integer> queue = new SafeQueue<>();
     * queue.enqueue(10);
     * queue.enqueue(20);
     * System.out.println(queue.peek()); // Output: 10
     * }
     * </pre>
     */
    public synchronized E peek() {
        if (this.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return this.getFirst();
    }

    /**
     * Checks if the queue is empty in a thread-safe manner.
     *
     * @return true if the queue is empty, false otherwise
     * <pre>
     * {@code
     * // Example usage:
     * SafeQueue<String> queue = new SafeQueue<>();
     * System.out.println(queue.isEmptySafe()); // Output: true
     * queue.enqueue("item");
     * System.out.println(queue.isEmptySafe()); // Output: false
     * }
     * </pre>
     */
    public synchronized boolean isEmptySafe() {
        return this.isEmpty();
    }

}
