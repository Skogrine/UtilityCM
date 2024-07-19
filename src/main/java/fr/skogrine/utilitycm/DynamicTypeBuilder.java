package fr.skogrine.utilitycm;

import javassist.*;

import java.util.HashMap;
import java.util.Map;

/**
 * DynamicTypeBuilder allows developers to dynamically create classes with specified fields and methods at runtime.
 * This class leverages Java's reflection and bytecode manipulation capabilities using the Javassist library.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * DynamicTypeBuilder builder = new DynamicTypeBuilder("DynamicPerson");
 * builder.addField("name", String.class);
 * builder.addField("age", int.class);
 * builder.addMethod("greet", String.class, "return \"Hello, \" + name + \"!\";", String.class);
 *
 * Class<?> dynamicClass = builder.build();
 * Object instance = dynamicClass.getConstructor().newInstance();
 *
 * Method setName = dynamicClass.getMethod("setName", String.class);
 * setName.invoke(instance, "John");
 * Method greet = dynamicClass.getMethod("greet");
 * String greeting = (String) greet.invoke(instance);
 * System.out.println(greeting);  // Output: Hello, John!
 * }</pre>
 */
public class DynamicTypeBuilder {
    private final ClassPool pool;
    private final CtClass ctClass;

    public DynamicTypeBuilder(String className) throws NotFoundException {
        pool = ClassPool.getDefault();
        ctClass = pool.makeClass(className);
    }

    public void addField(String fieldName, Class<?> fieldType) throws CannotCompileException, NotFoundException {
        CtField field = new CtField(resolveCtClass(fieldType), fieldName, ctClass);
        ctClass.addField(field);

        // Add getter
        String getterName = "get" + capitalize(fieldName);
        CtMethod getter = CtNewMethod.getter(getterName, field);
        ctClass.addMethod(getter);

        // Add setter
        String setterName = "set" + capitalize(fieldName);
        CtMethod setter = CtNewMethod.setter(setterName, field);
        ctClass.addMethod(setter);
    }

    public void addMethod(String methodName, Class<?> returnType, String methodBody, Class<?>... parameterTypes) throws CannotCompileException, NotFoundException {
        StringBuilder methodCode = new StringBuilder();
        methodCode.append("public ").append(resolveCtClass(returnType).getName()).append(" ").append(methodName).append("(");

        for (int i = 0; i < parameterTypes.length; i++) {
            methodCode.append(resolveCtClass(parameterTypes[i]).getName()).append(" arg").append(i);
            if (i < parameterTypes.length - 1) {
                methodCode.append(", ");
            }
        }

        methodCode.append(") { ").append(methodBody).append(" }");

        System.out.println("Adding method: " + methodCode); // Debugging line

        CtMethod method = CtNewMethod.make(methodCode.toString(), ctClass);
        ctClass.addMethod(method);

        // Verify that the method is added
        CtMethod[] methods = ctClass.getDeclaredMethods();
        System.out.println("Methods in " + ctClass.getName() + ":");
        for (CtMethod m : methods) {
            System.out.println(m.getName());
        }
    }

    public Class<?> build() throws CannotCompileException {
        return ctClass.toClass();
    }

    private CtClass resolveCtClass(Class<?> clazz) throws NotFoundException {
        return pool.get(clazz.getName());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
