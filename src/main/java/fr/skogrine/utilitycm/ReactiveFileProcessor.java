package fr.skogrine.utilitycm;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class for reactive and asynchronous file processing using Java NIO.
 * This class provides a functional API to read, write, and process files in a non-blocking manner.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ReactiveFileProcessor fileProcessor = new ReactiveFileProcessor();
 *
 * // Asynchronously read a file and process its content
 * fileProcessor.readFile("example.txt")
 *     .thenApply(content -> "File content: " + content)
 *     .thenAccept(System.out::println)
 *     .exceptionally(e -> {
 *         System.err.println("Failed to read file: " + e.getMessage());
 *         return null;
 *     });
 *
 * // Asynchronously write data to a file
 * fileProcessor.writeFile("output.txt", "Hello, world!")
 *     .thenRun(() -> System.out.println("Write completed"))
 *     .exceptionally(e -> {
 *         System.err.println("Failed to write file: " + e.getMessage());
 *         return null;
 *     });
 *
 * // Asynchronously process files in a directory
 * fileProcessor.processFilesInDirectory("mydir", file -> {
 *     // Process each file
 *     System.out.println("Processing file: " + file.getFileName());
 *     return CompletableFuture.completedFuture(null);
 * }).exceptionally(e -> {
 *     System.err.println("Failed to process files: " + e.getMessage());
 *     return null;
 * });
 * }</pre>
 */
public class ReactiveFileProcessor {
    /**
     * Reads the content of a file asynchronously.
     *
     * @param filePath the path to the file
     * @return a CompletableFuture with the file content as a String
     */
    public CompletableFuture<String> readFile(String filePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(Path.of(filePath));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read file", e);
            }
        });
    }

    /**
     * Writes data to a file asynchronously.
     *
     * @param filePath the path to the file
     * @param data the data to write
     * @return a CompletableFuture indicating the completion of the write operation
     */
    public CompletableFuture<Void> writeFile(String filePath, String data) {
        return CompletableFuture.runAsync(() -> {
            try {
                Files.writeString(Path.of(filePath), data);
            } catch (IOException e) {
                throw new RuntimeException("Failed to write file", e);
            }
        });
    }

    /**
     * Processes files in a directory asynchronously.
     *
     * @param directoryPath the path to the directory
     * @param processor a function that processes each file and returns a CompletableFuture
     * @return a CompletableFuture that completes when all files are processed
     */
    public CompletableFuture<Void> processFilesInDirectory(String directoryPath, Function<Path, CompletableFuture<Void>> processor) {
        return CompletableFuture.supplyAsync(() -> {
            try (Stream<Path> paths = Files.walk(Path.of(directoryPath))) {
                List<CompletableFuture<Void>> futures = paths
                        .filter(Files::isRegularFile)
                        .map(processor)
                        .collect(Collectors.toList());
                return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            } catch (IOException e) {
                throw new RuntimeException("Failed to process directory", e);
            }
        }).thenCompose(Function.identity());
    }

    /**
     * Processes a file with the given processor function.
     *
     * @param filePath the path to the file
     * @param processor a function that processes the file and returns a CompletableFuture
     * @return a CompletableFuture indicating the completion of the processing
     */
    public CompletableFuture<Void> processFile(String filePath, Function<Path, CompletableFuture<Void>> processor) {
        return processor.apply(Path.of(filePath));
    }

    /**
     * Walks through a directory asynchronously, visiting each file and directory.
     *
     * @param directoryPath the path to the directory
     * @param visitor a visitor function that processes each file and directory
     * @return a CompletableFuture indicating the completion of the walk
     */
    public CompletableFuture<Void> walkDirectory(String directoryPath, Consumer<Path> visitor) {
        return CompletableFuture.runAsync(() -> {
            try {
                Files.walkFileTree(Path.of(directoryPath), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        visitor.accept(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                        visitor.accept(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException("Failed to walk directory", e);
            }
        });
    }
}
