package ctfchallenge.assets;

import ctfchallenge.Team;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * @author Matteo Franzil
 * @version 20181108v1
 */
public class BackupHandler {

    /**
     * Logs both the program status and the logging window text.
     *
     * @param teamList The teamList.
     */
    public static void log(TeamList teamList) {
        backupData(teamList);
        standardLog();
    }

    /**
     * Dumps the logging window text to a file called "log.txt".
     */
    private static void standardLog() {
        BufferedWriter fileOut;
        try {
            fileOut = new BufferedWriter(new FileWriter("log.txt"));
            fileOut.write(Logging.getLog());
            fileOut.close();
            Logging.info("Log window saved to log.txt.");
        } catch (IOException e) {
            Logging.fatal("IOException occurred. Failed to log data. ");
        }
    }

    /**
     * This method dumps a JSON file named backup.json in the root folder, containing
     * the program state at the moment of backup. It's dumped automatically at each teamList update.     *
     *
     * @param teamList The ObservableList of teams.
     */
    private static void backupData(TeamList teamList) {
        JSONObject json = new JSONObject();
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("backup.json"));
            Logging.info("Backup in progress...");
            int i = 0;
            for (Team temp : teamList) {
                JSONObject tmp = new JSONObject();
                json.put(++i, tmp);
                tmp.put("Name", temp.getName());
                tmp.put("Player1", temp.getPlayer1());
                tmp.put("Player2", temp.getPlayer2());
                tmp.put("Player3", temp.getPlayer3());
                tmp.put("Score", temp.getScore());
            }
            StringWriter out = new StringWriter();
            json.writeJSONString(out);

            fileOut.write(out.getBuffer().toString());
            fileOut.write("\n");
            fileOut.close();
            Logging.info("Backup finished.");
        } catch (IOException ex) {
            Logging.fatal("IOException occurred. Failed to back up data. ");
        }
    }

    /**
     * Method that calls a JavaFX FileChooser to pick a JSON backup file to restore data from.
     *
     * @param teamList The ObservableList of teams.
     */
    public static boolean restoreData(TeamList teamList) {
        boolean res = false;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload a file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON", "*.json")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                Logging.warning("No file chosen!");
            } else {
                teamList.clear();

                JSONObject teams = (JSONObject) new JSONParser().parse(new FileReader(file));

                for (Object a : teams.values()) {
                    String teamName = (String) ((JSONObject) a).get("Name");
                    String player1 = (String) ((JSONObject) a).get("Player1");
                    String player2 = (String) ((JSONObject) a).get("Player2");
                    String player3 = (String) ((JSONObject) a).get("Player3");
                    int score = ((Long) ((JSONObject) a).get("Score")).intValue();

                    Team temp = new Team(teamName, player1, player2, player3, score);
                    teamList.add(temp);
                    Logging.info("Restored successfully: " + teamName + "\nMembers: "
                            + player1 + " " + player2 + "\nPoints: " + score);
                    res = true;
                }
                Logging.info("Backup successfully recovered.");
            }
        } catch (FileNotFoundException ex) {
            Logging.fatal("Backup file not found. Cannot recover from backup.");
        } catch (ParseException e) {
            e.printStackTrace();
            Logging.fatal("File is corrupted or has bad syntax.");
        } catch (IOException e) {
            Logging.fatal("Error while parsing file. Please try again.");
            e.printStackTrace();
        }
        return res;
    }
}
