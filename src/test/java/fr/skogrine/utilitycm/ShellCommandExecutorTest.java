package fr.skogrine.utilitycm;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class ShellCommandExecutorTest {

    @Test
    void testExecuteSync() {
        ShellCommandExecutor executor = new ShellCommandExecutor();
        String command = "echo Hello, World!";
        try {
            String result = executor.execute(command);
            assertTrue(result.contains("Hello, World!"), "The output should contain 'Hello, World!'");
        } catch (IOException | InterruptedException e) {
            fail("Execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    void testExecuteAsync() {
        ShellCommandExecutor executor = new ShellCommandExecutor();
        String command = "echo Hello, World!";
        CompletableFuture<String> future = executor.executeAsync(command);

        try {
            String result = future.get();
            assertTrue(result.contains("Hello, World!"), "The output should contain 'Hello, World!'");
        } catch (InterruptedException | ExecutionException e) {
            fail("Async execution should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    void testExecuteSyncInvalidCommand() {
        ShellCommandExecutor executor = new ShellCommandExecutor();
        String command = "invalidcommand";
        try {
            String result = executor.execute(command);
            assertFalse(result.isEmpty(), "The output should be empty for an invalid command");
        } catch (IOException | InterruptedException e) {
            assertInstanceOf(IOException.class, e, "IOException expected for invalid command");
        }
    }

    @Test
    void testExecuteAsyncInvalidCommand() {
        ShellCommandExecutor executor = new ShellCommandExecutor();
        String command = "invalidcommand";
        CompletableFuture<String> future = executor.executeAsync(command);

        try {
            future.get();
            fail("Async execution of an invalid command should throw an exception");
        } catch (InterruptedException | ExecutionException e) {
            assertInstanceOf(IOException.class, e.getCause(), "IOException expected for invalid command");
        }
    }

}
