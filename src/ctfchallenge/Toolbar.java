/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import static ctfchallenge.CTFChallenge.txt;
import static ctfchallenge.CTFChallenge.numeroes;
import static ctfchallenge.CTFChallenge.punteggioEs;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author matte
 */
public class Toolbar extends HBox {
        private Button addTeam = new Button("Nuova squadra");
        private Button removeTeam = new Button("Rimuovi squadra");
        private Button refreshScore = new Button("Aggiorna la classifica");
        private Button gameOn = new Button("Inizia la partita");
        private Button sendResults = new Button("Invia i risultati");
        private Button incrementFont = new Button("+");
        private Button decrementFont = new Button("-");
        private final Button editTeam = new Button("Modifica squadra");

    public Toolbar(RadioButtons buttons, ComboBoxBlock comboBoxBlock, SquadreHandler squadreHandler, Scoreboard scoreboard) {
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #336699;");
        
        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, gameOn);
        
        addTeam.setOnAction((ActionEvent event) -> {
            addTeamActions();
        });

        removeTeam.setOnAction((ActionEvent event) -> {
            removeTeamActions();
        });
        removeTeam.setDisable(true);

        editTeam.setOnAction((ActionEvent event) -> {
            editTeamActions();
        });
        editTeam.setDisable(true);
                
        incrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.incrementFont();
        });

        decrementFont.setOnAction((ActionEvent event) -> {
            scoreboard.decrementFont();
        });
        
        refreshScore.setOnAction((ActionEvent event) -> {
            scoreboard.refresh();
            CTFChallenge.backupData();
        });
        
        gameOn.setOnAction((ActionEvent event) -> {
            CTFChallenge.numeroes++;
            if (CTFChallenge.numeroes == 1) {
                addTeam.setDisable(true);
                removeTeam.setDisable(true);
                buttons.setRadioButtons(buttons, SquadreHandler.squadreList);
                comboBoxBlock.setComboBox(comboBoxBlock);
                getChildren().addAll(sendResults, incrementFont, decrementFont);
            }
            if (CTFChallenge.numeroes >= 1 && CTFChallenge.numeroes <= 5) {
                txt.appendText("Inizio esercizio " + numeroes + "\n");
                gameOn.setText("Prossimo esercizio");
                sendResults.setDisable(false);
            } else {
                squadreHandler.victoryHandler();
                sendResults.setDisable(true);
            }
            refreshScore.fire();
            gameOn.setDisable(true);
        });
        
        sendResults.setOnAction((ActionEvent onFinish) -> {
            buttons.sendResults(SquadreHandler.squadreList, punteggioEs(numeroes));
            comboBoxBlock.sendResults();
            refreshScore.fire();
            gameOn.setDisable(false);
            if (numeroes == 5) {
                gameOn.setText("Termina partita");
            }
            sendResults.setDisable(true);

        });
    }

    private void addTeamActions() {
        String member1 = null;
        String member2 = null;
        String nome = null;
        Squadra tmp;
        try {
            member1 = CTFChallenge.optionDialog("Nome del primo membro?");
            member2 = CTFChallenge.optionDialog("Nome del secondo membro?");
            nome = CTFChallenge.optionDialog("Nome della squadra");
        } catch (Exception e) {
            txt.appendText("ERRORE! Prova a reinserire la squadra!");
            Logger.getLogger(CTFChallenge.class.getName()).log(Level.SEVERE, null, e);
        }
        if (member1 != null && member2 != null && nome != null) {
            tmp = new Squadra(member1, member2, nome);
            txt.appendText("Nuova squadra creata:\nID: " + tmp.getId() + " (nome squadra: " + tmp.getNomesquadra() + ")\nMembri: " + tmp + "\n");
            SquadreHandler.squadreList.add(tmp);
        }
        if (!(SquadreHandler.squadreList.isEmpty())) {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
    }

    private void editTeamActions() {
        String id = CTFChallenge.optionDialog("Nome della squadra da modificare");
        Squadra tmp = Squadra.getSquadraFromName(id, SquadreHandler.squadreList);
        if (tmp != null) {
            Stage editStage = new Stage();
            StackPane editPane = new StackPane();
            Scene editScene = new Scene(editPane);
            editStage.setScene(editScene);
            editStage.setTitle("Modifica squadra...");
            editStage.show();
        } else {
            txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
        }
    }

    private void removeTeamActions() {
        if (Squadra.getNumerosquadre() != 0) {
            String id = CTFChallenge.optionDialog("Nome della squadra da cancellare");
            Squadra tmp = Squadra.getSquadraFromName(id, SquadreHandler.squadreList);
            if (tmp != null) {
                txt.appendText("Squadra con nome " + id + " rimossa con successo\n");
            } else {
                txt.appendText("Nessuna squadra trovata " + "con questo nome (" + id + ")\n");
            }
        } else {
            txt.appendText("Nessuna squadra da rimuovere!" + "\n");
        }
        if (SquadreHandler.squadreList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }
}
