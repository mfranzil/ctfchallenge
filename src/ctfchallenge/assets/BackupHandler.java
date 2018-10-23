package ctfchallenge.assets;

import ctfchallenge.Team;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

public class BackupHandler {

    public static void log(TeamList teamList) {
        backupData(teamList);
        standardLog();
    }

    public static void standardLog() {
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
     * Metodo che fa un backup dei dati su backup.txt ogni volta che viene
     * chiamato sovrascrivendo il precedente.
     *
     * @param teamList Il gestore interno delle squadre.
     */
    public static void backupData(TeamList teamList) {
        JSONObject json = new JSONObject();
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("backup.txt"));
            Logging.info("Backup in progress...");
            for (Team temp : teamList) {
                JSONObject tmp = new JSONObject();
                json.put(temp.getName(), tmp);
                tmp.put("Name", temp.getName());
                tmp.put("Player1", temp.getPlayer1());
                tmp.put("Player2", temp.getPlayer2());
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
     * Metodo che evoca un FileChooser per riprendere il gioco da un backup precedente.
     *
     * @param teamList Il gestore interno delle squadre.
     */
    public static boolean restoreData(TeamList teamList) {
        boolean res = false;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload a file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                Logging.warning("No file chosen!");
            } else {
                teamList.clear();
                String jsonContent = new Scanner(file).nextLine();
                JSONObject teams = (JSONObject) new JSONParser().parse(jsonContent);

                for (Object a : teams.values()) {
                    String nomesquadra = (String) ((JSONObject)a).get("Name");
                    String membro1 = (String) ((JSONObject)a).get("Player1");
                    String membro2 = (String) ((JSONObject)a).get("Player2");
                    Integer punteggio = ((Long) ((JSONObject)a).get("Score")).intValue();

                    Team temp = new Team(nomesquadra, membro1, membro2, punteggio);
                    teamList.add(temp);
                    Logging.info("Restored successfully: " + nomesquadra + "\nMembers: "
                            + membro1 + " " + membro2 + "\nPoints: " + punteggio);
                    res = true;
                }
                Logging.info("Backup successfully recovered.");
            }
        } catch (FileNotFoundException ex) {
            Logging.fatal("Backup file not found. Cannot recover from backup.");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }
}
