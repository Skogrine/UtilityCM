package fr.skogrine.utilitycm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StylishLogger {

    // ANSI escape codes for colors
    private static final String RESET = "\033[0m";
    private static final String BLACK_BG = "\033[40m";
    private static final String RED_BG = "\033[41m";
    private static final String GREEN_BG = "\033[42m";
    private static final String YELLOW_BG = "\033[43m";
    private static final String BLUE_BG = "\033[44m";
    private static final String MAGENTA_BG = "\033[45m";
    private static final String CYAN_BG = "\033[46m";
    private static final String WHITE_BG = "\033[47m";

    private static final String BLACK_FG = "\033[30m";
    private static final String WHITE_FG = "\033[37m";
    private static final String BOLD = "\033[1m";


    private enum LogLevel {
        INFO(BLUE_BG),
        SUCCESS(GREEN_BG),
        WARNING(YELLOW_BG),
        ERROR(RED_BG);

        private final String backgroundColor;

        LogLevel(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public String getTextColor() {
            // Determine text color based on background color brightness
            return isBackgroundColorDark(backgroundColor) ? WHITE_FG : BLACK_FG;
        }

        private boolean isBackgroundColorDark(String backgroundColor) {
            // Check if the background color is dark based on known color codes
            return backgroundColor.equals(RED_BG) ||
                    backgroundColor.equals(BLUE_BG) ||
                    backgroundColor.equals(BLACK_BG);
        }
    }

    /**
     * Logs a message with a colored level background and appropriate text color.
     *
     * @param level   The log level.
     * @param message The message to log.
     */
    public static void log(LogLevel level, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String levelText = level.name();

        String levelFormatted = level.getBackgroundColor() + level.getTextColor() + BOLD +
                String.format("[%-7s]", levelText) + RESET;
        System.out.println(levelFormatted + " [" + timestamp + "] : " + message);
    }

    public static void main(String[] args) {
        // Example usage
        StylishLogger.log(LogLevel.INFO, "This is an info message");
        StylishLogger.log(LogLevel.SUCCESS, "This is a success message");
        StylishLogger.log(LogLevel.WARNING, "This is a warning message");
        StylishLogger.log(LogLevel.ERROR, "This is an error message");
    }
}
