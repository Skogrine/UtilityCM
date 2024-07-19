package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnhancedJsonObjectTest {

    @Test
    public void testPutAndGetMethods() {
        EnhancedJsonObject json = new EnhancedJsonObject();
        json.put("name", "John");
        json.put("age", 30);
        json.putArray("skills", Arrays.asList("Java", "Python", "JavaScript"));

        assertEquals("John", json.getString("name"));
        assertEquals(30, json.getInt("age"));
        List<String> skills = json.getStringList("skills");
        System.out.println(json);
        assertEquals(Arrays.asList("Java", "Python", "JavaScript"), skills);
    }

    @Test
    public void testDefaultValues() {
        EnhancedJsonObject json = new EnhancedJsonObject();
        System.out.println(json);
        assertEquals("defaultName", json.getString("name", "defaultName"));
        assertEquals(25, json.getInt("age", 25));
        assertTrue(json.getBoolean("active", true));
        assertEquals(12.5, json.getDouble("score", 12.5), 0.001);
        assertEquals(100L, json.getLong("timestamp", 100L));
    }

    @Test
    public void testValidation() {
        EnhancedJsonObject json = new EnhancedJsonObject();
        json.put("name", "John");
        json.put("age", 30);

        System.out.println(json);
        assertDoesNotThrow(() -> json.validateField("name", String.class));
        assertDoesNotThrow(() -> json.validateField("age", Integer.class));

        assertThrows(IllegalArgumentException.class, () -> json.validateField("active", Boolean.class));
        assertThrows(IllegalArgumentException.class, () -> json.validateField("name", Integer.class));
    }
}
