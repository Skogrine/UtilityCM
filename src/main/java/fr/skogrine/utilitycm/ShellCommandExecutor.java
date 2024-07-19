package fr.skogrine.utilitycm;

import fr.skogrine.utilitycm.annotation.NotFinished;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

/**
 * ShellCommandExecutor is a utility class that allows Java developers to execute shell commands
 * in a platform-independent manner. It provides synchronous and asynchronous execution methods,
 * captures output, and handles errors gracefully.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ShellCommandExecutor executor = new ShellCommandExecutor();
 * String result = executor.execute("echo Hello, World!");
 * System.out.println(result);
 *
 * executor.executeAsync("ping -c 4 google.com")
 *         .thenAccept(output -> System.out.println("Async Result: " + output))
 *         .exceptionally(ex -> { System.err.println("Error: " + ex.getMessage()); return null; });
 * }</pre>
 */
@NotFinished
public class ShellCommandExecutor {
    /**
     * Executes a shell command synchronously and returns the output.
     *
     * @param command the shell command to execute
     * @return the output of the command
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the execution is interrupted
     */
    public String execute(String command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        builder.redirectErrorStream(true);
        Process process = builder.start();
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }
        }
        process.waitFor();
        return result.toString();
    }

    /**
     * Executes a shell command asynchronously and returns a CompletableFuture with the output.
     *
     * @param command the shell command to execute
     * @return a CompletableFuture containing the output of the command
     */
    public CompletableFuture<String> executeAsync(String command) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(command);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Failed to execute command: " + command, e);
            }
        });
    }


}
