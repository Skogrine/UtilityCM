package fr.skogrine.utilitycm;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * DependencyInjector is a lightweight dependency injection framework that simplifies the management of object creation and dependency resolution.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * DependencyInjector di = new DependencyInjector();
 * di.register(Service.class, ServiceImpl::new);
 * di.register(Repository.class, RepositoryImpl::new);
 *
 * Service service = di.get(Service.class);
 * service.performAction();
 * }</pre>
 */
public class DependencyInjector {

    private final Map<Class<?>, Supplier<?>> registry = new HashMap<>();

    /**
     * Registers a supplier for a given class type.
     *
     * @param <T> the type of the class
     * @param clazz the class to register
     * @param supplier the supplier that provides instances of the class
     */
    public <T> void register(Class<T> clazz, Supplier<T> supplier) {
        registry.put(clazz, supplier);
    }

    /**
     * Gets an instance of the specified class type.
     *
     * @param <T> the type of the class
     * @param clazz the class to get
     * @return an instance of the class
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        Supplier<?> supplier = registry.get(clazz);
        if (supplier == null) {
            throw new IllegalArgumentException("No supplier registered for class: " + clazz.getName());
        }
        return (T) supplier.get();
    }

    public static void main(String[] args) {
        DependencyInjector di = new DependencyInjector();
        di.register(Service.class, ServiceImpl::new);
        di.register(Repository.class, RepositoryImpl::new);

        Service service = di.get(Service.class);
        service.performAction();
    }
}

interface Service {
    void performAction();
}

class ServiceImpl implements Service {
    private final Repository repository;

    public ServiceImpl() {
        DependencyInjector di = new DependencyInjector();
        this.repository = di.get(Repository.class);
    }

    @Override
    public void performAction() {
        System.out.println("Service is performing an action...");
        repository.doSomething();
    }
}

interface Repository {
    void doSomething();
}

class RepositoryImpl implements Repository {
    @Override
    public void doSomething() {
        System.out.println("Repository is doing something...");
    }
}
