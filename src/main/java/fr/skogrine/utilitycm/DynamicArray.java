package fr.skogrine.utilitycm;

import java.util.Arrays;

/**
 * A dynamic array implementation that provides automatic resizing and custom functionalities.
 *
 * Example usage:
 * <pre>
 * {@code
 * DynamicArray<Integer> dynamicArray = new DynamicArray<>();
 * dynamicArray.add(1);
 * dynamicArray.add(2);
 * dynamicArray.add(3);
 * dynamicArray.remove(2);
 * System.out.println(dynamicArray.size()); // Output: 2
 * System.out.println(dynamicArray.get(0)); // Output: 1
 * }
 * </pre>
 *
 * @param <E> the type of elements in this dynamic array
 */
public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size = 0;

    public DynamicArray() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(E element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public E get(int index) {
        checkIndex(index);
        return (E) elements[index];
    }

    public void remove(int index) {
        checkIndex(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}