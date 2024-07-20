package fr.skogrine.utilitycm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * A class that uses reflection to dynamically create and populate objects.
 *
 * Example usage:
 * <pre>
 * {@code
 * Map<String, Object> fieldValues = new HashMap<>();
 * fieldValues.put("name", "John Doe");
 * fieldValues.put("age", 30);
 *
 * ReflectiveObjectBuilder builder = new ReflectiveObjectBuilder();
 * Person person = builder.createObject(Person.class, fieldValues);
 * System.out.println(person.getName()); // Output: John Doe
 * System.out.println(person.getAge());  // Output: 30
 * }
 * </pre>
 */
public class ReflectiveObjectBuilder {
    /**
     * Creates an object of the specified class and populates its fields with the provided values.
     *
     * @param clazz the class of the object to create
     * @param fieldValues a map of field names and values to set
     * @param <T> the type of the object
     * @return the created and populated object
     * @throws Exception if an error occurs during object creation or field population
     */
    public <T> T createObject(Class<T> clazz, Map<String, Object> fieldValues) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();

        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            Field field = clazz.getDeclaredField(entry.getKey());
            field.setAccessible(true);
            field.set(instance, entry.getValue());
        }

        return instance;
    }
}
