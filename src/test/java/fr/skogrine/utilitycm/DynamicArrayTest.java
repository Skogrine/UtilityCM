package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    public void testAddAndGet() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(1);
        dynamicArray.add(2);
        dynamicArray.add(3);

        System.out.println("Array size after additions: " + dynamicArray.size());
        assertEquals(3, dynamicArray.size());

        System.out.println("Element at index 0: " + dynamicArray.get(0));
        System.out.println("Element at index 1: " + dynamicArray.get(1));
        System.out.println("Element at index 2: " + dynamicArray.get(2));

        assertEquals(1, dynamicArray.get(0));
        assertEquals(2, dynamicArray.get(1));
        assertEquals(3, dynamicArray.get(2));
    }

    @Test
    public void testRemove() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(1);
        dynamicArray.add(2);
        dynamicArray.add(3);

        dynamicArray.remove(1);

        System.out.println("Array size after removal: " + dynamicArray.size());
        assertEquals(2, dynamicArray.size());

        System.out.println("Element at index 0: " + dynamicArray.get(0));
        System.out.println("Element at index 1: " + dynamicArray.get(1));

        assertEquals(1, dynamicArray.get(0));
        assertEquals(3, dynamicArray.get(1));
    }

    @Test
    public void testEnsureCapacity() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for (int i = 0; i < 15; i++) {
            dynamicArray.add(i);
        }

        System.out.println("Array size after 15 additions: " + dynamicArray.size());
        assertEquals(15, dynamicArray.size());

        for (int i = 0; i < 15; i++) {
            System.out.println("Element at index " + i + ": " + dynamicArray.get(i));
            assertEquals(i, dynamicArray.get(i));
        }
    }

    @Test
    public void testCheckIndex() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(1);
        dynamicArray.add(2);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            dynamicArray.get(2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            dynamicArray.remove(2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            dynamicArray.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            dynamicArray.remove(-1);
        });
    }
}
