package ctfchallenge;

import ctfchallenge.assets.AccentParser;
import ctfchallenge.assets.BackupHandler;
import ctfchallenge.assets.Common;
import ctfchallenge.assets.Logging;
import ctfchallenge.views.EditView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static ctfchallenge.assets.Common.MAX_EXERCISES;
import static ctfchallenge.assets.Common.numeroes;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public final class Toolbar extends HBox {

    private final Button restoreData = new Button("Recupera da backup");
    private final Button editTeam = new Button("Modifica squadra");
    private final Button addTeam = new Button("Nuova squadra");
    private final Button removeTeam = new Button("Rimuovi squadra");
    private final Button refreshScore = new Button("Aggiorna la classifica");
    private final Button startGame = new Button("Inizia la partita");
    private final Button sendResults = new Button("Invia i risultati");
    private final Button incrementFont = new Button("+");
    private final Button decrementFont = new Button("-");
    private final Button goToEx = new Button("Modifica numero esercizio");

    /**
     * Costruttore standard della toolbar del programma.
     *
     * @param buttons       I radiobutton delle squadre usati per il completamento dell'esercizio.
     * @param comboBoxBlock I comboBox usati per selezionare i primi...quinti arrivati.
     * @param teamList      Il gestore delle squadre.
     * @param scoreboard    La finestra dello scoreboard
     */
    public Toolbar(RadioButtonsBlock buttons, ComboBoxBlock comboBoxBlock,
                   TeamList teamList, Scoreboard scoreboard) {

        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setBackground(new Background(new BackgroundFill(AccentParser.getAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));

        removeTeam.setDisable(true);
        editTeam.setDisable(true);

        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, startGame, goToEx, restoreData);

        addTeam.setOnAction(e -> addTeamActions(scoreboard, teamList));
        removeTeam.setOnAction(e -> removeTeamActions(teamList));
        editTeam.setOnAction(e -> editTeamActions(scoreboard, teamList));
        incrementFont.setOnAction(e -> scoreboard.incrementFont());
        decrementFont.setOnAction(e -> scoreboard.decrementFont());

        refreshScore.setOnAction(e -> {
            scoreboard.refresh();
            BackupHandler.backupData(teamList);
        });

        startGame.setOnAction(e -> {
            Common.numeroes++;
            if (Common.numeroes == 1) {
                addTeam.setDisable(true);
                removeTeam.setDisable(true);
                buttons.setRadioButtons(buttons, teamList);
                comboBoxBlock.setComboBox(teamList);
                getChildren().addAll(sendResults, incrementFont, decrementFont);
            }
            if (Common.numeroes >= 1 && Common.numeroes <= MAX_EXERCISES) {
                Logging.info("Inizio esercizio " + numeroes + "\n");
                startGame.setText("Prossimo esercizio");
                sendResults.setDisable(false);
            } else {
                teamList.processVictory();
                sendResults.setDisable(true);
            }
            refreshScore.fire();
            startGame.setDisable(true);
            goToEx.setDisable(false);
        });

        sendResults.setOnAction(e -> {
            buttons.sendResults(teamList, Common.punteggioEs(numeroes));
            comboBoxBlock.sendResults(teamList);
            refreshScore.fire();
            startGame.setDisable(false);
            if (numeroes == 5) {
                startGame.setText("Termina partita");
            }
            sendResults.setDisable(true);
        });

        goToEx.setOnAction(e -> {
            try {
                HBox root = new HBox();
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                for (int i = 0; i < MAX_EXERCISES; i++) {
                    int finalI = i + 1;
                    root.getChildren().add(new Button("" + finalI) {{
                        setOnAction(e -> {
                            Common.numeroes = finalI;
                            startGame.fire();
                            Logging.info("Salto al livello " + numeroes + "\n");
                            stage.close();
                        });
                    }});
                }
                root.setPadding(new Insets(16));
                root.setSpacing(16);

                stage.setScene(scene);
                stage.setTitle("Scegli l'esercizio");
                stage.show();
            } catch (Exception ex) {
                Logging.warning("Numero esercizio non valido!");
            }
        });
        goToEx.setDisable(true);

        restoreData.setOnAction(e -> BackupHandler.restoreData(teamList));
    }

    /**
     * Metodo di gestione del bottone per aggiungere le squadre.
     *
     * @param teamList Una ObservableList di Squadre
     */
    private void addTeamActions(Scoreboard scoreboard, TeamList teamList) {
        try {
            Team tmp = new Team("", "", "");
            EditView editView = new EditView(scoreboard, tmp, false);
            editView.showAndWait();
            teamList.add(tmp);
        } catch (Exception e) {
            Logging.error("Inserimento della squadra fallito.\n");
        }
        if (!(teamList.isEmpty())) {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
    }

    /**
     * Metodo di gestione del bottone per modificare le squadre.
     *
     * @param teamList Una ObservableList di Squadre
     */
    private void editTeamActions(Scoreboard scoreboard, TeamList teamList) {
        if (teamList.size() > 0) {
            VBox root = new VBox();
            root.getChildren().add(new Text("Scegli la squadra da modificare"));

            for (Team team : teamList) {
                root.getChildren().add(new Button(team.getTeamName()) {{
                    setOnAction(e -> new EditView(scoreboard, team, true).showAndWait());
                }});
            }
            root.setPadding(new Insets(16));
            root.setSpacing(16);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Scegli la squadra");
            stage.show();
        } else {
            // new Alert(Alert.AlertType.ERROR, "Nessuna squadra da modificare!").showAndWait();
            Logging.error("Nessuna squadra da modificare!");
        }
    }


    /**
     * Metodo di gestione del bottone per rimuovere le squadre.
     *
     * @param teamList Una ObservableList di Squadre
     */
    private void removeTeamActions(TeamList teamList) {
        if (teamList.size() > 0) {
            VBox root = new VBox();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.getChildren().add(new Text("Scegli la squadra da cancellare"));

            for (Team team : teamList) {
                root.getChildren().add(new Button(team.getTeamName()) {{
                    setOnAction(e -> {
                        Logging.info("Team con nome " + team.getTeamName() + " rimosso con successo\n");
                        teamList.remove(team);
                        root.getChildren().remove(this);

                        if (teamList.size() <= 0) {
                            stage.close();
                        }
                    });
                }});
            }
            root.setPadding(new Insets(16));
            root.setSpacing(16);
            stage.setScene(scene);
            stage.setTitle("Scegli la squadra");
            stage.show();
        } else {
            Logging.error("Nessuna squadra da rimuovere!" + "\n");
        }
        if (teamList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }
}
