package ctfchallenge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class CTFChallenge extends Application {

    /**
     *
     */
    public static int numeroes = 0;

    /**
     *
     */
    public static final int MAX_TEAMS_BONUS = 5;

    /**
     *
     */
    public static final int MAX_TEAMS = 10;

    /**
     *
     */
    public static final int MAX_EXERCISES = 5;

    @Override
    public void start(Stage primaryStage) {
        // Una seconda stage per una seconda finestra della scoreboard
        Stage scoreboardWindow = new Stage();

        // mainPane per tutto, scoreBoardPane per la finestra esterna
        BorderPane mainPane = new BorderPane();
        StackPane scoreboardPane = new StackPane();

        Scene mainScene = new Scene(mainPane);
        Scene scoreboardScene = new Scene(scoreboardPane);

        // Queste classi verranno inserite nel borderpane
        TextArea txt = new TextArea();
        RadioButtons buttons = new RadioButtons();
        ComboBoxBlock comboBoxBlock = new ComboBoxBlock();
        SquadreHandler squadreHandler = new SquadreHandler();
        Scoreboard scoreboard = new Scoreboard(txt);
        Toolbar toolBar = new Toolbar(txt, buttons, comboBoxBlock, squadreHandler, scoreboard, primaryStage);

        scoreboard.setItems(squadreHandler.squadreList);
        scoreboardPane.getChildren().add(scoreboard);

        mainPane.setTop(toolBar);
        mainPane.setLeft(txt);
        mainPane.setCenter(buttons);
        mainPane.setRight(comboBoxBlock);

        txt.setEditable(false);

        // Infine mostro le due finestre
        primaryStage.setMaximized(false);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(600);
        primaryStage.setTitle("CTFChallenge");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest((final WindowEvent event) -> {
            event.consume();
            ExitStage exit = new ExitStage();
        });
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.show();

        scoreboardWindow.setTitle("Classifica");
        scoreboardWindow.setWidth(500);
        scoreboardWindow.setHeight(600);
        scoreboardWindow.setScene(scoreboardScene);
        scoreboardWindow.setOnCloseRequest((final WindowEvent event) -> {
            event.consume();
        });
        scoreboardWindow.getIcons().add(new Image("file:logo.png"));
        scoreboardWindow.show();
    }

    /**
     * Finestrella per chiedere una String customizzata senza grafica basata su
     * JavaFX.
     *
     * @param question La domanda da porre nella finestra
     * @return Una string contenente la risposta.
     */
    public static String optionDialog(String question) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(question);
        dialog.setTitle("CTF");
        dialog.setGraphic(null);
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }

    /**
     * Metodo che fa un backup dei dati su backup.txt ogni volta che viene
     * chiamato sovrascrivendo il precedente.
     *
     * @param txt
     * @param squadreHandler
     */
    public static void backupData(TextArea txt, SquadreHandler squadreHandler) {
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("log.txt"));
            fileOut.write(txt.getText());
            fileOut.close();
            fileOut = new BufferedWriter(new FileWriter("backup.txt"));
            System.out.println("Logging in progress...");
            for (Squadra temp : squadreHandler.squadreList) {
                String data = temp.getId() + " TAB " + temp.getNomesquadra() + " TAB "
                        + temp.getMembro1() + " TAB " + temp.getMembro2() + " TAB " + temp.getPunteggio() + " TAB";
                fileOut.write(data);
            }
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Logging finished.");
        }

    }

    /**
     * Metodo che evoca un FileChooser per riprendere il gioco da un backup precedente.
     * @param txt La finestra di log del programma.
     * @param squadreHandler
     * @param stage La stage su cui mostrare il FileChooser.
     */
    public static void restoreData(TextArea txt, SquadreHandler squadreHandler, Stage stage) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carica un file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) {
                System.out.println("No file chosen");
                System.exit(1);
            }
            Scanner fileIn = new Scanner(file).useDelimiter("\\s*TAB\\s*");
            while(fileIn.hasNext()) {
                String id = fileIn.next(), nomesquadra = fileIn.next(),
                        membro1 = fileIn.next(), membro2 = fileIn.next(), punteggio = fileIn.next();
                Squadra temp = new Squadra(Integer.parseInt(id), nomesquadra, membro1, membro2, Integer.parseInt(punteggio));
                squadreHandler.squadreList.add(temp);
            }
            txt.appendText("Backup ripristinato con successo.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, ex);
            txt.appendText("Impossibile ripristinare il backup:");
        }
    }

    /**
     * Funzione ausiliaria per assegnare i punti agli esercizi. Sono supportati
     * fino a 5 esercizi.
     *
     * @param i L'indice dell'esercizio di cui ottenere il punteggio.
     * @return Il punteggio dell'esercizio desiderato.
     */
    public static int punteggioEs(int i) {
        int res;
        switch (i) {
            case 1:
                res = 5;
                break;
            case 2:
                res = 10;
                break;
            case 3:
                res = 15;
                break;
            case 4:
                res = 25;
                break;
            case 5:
                res = 25;
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }

    /**
     * Funzione ausiliaria statica che trasforma un numero cardinale nella sua
     * versione ordinale scritta in String.
     *
     * @param i Il numero da 1 a 5 da trasformare in String.
     * @return Una String con la prima lettera maiuscola, rappresentante
     * l'input.
     */
    public static String intToText(int i) {
        String res;
        switch (i) {
            case 1:
                res = "Primo";
                break;
            case 2:
                res = "Secondo";
                break;
            case 3:
                res = "Terzo";
                break;
            case 4:
                res = "Quarto";
                break;
            case 5:
                res = "Quinto";
                break;
            default:
                res = null;
                break;
        }
        return res;
    }

    /**
     * Metodo che si occupa di settare in maniera veloce gli indici di riga e
     * colonna in una GridPane.
     *
     * @param node Il nodo (appartenente a GridPane)
     * @param column L'indice della colonna
     * @param row L'indice della riga
     */
    public static void setColumnRowIndex(Node node, int column, int row) {
        GridPane.setColumnIndex(node, column);
        GridPane.setRowIndex(node, row);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
