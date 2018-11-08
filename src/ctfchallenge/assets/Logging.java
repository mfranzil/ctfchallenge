package ctfchallenge.assets;

import javafx.scene.control.TextArea;

import java.sql.Timestamp;

/**
 * @author Matteo Franzil
 * @version 20181108v1
 */
public class Logging extends TextArea {

    private static Logging INSTANCE;

    private Logging() {
        setEditable(false);
    }

    /**
     * @return The only instance of the Logging class (a JavaFX {@link #TextArea})
     */
    public static Logging getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logging();
        }
        return INSTANCE;
    }

    /**
     * Logs a String with level DEBUG.
     *
     * @param text The string to be logged.
     */
    public static void debug(String text) {
        getInstance().appendText("[DEBUG] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logs a String with level INFO.
     *
     * @param text The string to be logged.
     */
    public static void info(String text) {
        getInstance().appendText("[INFO] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logs a String with level WARNING
     *
     * @param text The string to be logged.
     */
    public static void warning(String text) {
        getInstance().appendText("[WARNING] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logs a String with level ERROR.
     *
     * @param text The string to be logged.
     */
    public static void error(String text) {
        getInstance().appendText("[ERROR] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logs a String with level FATAL.
     *
     * @param text The string to be logged.
     */
    public static void fatal(String text) {
        getInstance().appendText("[FATAL] " + getTime() + " - " + text + "\n");
    }
    private static String getTime() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * Returns a String containing the entire log.
     *
     * @return The entire content of the log window for this instance.
     */
    public static String getLog() {
        return getInstance().getText();
    }

}
