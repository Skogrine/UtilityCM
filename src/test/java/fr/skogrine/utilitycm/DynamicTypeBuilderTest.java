package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

public class DynamicTypeBuilderTest {

    @Test
    public void testDynamicClassCreation() throws Exception {
        DynamicTypeBuilder builder = new DynamicTypeBuilder("DynamicPerson");
        builder.addField("name", String.class);
        builder.addField("age", int.class);
        builder.addMethod("greet", String.class, "return \"Hello, \" + arg0 + \"!\";", String.class);

        Class<?> dynamicClass = builder.build();
        Object instance = dynamicClass.getConstructor().newInstance();

        Method setName = dynamicClass.getMethod("setName", String.class);
        setName.invoke(instance, "John");

        Method greet = dynamicClass.getMethod("greet", String.class);
        String greeting = (String) greet.invoke(instance, "John");
        assertEquals("Hello, John!", greeting);
    }

    @Test
    public void testFieldGetterSetter() throws Exception {
        DynamicTypeBuilder builder = new DynamicTypeBuilder("DynamicPerson");
        builder.addField("name", String.class);
        builder.addField("age", int.class);

        Class<?> dynamicClass = builder.build();
        Object instance = dynamicClass.getConstructor().newInstance();

        Method setName = dynamicClass.getMethod("setName", String.class);
        Method getName = dynamicClass.getMethod("getName");
        Method setAge = dynamicClass.getMethod("setAge", int.class);
        Method getAge = dynamicClass.getMethod("getAge");

        setName.invoke(instance, "Alice");
        setAge.invoke(instance, 25);

        assertEquals("Alice", getName.invoke(instance));
        assertEquals(25, getAge.invoke(instance));
    }
}