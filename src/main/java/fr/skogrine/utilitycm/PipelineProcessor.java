package fr.skogrine.utilitycm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * PipelineProcessor allows creating and executing a series of processing stages, where data flows through each stage.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * PipelineProcessor<String, String> processor = new PipelineProcessor<>();
 * processor.addStage(String::trim)
 *          .addStage(String::toUpperCase)
 *          .addStage(s -> "Processed: " + s);
 *
 * String result = processor.process("  hello world  ");
 * System.out.println(result); // Output: Processed: HELLO WORLD
 * }</pre>
 */
public class PipelineProcessor<T, R> {
    private final List<Function<T, T>> stages;

    /**
     * Constructs an empty PipelineProcessor.
     */
    public PipelineProcessor() {
        this.stages = new ArrayList<>();
    }

    /**
     * Adds a new processing stage to the pipeline.
     *
     * @param stage the processing stage
     * @return this pipeline processor
     */
    public PipelineProcessor<T, R> addStage(Function<T, T> stage) {
        stages.add(stage);
        return this;
    }

    /**
     * Processes the input through all the stages in the pipeline.
     *
     * @param input the input to process
     * @return the processed output
     */
    public T process(T input) {
        T result = input;
        for (Function<T, T> stage : stages) {
            result = stage.apply(result);
        }
        return result;
    }

}
