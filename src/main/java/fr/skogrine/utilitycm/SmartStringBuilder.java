package fr.skogrine.utilitycm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * SmartStringBuilder enhances the traditional StringBuilder with additional functionality.
 * It supports template processing, undo/redo operations, and provides text statistics.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SmartStringBuilder ssb = new SmartStringBuilder();
 * ssb.append("Hello, world!");
 * System.out.println(ssb.toString()); // Output: Hello, world!
 *
 * ssb.undo();
 * System.out.println(ssb.toString()); // Output:
 *
 * ssb.redo();
 * System.out.println(ssb.toString()); // Output: Hello, world!
 *
 * ssb.append(" This is a SmartStringBuilder.");
 * System.out.println(ssb.wordCount()); // Output: 5
 * System.out.println(ssb.charCount()); // Output: 37
 * System.out.println(ssb.lineCount()); // Output: 1
 *
 * Map<String, String> placeholders = Map.of("name", "Alice", "action", "coding");
 * String template = "Hello, {{name}}! Welcome to {{action}}.";
 * System.out.println(ssb.processTemplate(template, placeholders)); // Output: Hello, Alice! Welcome to coding.
 * }</pre>
 */
public class SmartStringBuilder {
    private final StringBuilder builder;
    private final Deque<String> history;
    private final Deque<String> redoStack;

    /**
     * Constructs an empty SmartStringBuilder.
     */
    public SmartStringBuilder() {
        this.builder = new StringBuilder();
        this.history = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
    }

    /**
     * Appends the specified string to this builder.
     *
     * @param str the string to append
     * @return this builder
     */
    public SmartStringBuilder append(String str) {
        saveState();
        builder.append(str);
        return this;
    }

    /**
     * Inserts the specified string at the specified position.
     *
     * @param offset the offset position
     * @param str the string to insert
     * @return this builder
     */
    public SmartStringBuilder insert(int offset, String str) {
        saveState();
        builder.insert(offset, str);
        return this;
    }

    /**
     * Replaces the text in this builder with the specified string.
     *
     * @param start the start index
     * @param end the end index
     * @param str the string to replace with
     * @return this builder
     */
    public SmartStringBuilder replace(int start, int end, String str) {
        saveState();
        builder.replace(start, end, str);
        return this;
    }

    /**
     * Reverses the sequence of characters in this builder.
     *
     * @return this builder
     */
    public SmartStringBuilder reverse() {
        saveState();
        builder.reverse();
        return this;
    }

    /**
     * Returns the length of this builder.
     *
     * @return the length
     */
    public int length() {
        return builder.length();
    }

    /**
     * Processes a template by replacing placeholders with corresponding values.
     *
     * @param template the template string
     * @param placeholders the placeholders and their values
     * @return the processed string
     */
    public String processTemplate(String template, Map<String, String> placeholders) {
        String result = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }

    /**
     * Undoes the last operation.
     *
     * @return true if the undo was successful, false otherwise
     */
    public boolean undo() {
        if (!history.isEmpty()) {
            redoStack.push(builder.toString());
            builder.setLength(0);
            builder.append(history.pop());
            return true;
        }
        return false;
    }

    /**
     * Redoes the last undone operation.
     *
     * @return true if the redo was successful, false otherwise
     */
    public boolean redo() {
        if (!redoStack.isEmpty()) {
            history.push(builder.toString());
            builder.setLength(0);
            builder.append(redoStack.pop());
            return true;
        }
        return false;
    }

    /**
     * Returns the number of words in this builder.
     *
     * @return the word count
     */
    public int wordCount() {
        String content = builder.toString().trim();
        if (content.isEmpty()) {
            return 0;
        }
        return content.split("\\s+").length;
    }

    /**
     * Returns the number of characters in this builder.
     *
     * @return the character count
     */
    public int charCount() {
        return builder.length();
    }

    /**
     * Returns the number of lines in this builder.
     *
     * @return the line count
     */
    public int lineCount() {
        String content = builder.toString();
        if (content.isEmpty()) {
            return 0;
        }
        return content.split("\r\n|\r|\n").length;
    }

    /**
     * Returns the string representation of this builder.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * Saves the current state of the builder for undo functionality.
     */
    private void saveState() {
        history.push(builder.toString());
        redoStack.clear();
    }
}
