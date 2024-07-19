package fr.skogrine.utilitycm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EnhancedJsonObject extends the capabilities of standard JSON manipulation by providing additional methods
 * for easy retrieval and manipulation of data, type-safe accessors, and built-in validation.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * EnhancedJsonObject json = new EnhancedJsonObject();
 * json.put("name", "John");
 * json.put("age", 30);
 * json.putArray("skills", Arrays.asList("Java", "Python", "JavaScript"));
 *
 * String name = json.getString("name");  // John
 * int age = json.getInt("age");         // 30
 * List<String> skills = json.getStringList("skills");  // [Java, Python, JavaScript]
 *
 * json.validateField("name", String.class);
 * json.validateField("age", Integer.class);
 * }</pre>
 */
public class EnhancedJsonObject extends JSONObject {

    public EnhancedJsonObject() {
        super();
    }

    public EnhancedJsonObject(String json) {
        super(json);
    }

    public EnhancedJsonObject(Map<String, Object> map) {
        super(map);
    }

    public void putArray(String key, List<Object> values) {
        put(key, new JSONArray(values));
    }

    public String getString(String key, String defaultValue) {
        return has(key) ? getString(key) : defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        return has(key) ? getInt(key) : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return has(key) ? getBoolean(key) : defaultValue;
    }

    public double getDouble(String key, double defaultValue) {
        return has(key) ? getDouble(key) : defaultValue;
    }

    public long getLong(String key, long defaultValue) {
        return has(key) ? getLong(key) : defaultValue;
    }

    public List<Object> getArray(String key) {
        JSONArray array = getJSONArray(key);
        return array.toList();
    }

    public List<String> getStringList(String key) {
        JSONArray array = getJSONArray(key);
        return array.toList().stream().map(Object::toString).collect(Collectors.toList());
    }

    public void validateField(String key, Class<?> type) {
        if (!has(key)) {
            throw new IllegalArgumentException("Key '" + key + "' is missing.");
        }
        Object value = get(key);
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Key '" + key + "' is not of type " + type.getSimpleName() + ".");
        }
    }

}
