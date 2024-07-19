package fr.skogrine.utilitycm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ConfigManagerTest {

    @Test
    void testConfigManager() throws IOException {
        String testConfigFile = "test_config.properties";
        Path path = Paths.get(testConfigFile);
        Files.write(path, "key=value".getBytes());

        ConfigManager configManager = new ConfigManager(testConfigFile);
        assertEquals("value", configManager.getProperty("key"));

        configManager.setProperty("key", "newValue");
        assertEquals("newValue", configManager.getProperty("key"));

        configManager.reload();
        Files.delete(path);
    }
}
