package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DependencyInjectorTest {
    @Test
    void testRegisterAndResolve() {
        DependencyInjector injector = new DependencyInjector();

        class SampleService {
            String getMessage() {
                return "Hello, Dependency Injection!";
            }
        }

        injector.register(SampleService.class, SampleService::new);
        SampleService service = injector.get(SampleService.class);
        assertNotNull(service, "The resolved service should not be null");
        assertEquals("Hello, Dependency Injection!", service.getMessage(), "The message from the service should match the expected value");
    }

    @Test
    void testResolveWithoutRegistration() {
        DependencyInjector injector = new DependencyInjector();

        // Attempt to resolve a service that has not been registered
        Exception exception = assertThrows(IllegalArgumentException.class, () -> injector.get(String.class));

        assertEquals("No supplier registered for class: java.lang.String", exception.getMessage(), "The exception message should indicate that the type is not registered");
    }

    @Test
    void testMultipleRegistrations() {
        DependencyInjector injector = new DependencyInjector();

        // Define multiple services
        class FirstService {
            String getName() {
                return "FirstService";
            }
        }

        class SecondService {
            String getName() {
                return "SecondService";
            }
        }

        // Register both services
        injector.register(FirstService.class, FirstService::new);
        injector.register(SecondService.class, SecondService::new);

        // Resolve and verify both services
        FirstService firstService = injector.get(FirstService.class);
        assertNotNull(firstService, "The resolved FirstService should not be null");
        assertEquals("FirstService", firstService.getName(), "The name from FirstService should match the expected value");

        SecondService secondService = injector.get(SecondService.class);
        assertNotNull(secondService, "The resolved SecondService should not be null");
        assertEquals("SecondService", secondService.getName(), "The name from SecondService should match the expected value");
    }
}
