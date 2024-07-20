package fr.skogrine.utilitycm;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * EnhancedList is an extension of ArrayList providing additional utility methods
 * for filtering, mapping, matching, grouping, sorting, and reversing elements.
 *
 * @param <E> the type of elements in this list
 *
 * <pre>
 * {@code
 * // Example usage:
 * EnhancedList<String> list = new EnhancedList<>(Arrays.asList("apple", "banana", "cherry"));
 * EnhancedList<String> filteredList = list.filter(s -> s.startsWith("a"));
 * System.out.println(filteredList); // Output: [apple]
 *
 * EnhancedList<Integer> intList = new EnhancedList<>(Arrays.asList(1, 2, 3));
 * EnhancedList<String> mappedList = intList.map(Object::toString);
 * System.out.println(mappedList); // Output: [1, 2, 3]
 * }
 * </pre>
 */
public class EnhancedList<E> extends ArrayList<E> {
    public EnhancedList() {
        super();
    }

    public EnhancedList(Collection<? extends E> c) {
        super(c);
    }

    public EnhancedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Filters the list based on a predicate.
     *
     * @param predicate the condition to filter the list
     * @return a new EnhancedList with elements that match the predicate
     */
    public EnhancedList<E> filter(Predicate<? super E> predicate) {
        return this.stream().filter(predicate).collect(Collectors.toCollection(EnhancedList::new));
    }

    /**
     * Maps the list to another list of different type using a mapper function.
     *
     * @param mapper the function to transform elements
     * @param <R>    the type of elements in the new list
     * @return a new EnhancedList with transformed elements
     */
    public <R> EnhancedList<R> map(Function<? super E, ? extends R> mapper) {
        return this.stream()
                .map(mapper)
                .collect(Collectors.toCollection(EnhancedList::new));
    }

    /**
     * Checks if all elements in the list match the given predicate.
     *
     * @param predicate the condition to check
     * @return true if all elements match the predicate, false otherwise
     */
    public boolean allMatch(Predicate<? super E> predicate) {
        return this.stream().allMatch(predicate);
    }

    /**
     * Checks if any element in the list matches the given predicate.
     *
     * @param predicate the condition to check
     * @return true if any element matches the predicate, false otherwise
     */
    public boolean anyMatch(Predicate<? super E> predicate) {
        return this.stream().anyMatch(predicate);
    }

    /**
     * Groups elements in the list based on a classifier function.
     *
     * @param classifier the function to classify elements
     * @param <K>        the type of the keys in the grouping map
     * @return a map where keys are the results of applying the classifier function, and values are lists of items that belong to those groups
     */
    public <K> Map<K, EnhancedList<E>> groupBy(Function<? super E, ? extends K> classifier) {
        return this.stream()
                .collect(Collectors.groupingBy(
                        classifier,
                        Collectors.toCollection(EnhancedList::new)
                ));
    }

    /**
     * Sorts the list in place based on the given comparator.
     *
     * @param comparator the comparator to determine the order of the list
     */
    public void sortInPlace(Comparator<? super E> comparator) {
        this.sort(comparator);
    }

    /**
     * Reverses the list in place.
     */
    public void reverseInPlace() {
        Collections.reverse(this);
    }
}
