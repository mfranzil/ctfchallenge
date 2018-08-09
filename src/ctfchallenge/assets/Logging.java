package ctfchallenge.assets;

import javafx.scene.control.TextArea;

import java.sql.Timestamp;

/**
 * @author Matteo Franzil
 * @version 1.1
 */
public class Logging extends TextArea {

    private static Logging INSTANCE;

    private Logging() {
        setEditable(false);
    }

    /**
     * @return L'unica istanza della classe Logging (una TextArea)
     */
    public static Logging getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logging();
        }
        return INSTANCE;
    }

    /**
     * Logga una stringa con livello DEBUG.
     *
     * @param text La string da mettere nella finestra di log.
     */
    public static void debug(String text) {
        getInstance().appendText("[DEBUG] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logga una strina con livello INFO.
     *
     * @param text La string da mettere nella finestra di log.
     */
    public static void info(String text) {
        getInstance().appendText("[INFO] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logga una string con livello WARNING
     *
     * @param text La string da mettere nella finestra di log.
     */
    public static void warning(String text) {
        getInstance().appendText("[WARNING] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logga una string con livello FATAL.
     *
     * @param text La string da mettere nella finestra di log.
     */
    public static void fatal(String text) {
        getInstance().appendText("[FATAL] " + getTime() + " - " + text + "\n");
    }

    /**
     * Logga una string con livello ERROR.
     *
     * @param text La string da mettere nella finestra di log.
     */
    public static void error(String text) {
        getInstance().appendText("[ERROR] " + getTime() + " - " + text + "\n");
    }

    private static String getTime() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    /**
     * @return L'intero contenuto della finestra di log.
     */
    public static String getLog() {
        return getInstance().getText();
    }

}
