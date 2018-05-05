package ctfchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author matte
 */
public class CTFChallenge extends Application {

    private static final ObservableList<Squadra> squadre = FXCollections.observableArrayList();
    private static int numeroes = 0;
    private static final TextArea txt = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        // Una seconda stage per una seconda finestra
        Stage scoreboardWindow = new Stage();

        // Bottoni
        Button addTeam = new Button("Nuova squadra");
        Button removeTeam = new Button("Rimuovi squadra");
        Button refreshScore = new Button("Aggiorna la classifica");
        Button gameOn = new Button("Inizia la partita");
        Button sendResults = new Button("Invia i risultati");
        Button incrementFont = new Button("+");
        Button decrementFont = new Button("-");

        // Queste due classi raggruppano le scelte dei vincitori
        RadioButtons buttons = new RadioButtons();
        ComboBoxBlock comboBoxBlock = new ComboBoxBlock();
        Scoreboard scoreboard = new Scoreboard();

        // mainPane per tutto, scoreBoardPane per la finestra esterna
        GridPane mainPane = new GridPane();
        StackPane scoreboardPane = new StackPane();

        Scene primary = new Scene(mainPane);
        Scene scoreboardScene = new Scene(scoreboardPane);

        // Disegno la tabella e inizializzo alcuni attributi
        txt.setEditable(false);
        removeTeam.setDisable(true);

        // Se chiudo una finestra si chiudono tutte
        scoreboardWindow.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
        });
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            Platform.exit();
        });

        // Allineo tutti gli oggetti
        setColumnRowIndex(addTeam, 1, 1);
        setColumnRowIndex(removeTeam, 2, 1);
        setColumnRowIndex(refreshScore, 3, 1);
        setColumnRowIndex(gameOn, 4, 1);
        setColumnRowIndex(incrementFont, 5, 1);
        setColumnRowIndex(decrementFont, 6, 1);
        setColumnRowIndex(sendResults, 10, 13);
        setColumnRowIndex(txt, 1, 2);

        GridPane.setColumnSpan(txt, 4);
        GridPane.setRowSpan(txt, 25);
        GridPane.setHalignment(decrementFont, HPos.LEFT);
        GridPane.setHalignment(incrementFont, HPos.RIGHT);

        // Un po' di padding non fa male
        mainPane.setHgap(8);
        mainPane.setVgap(8);

        // Bottone addTeam
        addTeam.setOnAction((ActionEvent event) -> {
            addTeamActions(removeTeam);
        });

        // Bottone removeTeam
        removeTeam.setOnAction((ActionEvent event) -> {
            removeTeamActions(removeTeam);
        });

        // Bottoni per i font della tabella
        incrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.incrementFont();
        });

        decrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.decrementFont();
        });

        // Bottone di refresh // TODO Find other workaround
        refreshScore.setOnAction((ActionEvent event) -> {
            if (!(scoreboardPane.getChildren().contains(scoreboard))) {
                scoreboard.setItems(getSquadre());
                scoreboardPane.getChildren().add(scoreboard);
                scoreboardWindow.show();
            } else {
                scoreboard.refresh();
            }

            backupData();
        });

        // Bottone per iniziare il match
        gameOn.setOnAction((ActionEvent event) -> {
            numeroes++;
            if (numeroes == 1) {
                addTeam.setDisable(true);
                removeTeam.setDisable(true);
                buttons.setRadioButtons(mainPane);
                comboBoxBlock.setComboBox(mainPane);

                mainPane.getChildren().addAll(sendResults, incrementFont, decrementFont);
            }
            if (numeroes >= 1 && numeroes <= 5) {
                txt.appendText("Inizio esercizio " + numeroes + "\n");
                gameOn.setText("Prossimo esercizio");
                sendResults.setDisable(false);
            } else {
                txt.appendText("PARTITA FINITA!\nVince la squadra ");
                sendResults.setDisable(true);
            }
            refreshScore.fire();
            gameOn.setDisable(true);
            primaryStage.sizeToScene();
        });

        // Bottone per inviare i risultati
        sendResults.setOnAction((ActionEvent onFinish) -> {
            for (int i = 0; i < getSquadre().size(); i++) {
                RadioButton temp = (RadioButton) buttons.getRadio_btn().get(i).getSelectedToggle();
                if (temp.getText().equals("Completato")) {
                    getSquadre().get(i).setPunteggio(getSquadre().get(i).getPunteggio() + punteggioEs(numeroes));
                    txt.appendText("La squadra " + getSquadre().get(i).getNomesquadra()
                            + " ottiene " + punteggioEs(numeroes) + " punti\n");
                }
            }
            for (int i = 0; i < 5; i++) {
                String object_name = (comboBoxBlock.getCbox().get(i).getValue());
                if (object_name != null) {
                    Squadra temp = getSquadre().get(Squadra.nameToId(object_name, getSquadre()) - 1);
                    temp.setPunteggio(temp.getPunteggio() + 5 - i);
                    txt.appendText("La squadra " + temp.getNomesquadra()
                            + " ottiene " + (5 - i) + " punti bonus\n");
                }
            }

            refreshScore.fire();
            gameOn.setDisable(false);
            if (numeroes == 5) {
                gameOn.setText("Termina partita");
            }
            sendResults.setDisable(true);

        });

        // Aggiungo ai pane quanto serve
        mainPane.getChildren().addAll(addTeam, removeTeam, refreshScore, gameOn, txt);

        // Infine mostro le due finestre
        primaryStage.setMaximized(false);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(600);
        primaryStage.setTitle("CTFChallenge");
        primaryStage.setScene(primary);
        primaryStage.show();

        scoreboardWindow.setTitle("Classifica");
        scoreboardWindow.setScene(scoreboardScene);
    }

    /**
     * Finestrella per chiedere una String customizzata senza grafica basata su JavaFX.
     * @param question La domanda da porre nella finestra
     * @return Una string contenente la risposta.
     */
    private String optionDialog(String question) {
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
    public void backupData() {
        try (BufferedWriter fileOut = new BufferedWriter(new FileWriter("backup.txt"))) {
            fileOut.write("LOG:\n" + txt.getText() + "\nSCOREBOARD:\n");
            fileOut.write("ID Squadra\t\tNome squadra\t\tMembro 1\t\tMembro 2\t\tPunteggio\n");
            System.out.println("Logging in progress...");
            for (Squadra temp : squadre) {
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
     * Metodo che gestisce il bottone addTeam.
     *
     * @param removeTeam Il bottone removeTeam che deve essere nel caso attivato
     * se le squadre sono >0.
     */
    public void addTeamActions(Button removeTeam) {
        String member1 = null, member2 = null, nome = null;
        Squadra tmp;
        try {
            member1 = optionDialog("Nome del primo membro?");
            member2 = optionDialog("Nome del secondo membro?");
            nome = optionDialog("Nome della squadra");
        } catch (Exception e) {
            txt.appendText("ERRORE! Prova a reinserire la squadra!");
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, e);
        }
        if (member1 != null && member2 != null && nome != null) {
            tmp = new Squadra(member1, member2, nome);
            txt.appendText("Nuova squadra creata:\nID: "
                    + tmp.getId() + " (nome squadra: " + tmp.getNomesquadra()
                    + ")\nMembri: " + tmp + "\n");
            getSquadre().add(tmp);
        }
        if (Squadra.getNumerosquadre() > 0) {
            removeTeam.setDisable(false);
        }
    }

    /**
     * Metodo che gestisce il bottone removeTeam.
     *
     * @param removeTeam Il bottone sulla quale agisce il metodo.
     */
    public void removeTeamActions(Button removeTeam) {
        if (Squadra.getNumerosquadre() != 0) {
            int id = Integer.parseInt(optionDialog("ID della"
                    + " squadra da cancellare"));
            Squadra tmp = getSquadre().remove(id - 1);
            if (tmp != null) {
                txt.appendText("Squadra con ID " + id
                        + " rimossa con successo\n");
            } else {
                txt.appendText("Nessuna squadra trovata "
                        + "con questo ID (" + id + ")\n");
            }
        } else {
            txt.appendText("Nessuna squadra da rimuovere!" + "\n");
        }

        if (getSquadre().isEmpty()) {
            removeTeam.setDisable(true);
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

    /**
     * Metodo getter per il parametro statico squadre.
     *
     * @return Una ObservableList delle squadre della partita.
     */
    public static ObservableList<Squadra> getSquadre() {
        return squadre;
    }

    public static TextArea getTxt() {
        return txt;
    }

}
