package ctfchallenge;


import static ctfchallenge.CTFChallenge.numeroes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;


/**
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class Toolbar extends HBox {

    private Button addTeam = new Button("Nuova squadra");
    private Button removeTeam = new Button("Rimuovi squadra");
    private Button refreshScore = new Button("Aggiorna la classifica");
    private Button startGame = new Button("Inizia la partita");
    private Button sendResults = new Button("Invia i risultati");
    private Button incrementFont = new Button("+");
    private Button decrementFont = new Button("-");
    private final Button editTeam = new Button("Modifica squadra");

    /**
     *
     * @param buttons
     * @param comboBoxBlock
     * @param squadreHandler
     * @param scoreboard
     */
    public Toolbar(TextArea txt, RadioButtons buttons, ComboBoxBlock comboBoxBlock,
            SquadreHandler squadreHandler, Scoreboard scoreboard) {
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #336699;");

        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, startGame);

        addTeam.setOnAction((ActionEvent event) -> {
            addTeamActions(txt, scoreboard, squadreHandler);
        });

        removeTeam.setOnAction((ActionEvent event) -> {
            removeTeamActions(txt, squadreHandler);
        });
        removeTeam.setDisable(true);

        editTeam.setOnAction((ActionEvent event) -> {
            editTeamActions(txt, scoreboard, squadreHandler);
        });
        editTeam.setDisable(true);

        incrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.incrementFont(txt);
        });

        decrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.decrementFont(txt);
        });

        refreshScore.setOnAction((ActionEvent event) -> {
            scoreboard.refresh();
            CTFChallenge.backupData(txt, squadreHandler);
        });

        startGame.setOnAction((ActionEvent event) -> {
            CTFChallenge.numeroes++;
            if (CTFChallenge.numeroes == 1) {
                addTeam.setDisable(true);
                removeTeam.setDisable(true);
                buttons.setRadioButtons(buttons, squadreHandler.squadreList);
                comboBoxBlock.setComboBox(comboBoxBlock, squadreHandler);
                getChildren().addAll(sendResults, incrementFont, decrementFont);
            }
            if (CTFChallenge.numeroes >= 1 && CTFChallenge.numeroes <= CTFChallenge.MAX_EXERCISES) {
                txt.appendText("Inizio esercizio " + numeroes + "\n");
                startGame.setText("Prossimo esercizio");
                sendResults.setDisable(false);
            } else {
                squadreHandler.victoryHandler(txt);
                sendResults.setDisable(true);
            }
            refreshScore.fire();
            startGame.setDisable(true);
        });

        sendResults.setOnAction((ActionEvent onFinish) -> {
            buttons.sendResults(txt, squadreHandler.squadreList, CTFChallenge.punteggioEs(numeroes));
            comboBoxBlock.sendResults(txt, squadreHandler);
            refreshScore.fire();
            startGame.setDisable(false);
            if (numeroes == 5) {
                startGame.setText("Termina partita");
            }
            sendResults.setDisable(true);
        });
    }

    /**
     * 
     * @param squadreHandler 
     */
    private void addTeamActions(TextArea txt, Scoreboard scoreboard, SquadreHandler squadreHandler) {
        try {
            Squadra tmp = new Squadra("", "", "");
            EditStage editStage = new EditStage(txt, squadreHandler, scoreboard, tmp, false);
            squadreHandler.squadreList.add(tmp);
        } catch (Exception e) {
            txt.appendText("ERRORE! Prova a reinserire la squadra!\n");
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, e);
        }
        if (!(squadreHandler.squadreList.isEmpty())) {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
    }
    
    /**
     * 
     * @param squadreHandler 
     */
    private void editTeamActions(TextArea txt, Scoreboard scoreboard, SquadreHandler squadreHandler) { // TODO move to another method
        String id = CTFChallenge.optionDialog("Nome della squadra da modificare");
        Squadra tmp = Squadra.getSquadraFromName(id, squadreHandler.squadreList);
        if (tmp != null) {
            EditStage editStage = new EditStage(txt, squadreHandler, scoreboard, tmp, true);
        } else {
            txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
        }
    }

    /**
     * 
     * @param squadreHandler 
     */
    private void removeTeamActions(TextArea txt, SquadreHandler squadreHandler) {
        if (Squadra.getNumerosquadre() != 0) {
            String id = CTFChallenge.optionDialog("Nome della squadra da cancellare");
            Squadra tmp = Squadra.getSquadraFromName(id, squadreHandler.squadreList);
            if (tmp != null) {
                txt.appendText("Squadra con nome " + id + " rimossa con successo\n");
                squadreHandler.squadreList.remove(tmp);
            } else {
                txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
            }
        } else {
            txt.appendText("Nessuna squadra da rimuovere!" + "\n");
        }
        if (squadreHandler.squadreList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }
}
