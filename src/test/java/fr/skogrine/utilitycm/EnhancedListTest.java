package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EnhancedListTest {

    @Test
    public void testFilter() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(1, 2, 3, 4, 5));
        EnhancedList<Integer> filteredList = list.filter(x -> x % 2 == 0);
        assertEquals(Arrays.asList(2, 4), filteredList);
    }

    @Test
    public void testMap() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(1, 2, 3));
        EnhancedList<String> mappedList = list.map(String::valueOf);
        assertEquals(Arrays.asList("1", "2", "3"), mappedList);
    }

    @Test
    public void testAllMatch() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(2, 4, 6));
        assertTrue(list.allMatch(x -> x % 2 == 0));
    }

    @Test
    public void testAnyMatch() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(1, 3, 5));
        assertTrue(list.anyMatch(x -> x % 2 != 0));
    }

    @Test
    public void testGroupBy() {
        EnhancedList<String> list = new EnhancedList<>(Arrays.asList("apple", "banana", "apricot"));
        Map<Character, EnhancedList<String>> grouped = list.groupBy(s -> s.charAt(0));
        assertEquals(Arrays.asList("apple", "apricot"), grouped.get('a'));
        assertEquals(List.of("banana"), grouped.get('b'));
    }

    @Test
    public void testSortInPlace() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(3, 1, 2));
        list.sortInPlace(Comparator.naturalOrder());
        assertEquals(Arrays.asList(1, 2, 3), list);
    }

    @Test
    public void testReverseInPlace() {
        EnhancedList<Integer> list = new EnhancedList<>(Arrays.asList(1, 2, 3));
        list.reverseInPlace();
        assertEquals(Arrays.asList(3, 2, 1), list);
    }
}
