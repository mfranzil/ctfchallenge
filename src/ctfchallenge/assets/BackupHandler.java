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
     * @param txt            La finestra di log dei dati.
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
                String data = temp.getId() + " TAB " + temp.getNomesquadra() + " TAB "
                        + temp.getMembro1() + " TAB " + temp.getMembro2() + " TAB " + temp.getPunteggio() + " TAB";
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
     * @param txt            La finestra di log del programma.
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
                Scanner fileIn = new Scanner(file).useDelimiter("\\s*TAB\\s*");
                while (fileIn.hasNext()) {
                    String id = fileIn.next(), nomesquadra = fileIn.next(),
                            membro1 = fileIn.next(), membro2 = fileIn.next(), punteggio = fileIn.next();
                    Team temp = new Team(Integer.parseInt(id), nomesquadra, membro1, membro2, Integer.parseInt(punteggio));
                    teamList.add(temp);
                }
            }
            txt.appendText("Backup ripristinato con successo.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            txt.appendText("Impossibile ripristinare il backup:");
        }
    }
}
