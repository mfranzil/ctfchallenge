package ctfchallenge.assets;

import ctfchallenge.Main;
import ctfchallenge.Team;
import ctfchallenge.TeamList;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackupHandler {
    /**
     * Metodo che fa un backup dei dati su backup.txt ogni volta che viene
     * chiamato sovrascrivendo il precedente.
     *
     * @param txt      La finestra di log dei dati.
     * @param teamList Il gestore interno delle squadre.
     */
    public static void backupData(TextArea txt, TeamList teamList) {
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("log.txt"));
            fileOut.write(txt.getText());
            fileOut.close();
            fileOut = new BufferedWriter(new FileWriter("backup.txt"));
            System.out.println("Logging in progress...");
            for (Team temp : teamList) {
                String data = temp.getTeamName() + " TAB " + temp.getMember1() + " TAB "
                        + temp.getMember2() + " TAB " + temp.getScore() + " TAB";
                fileOut.write(data);
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Logging finished.");
        }

    }

    /**
     * Metodo che evoca un FileChooser per riprendere il gioco da un backup precedente.
     *
     * @param txt      La finestra di log del programma.
     * @param teamList Il gestore interno delle squadre.
     */
    public static void restoreData(TextArea txt, TeamList teamList) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carica un file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                System.out.println("No file chosen");
                return;
            } else {
                teamList.clear();
                Scanner stream = new Scanner(file).useDelimiter("\\s*TAB\\s*");
                while (stream.hasNext()) {
                    String nomesquadra = stream.next(), membro1 = stream.next(),
                            membro2 = stream.next(), punteggio = stream.next();
                    Team temp = new Team(nomesquadra, membro1, membro2, Integer.parseInt(punteggio));
                    teamList.add(temp);
                }
            }
            txt.appendText("Backup ripristinato con successo.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "File di backup non trovato.", ex);
            txt.appendText("Impossibile ripristinare il backup");
        }
    }
}
