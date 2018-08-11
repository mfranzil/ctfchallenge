package ctfchallenge.assets;

import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * @author Matteo Franzil
 * @version 1.2
 */
public class AccentParser {
    /**
     * This method returns Windows 10's current accent color, or grey in any other OS.
     *
     * @return A Color with Opacity 1.
     */
    public static Color getAccentColor() {
        Color output;
        String value = null;
        try {
            value = new AccentParser().readRegistry("HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\DWM",
                    "ColorizationColor");
        } catch (@NotNull IOException | InterruptedException e) {
            e.printStackTrace();
        }

        assert value != null;

        if (value.length() == 10) {
            value = value.substring(4);
        } else {
            value = value.substring(2);
        }

        int r = Integer.valueOf(value.substring(0, 2), 16);
        int g = Integer.valueOf(value.substring(2, 4), 16);
        int b = Integer.valueOf(value.substring(4, 6), 16);
        //     int a = Integer.valueOf(value.substring(6, 8), 16);

        if (System.getProperty("os.name").equals("Windows 10")) {
            output = Color.rgb(r, g, b, 1);
        } else {
            output = Color.rgb(10, 10, 10);
        }

        return output;
    }

    /**
     * This method returns Windows 10's current accent color, or grey in any other OS.
     *
     * @return A Color with Opacity 0.5.
     */
    public static Color getLightAccentColor() {
        Color color = getAccentColor();
        return Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.33);
    }

    /**
     * @param location The registry location to read.
     * @param key      The registry key to read.
     * @return registry The value found, or null.
     * @throws IOException Thrown in case of I/O errors.
     * @throws InterruptedException Thrown if the process is killed by the user.
     */
    @Nullable
    public String readRegistry(String location, String key) throws IOException, InterruptedException {
        // Run reg query, then read output with StreamReader (internal class)
        Process process = Runtime.getRuntime().exec("reg query " +
                '"' + location + "\" /v " + key);

        StreamReader reader = new StreamReader(process.getInputStream());
        reader.start();
        process.waitFor();
        reader.join();

        // Parse out the value
        String[] parsed = reader.getResult().split("\\s+");
        if (parsed.length > 1) {
            return parsed[parsed.length - 1];
        }
        return null;
    }

    class StreamReader extends Thread {
        private InputStream is;
        @NotNull
        private StringWriter sw = new StringWriter();

        public StreamReader(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                int c;
                while ((c = is.read()) != -1)
                    sw.write(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getResult() {
            return sw.toString();
        }
    }
}
