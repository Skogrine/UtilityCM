package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ReflectiveObjectBuilderTest {

    public static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    @Test
    public void testCreateObject() throws Exception {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("name", "John Doe");
        fieldValues.put("age", 30);

        ReflectiveObjectBuilder builder = new ReflectiveObjectBuilder();
        Person person = builder.createObject(Person.class, fieldValues);

        System.out.println("Created person: " + person.getName() + ", " + person.getAge());

        assertEquals("John Doe", person.getName());
        assertEquals(30, person.getAge());
    }

    public static class Car {
        private String make;
        private String model;
        private int year;

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
        }

        public int getYear() {
            return year;
        }
    }

    @Test
    public void testCreateObjectWithDifferentClass() throws Exception {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("make", "Toyota");
        fieldValues.put("model", "Corolla");
        fieldValues.put("year", 2020);

        ReflectiveObjectBuilder builder = new ReflectiveObjectBuilder();
        Car car = builder.createObject(Car.class, fieldValues);

        System.out.println("Created car: " + car.getMake() + ", " + car.getModel() + ", " + car.getYear());

        assertEquals("Toyota", car.getMake());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
    }

    @Test
    public void testCreateObjectWithInvalidField() {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("name", "John Doe");
        fieldValues.put("invalidField", "test");

        ReflectiveObjectBuilder builder = new ReflectiveObjectBuilder();

        assertThrows(NoSuchFieldException.class, () -> {
            builder.createObject(Person.class, fieldValues);
        });
    }
}