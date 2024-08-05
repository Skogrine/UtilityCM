package fr.skogrine.utilitycm;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * A utility class for generating JSON, YAML, and script files (shell/batch).
 */
public class FileGenerator {

    public static class Builder {
        private Map<String, Object> data;
        private String filePath;
        private FileType fileType;
        private String scriptContent;

        /**
         * Sets the data for JSON or YAML file generation.
         *
         * @param data The data to be converted to JSON or YAML.
         * @return The current Builder instance.
         */
        public Builder setData(Map<String, Object> data) {
            this.data = Objects.requireNonNull(data, "Data cannot be null");
            return this;
        }

        /**
         * Sets the file path where the generated file will be saved.
         *
         * @param filePath The file path.
         * @return The current Builder instance.
         */
        public Builder setFilePath(String filePath) {
            this.filePath = Objects.requireNonNull(filePath, "File path cannot be null");
            return this;
        }

        /**
         * Sets the type of file to be generated.
         *
         * @param fileType The type of file (JSON, YAML, SHELL, BATCH).
         * @return The current Builder instance.
         */
        public Builder setFileType(FileType fileType) {
            this.fileType = Objects.requireNonNull(fileType, "File type cannot be null");
            return this;
        }

        /**
         * Sets the content for script file generation.
         *
         * @param scriptContent The content of the shell or batch script.
         * @return The current Builder instance.
         */
        public Builder setScriptContent(String scriptContent) {
            this.scriptContent = Objects.requireNonNull(scriptContent, "Script content cannot be null");
            return this;
        }

        /**
         * Builds and returns a FileGenerator instance with the specified properties.
         *
         * @return A new FileGenerator instance.
         * @throws IllegalStateException if required fields are missing
         */
        public FileGenerator build() {
            if (fileType == FileType.JSON || fileType == FileType.YAML) {
                if (data == null) {
                    throw new IllegalStateException("Data is required for JSON or YAML file generation.");
                }
            }
            if (fileType == FileType.SHELL || fileType == FileType.BATCH) {
                if (scriptContent == null) {
                    throw new IllegalStateException("Script content is required for Shell or Batch file generation.");
                }
            }
            return new FileGenerator(data, filePath, fileType, scriptContent);
        }
    }

    /**
     * Enum representing the type of file to be generated.
     */
    public enum FileType {
        JSON, YAML, SHELL, BATCH
    }

    private final Map<String, Object> data;
    private final String filePath;
    private final FileType fileType;
    private final String scriptContent;

    /**
     * Private constructor for FileGenerator.
     *
     * @param data          The data for JSON or YAML file generation.
     * @param filePath      The file path where the generated file will be saved.
     * @param fileType      The type of file to be generated.
     * @param scriptContent The content for script file generation.
     */
    private FileGenerator(Map<String, Object> data, String filePath, FileType fileType, String scriptContent) {
        this.data = data;
        this.filePath = filePath;
        this.fileType = fileType;
        this.scriptContent = scriptContent;
    }

    /**
     * Generates the file based on the specified file type.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void generateFile() throws IOException {
        switch (fileType) {
            case JSON:
                generateJsonFile();
                break;
            case YAML:
                generateYamlFile();
                break;
            case SHELL:
                generateShellScript();
                break;
            case BATCH:
                generateBatchScript();
                break;
        }
    }

    private void generateJsonFile() throws IOException {
        String jsonContent = convertToJson(data);
        writeToFile(jsonContent);
    }

    private void generateYamlFile() throws IOException {
        String yamlContent = convertToYaml(data);
        writeToFile(yamlContent);
    }

    private void generateShellScript() throws IOException {
        validateScriptContent(scriptContent);
        writeToFile(scriptContent);
    }

    private void generateBatchScript() throws IOException {
        validateScriptContent(scriptContent);
        writeToFile(scriptContent);
    }

    private void writeToFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    private String convertToJson(Map<String, Object> data) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            json.append("  \"").append(entry.getKey()).append("\": ");
            appendValue(json, entry.getValue());
            json.append(",\n");
        }
        if (json.length() > 2) {
            json.setLength(json.length() - 2);
        }
        json.append("\n}");
        return json.toString();
    }

    private String convertToYaml(Map<String, Object> data) {
        StringBuilder yaml = new StringBuilder();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            yaml.append(entry.getKey()).append(": ");
            appendValue(yaml, entry.getValue());
            yaml.append("\n");
        }
        return yaml.toString();
    }

    private void appendValue(StringBuilder builder, Object value) {
        if (value instanceof String) {
            builder.append("\"").append(value).append("\"");
        } else if (value instanceof Map<?, ?> map) {
            builder.append("{");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                builder.append("\"").append(entry.getKey()).append("\": ");
                appendValue(builder, entry.getValue());
                builder.append(", ");
            }
            if (builder.charAt(builder.length() - 2) == ',') {
                builder.setLength(builder.length() - 2);
            }
            builder.append("}");
        } else {
            builder.append(value);
        }
    }

    private void validateScriptContent(String scriptContent) {
        if (scriptContent == null || scriptContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Script content cannot be null or empty.");
        }
    }
}
