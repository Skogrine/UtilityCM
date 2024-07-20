package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SafeQueueTest {

    @Test
    public void testEnqueueAndDequeue() {
        SafeQueue<Integer> queue = new SafeQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @Test
    public void testPeek() {
        SafeQueue<Integer> queue = new SafeQueue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.peek());
        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.peek());
    }

    @Test
    public void testIsEmptySafe() {
        SafeQueue<String> queue = new SafeQueue<>();
        assertTrue(queue.isEmptySafe());
        queue.enqueue("item");
        assertFalse(queue.isEmptySafe());
    }

    @Test
    public void testDequeueEmptyQueue() {
        SafeQueue<Integer> queue = new SafeQueue<>();
        assertThrows(IllegalStateException.class, queue::dequeue);
    }

    @Test
    public void testPeekEmptyQueue() {
        SafeQueue<Integer> queue = new SafeQueue<>();
        assertThrows(IllegalStateException.class, queue::peek);
    }
}
