package ctfchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author matte
 */
public class CTFChallenge extends Application {

    public static int numeroes = 0;
    public static final TextArea txt = new TextArea();

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
        RadioButtons buttons = new RadioButtons();
        ComboBoxBlock comboBoxBlock = new ComboBoxBlock();
        SquadreHandler squadreHandler = new SquadreHandler();
        Scoreboard scoreboard = new Scoreboard();
        Toolbar toolBar = new Toolbar(buttons, comboBoxBlock, squadreHandler, scoreboard);
        
        scoreboard.setItems(squadreHandler.squadreList);
        scoreboardPane.getChildren().add(scoreboard);
        
        mainPane.setTop(toolBar);
        mainPane.setLeft(txt);
        mainPane.setCenter(buttons);
        mainPane.setRight(comboBoxBlock);

        txt.setEditable(false);

        // Se chiudo una finestra si chiudono tutte
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
        });
                
        scoreboardWindow.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
        });

        // Infine mostro le due finestre
        primaryStage.setMaximized(false);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(600);
        primaryStage.setTitle("CTFChallenge");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        scoreboardWindow.setTitle("Classifica");
        scoreboardWindow.setScene(scoreboardScene);
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
     */
    public static void backupData(SquadreHandler squadreHandler) {
        try {
            BufferedWriter fileOut = new BufferedWriter(new FileWriter("backup.txt"));
            fileOut.write("LOG:\n" + txt.getText() + "\nSCOREBOARD:\n");
            fileOut.write("ID Squadra\t\tNome squadra\t\tMembro 1\t\tMembro 2\t\tPunteggio\n");
            System.out.println("Logging in progress...");
            for (Squadra temp : squadreHandler.squadreList) {
                String data = temp.getId() + "\t\t" + temp.getNomesquadra() + "\t\t"
                        + temp.getMembro1() + "\t\t" + temp.getMembro2() + "\t\t" + temp.getPunteggio() + "\n";
                fileOut.write(data);
                System.out.print(data);
            }
        } catch (IOException ex) {
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void main(String[] args) {
        launch(args);
    }
}
