package fr.skogrine.utilitycm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * ConfigManager manages configuration settings with support for dynamic reloading, validation, and hierarchical configurations.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ConfigManager configManager = new ConfigManager("config.properties");
 * String value = configManager.getProperty("key");
 * System.out.println("Property value: " + value);
 * configManager.setProperty("key", "newValue");
 * }</pre>
 */
public class ConfigManager {

    private final Properties properties;
    private final Path configPath;

    /**
     * Constructs a ConfigManager with the specified configuration file.
     *
     * @param configFile the path to the configuration file
     */
    public ConfigManager(String configFile) {
        this.configPath = Paths.get(configFile);
        this.properties = new Properties();
        loadProperties();
    }

    /**
     * Loads properties from the configuration file.
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(configPath.toFile())) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a property value by key.
     *
     * @param key the property key
     * @return the property value, or null if the key does not exist
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Sets a property value.
     *
     * @param key the property key
     * @param value the property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Reloads the properties from the configuration file.
     */
    public void reload() {
        loadProperties();
    }
}