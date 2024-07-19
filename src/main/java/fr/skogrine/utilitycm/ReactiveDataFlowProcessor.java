package fr.skogrine.utilitycm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * A class for creating and managing reactive data processing pipelines.
 *
 * <p>This class allows for the construction of data processing pipelines using a fluent API.
 * It leverages reactive programming principles to handle data in a non-blocking, asynchronous
 * manner. The processor supports adding multiple processing functions and filtering data before
 * processing.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Create a ReactiveDataFlowProcessor instance with a data source and filter
 * ReactiveDataFlowProcessor<String> processor = new ReactiveDataFlowProcessor<>(
 *     () -> {
 *         // Simulate data generation
 *         return "Sample Data " + Math.random();
 *     },
 *     data -> data.contains("Sample") // Example filter to include only "Sample" data
 * );
 *
 * // Add processors to the pipeline
 * processor.addProcessor(data -> data.toUpperCase()) // Convert data to uppercase
 *          .addProcessor(data -> "Processed: " + data) // Prefix data with "Processed: "
 *          .execute(); // Execute the pipeline
 * }</pre>
 *
 * @param <T> the type of data to be processed
 */
public class ReactiveDataFlowProcessor<T> {
    private final List<Function<T, T>> processors = new ArrayList<>();
    private final Predicate<T> filter;
    private final Supplier<T> sourceSupplier;

    /**
     * Constructs a ReactiveDataFlowProcessor with a source supplier and optional filter.
     *
     * @param sourceSupplier the supplier of data to be processed
     * @param filter an optional filter to apply to the data
     */
    public ReactiveDataFlowProcessor(Supplier<T> sourceSupplier, Predicate<T> filter) {
        this.sourceSupplier = sourceSupplier;
        this.filter = filter != null ? filter : t -> true;
    }

    /**
     * Adds a processing function to the data flow pipeline.
     *
     * @param processor the function to apply to each data item
     * @return this ReactiveDataFlowProcessor
     */
    public ReactiveDataFlowProcessor<T> addProcessor(Function<T, T> processor) {
        processors.add(processor);
        return this;
    }

    /**
     * Executes the data processing pipeline and prints the processed results.
     */
    public void execute() {
        SubmissionPublisher<T> publisher = new SubmissionPublisher<>();

        // Create a processor to handle the data flow
        publisher.subscribe(new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T item) {
                if (filter.test(item)) {
                    T processedItem = item;
                    for (Function<T, T> processor : processors) {
                        processedItem = processor.apply(processedItem);
                    }
                    System.out.println(processedItem);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Processing complete.");
            }
        });

        // Supply data to the publisher
        new Thread(() -> {
            while (true) {
                T item = sourceSupplier.get();
                if (item == null) break;
                publisher.submit(item);
            }
            publisher.close();
        }).start();
    }
}
