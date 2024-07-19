package fr.skogrine.utilitycm.annotation;

import java.lang.reflect.Method;

/**
 * Utility class to handle warnings for methods annotated with @NotFinished.
 */
public class WarningUtil {

    /**
     * Checks if the specified class or method is annotated with @NotFinished.
     * If so, prints a warning to the console.
     *
     * @param clazz the class to check
     */
    public static void warnIfNotFinished(Class<?> clazz) {
        if (clazz.isAnnotationPresent(NotFinished.class)) {
            System.out.println("WARNING: Class " + clazz.getName() + " is annotated with @NotFinished. It may not work as expected.");
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(NotFinished.class)) {
                System.out.println("WARNING: Method " + method.getName() + " in class " + clazz.getName() + " is annotated with @NotFinished. It may not work as expected.");
            }
        }
    }
}