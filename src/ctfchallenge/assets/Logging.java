package ctfchallenge.assets;

import javafx.scene.control.TextArea;

import java.sql.Timestamp;

public class Logging extends TextArea {

    private static Logging INSTANCE;

    private Logging() {
        setEditable(false);
    }

    public static Logging getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logging();
        }
        return INSTANCE;
    }

    public static void debug(String text) {
        getInstance().appendText("[DEBUG] " + getTime() + " - " + text + "\n");
    }

    public static void info(String text) {
        getInstance().appendText("[INFO] " + getTime() + " - " + text + "\n");
    }

    public static void warning(String text) {
        getInstance().appendText("[WARNING] " + getTime() + " - " + text + "\n");
    }

    public static void fatal(String text) {
        getInstance().appendText("[FATAL] " + getTime() + " - " + text + "\n");
    }

    public static void error(String text) {
        getInstance().appendText("[ERROR] " + getTime() + " - " + text + "\n");
    }

    private static String getTime() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static String getLog() {
        return getInstance().getText();
    }

}
