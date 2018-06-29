package ctfchallenge;

import ctfchallenge.assets.BackupHandler;
import ctfchallenge.assets.Common;
import ctfchallenge.views.EditView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ctfchallenge.assets.Common.numeroes;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class Toolbar extends HBox {

    private final Button restoreData = new Button("Recupera da backup");
    private final Button editTeam = new Button("Modifica squadra");
    private Button addTeam = new Button("Nuova squadra");
    private Button removeTeam = new Button("Rimuovi squadra");
    private Button refreshScore = new Button("Aggiorna la classifica");
    private Button startGame = new Button("Inizia la partita");
    private Button sendResults = new Button("Invia i risultati");
    private Button incrementFont = new Button("+");
    private Button decrementFont = new Button("-");
    private Button goToEx = new Button("Modifica numero esercizio");

    /**
     * Costruttore standard della toolbar del programma.
     *
     * @param txt            La finestra di log del programma principale
     * @param buttons        I radiobutton delle squadre usati per il completamento dell'esercizio.
     * @param comboBoxBlock  I comboBox usati per selezionare i primi...quinti arrivati.
     * @param squadreHandler Il gestore delle squadre.
     * @param scoreboard     La finestra dello scoreboard
     * @param primaryStage   La finestra principale del programma.
     */
    public Toolbar(TextArea txt, RadioButtonsBlock buttons, ComboBoxBlock comboBoxBlock,
                   SquadreHandler squadreHandler, Scoreboard scoreboard, Stage primaryStage) {

        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #336699;");

        removeTeam.setDisable(true);
        editTeam.setDisable(true);

        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, startGame, goToEx, restoreData);

        addTeam.setOnAction(e -> addTeamActions(txt, scoreboard, squadreHandler.squadreList));
        removeTeam.setOnAction(e -> removeTeamActions(txt, squadreHandler.squadreList));
        editTeam.setOnAction(e -> editTeamActions(txt, scoreboard, squadreHandler.squadreList));
        incrementFont.setOnAction(e -> scoreboard.incrementFont(txt));
        decrementFont.setOnAction(e -> scoreboard.decrementFont(txt));

        refreshScore.setOnAction(e -> {
            scoreboard.refresh();
            BackupHandler.backupData(txt, squadreHandler);
        });

        startGame.setOnAction((ActionEvent event) -> {
            Common.numeroes++;
            if (Common.numeroes == 1) {
                addTeam.setDisable(true);
                removeTeam.setDisable(true);
                buttons.setRadioButtons(buttons, squadreHandler.squadreList);
                comboBoxBlock.setComboBox(squadreHandler.squadreList);
                getChildren().addAll(sendResults, incrementFont, decrementFont);
            }
            if (Common.numeroes >= 1 && Common.numeroes <= Common.MAX_EXERCISES) {
                txt.appendText("Inizio esercizio " + numeroes + "\n");
                startGame.setText("Prossimo esercizio");
                sendResults.setDisable(false);
            } else {
                squadreHandler.victoryHandler(txt);
                sendResults.setDisable(true);
            }
            refreshScore.fire();
            startGame.setDisable(true);
            goToEx.setDisable(false);
        });

        sendResults.setOnAction((ActionEvent onFinish) -> {
            buttons.sendResults(txt, squadreHandler.squadreList, Common.punteggioEs(numeroes));
            comboBoxBlock.sendResults(txt, squadreHandler.squadreList);
            refreshScore.fire();
            startGame.setDisable(false);
            if (numeroes == 5) {
                startGame.setText("Termina partita");
            }
            sendResults.setDisable(true);
        });

        goToEx.setOnAction((ActionEvent onFinish) -> {
            try {
                do {
                    Common.numeroes = Integer.parseInt(Common.optionDialog("Numero dell'esercizio?"));
                } while (numeroes < 1 || numeroes > 5);
                startGame.fire();
                txt.appendText("Salto al livello " + numeroes + "\n");
            } catch (Exception e) {
                txt.appendText("Numero esercizio non valido!");
            }
        });
        goToEx.setDisable(true);

        restoreData.setOnAction((ActionEvent event) -> {
            squadreHandler.squadreList.clear();
            BackupHandler.restoreData(txt, squadreHandler, primaryStage);
        });

    }

    /**
     * Metodo di gestione del bottone per aggiungere le squadre.
     *
     * @param squadreList Una ObservableList di Squadre
     */
    private void addTeamActions(TextArea txt, Scoreboard scoreboard, ObservableList<Squadra> squadreList) {
        try {
            Squadra tmp = new Squadra("", "", "");
            EditView editView = new EditView(txt, scoreboard, tmp, false);
            editView.showAndWait();
            squadreList.add(tmp);
        } catch (Exception e) {
            txt.appendText("ERRORE! Prova a reinserire la squadra!\n");
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
        if (!(squadreList.isEmpty())) {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
    }

    /**
     * Metodo di gestione del bottone per modificare le squadre.
     *
     * @param squadreList Una ObservableList di Squadre
     */
    private void editTeamActions(TextArea txt, Scoreboard scoreboard, ObservableList<Squadra> squadreList) { // TODO move to another method
        String id = Common.optionDialog("Nome della squadra da modificare");
        Squadra tmp = Squadra.getSquadraFromName(id, squadreList);
        if (tmp != null) {
            EditView editView = new EditView(txt, scoreboard, tmp, true);
            editView.showAndWait();
        } else {
            txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
        }
    }

    /**
     * Metodo di gestione del bottone per rimuovere le squadre.
     *
     * @param squadreList Una ObservableList di Squadre
     */
    private void removeTeamActions(TextArea txt, ObservableList<Squadra> squadreList) {
        if (Squadra.getNumerosquadre() != 0) {
            String id = Common.optionDialog("Nome della squadra da cancellare");
            Squadra tmp = Squadra.getSquadraFromName(id, squadreList);
            if (tmp != null) {
                txt.appendText("Squadra con nome " + id + " rimossa con successo\n");
                squadreList.remove(tmp);
            } else {
                txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
            }
        } else {
            txt.appendText("Nessuna squadra da rimuovere!" + "\n");
        }
        if (squadreList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }
}
