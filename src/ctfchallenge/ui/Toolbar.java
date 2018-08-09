package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.*;
import ctfchallenge.views.EditView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import static ctfchallenge.assets.Common.MAX_EXERCISES;
import static ctfchallenge.assets.Common.currentEs;

/**
 * @author Matteo Franzil
 * @version 1.1
 */
public final class Toolbar extends HBox {

    private final Button restoreData, editTeam, addTeam, removeTeam, refreshScore,
            startGame, sendResults, incrementFont, decrementFont, goToEx;

    /**
     * Costruttore standard della toolbar del programma.
     *
     * @param pointAssigner Il gestore dei punti.
     * @param teamList      Il gestore delle squadre.
     */
    public Toolbar(@NotNull PointAssigner pointAssigner, @NotNull TeamList teamList) {
        addTeam = new Button("Nuova squadra");
        removeTeam = new Button("Rimuovi squadra");
        editTeam = new Button("Modifica squadra");
        incrementFont = new Button("+");
        decrementFont = new Button("-");
        startGame = new Button("Inizia la partita");
        goToEx = new Button("Modifica numero esercizio");
        restoreData = new Button("Recupera da backup");
        refreshScore = new Button("Aggiorna la classifica");
        sendResults = new Button("Invia i risultati");

        removeTeam.setDisable(true);
        editTeam.setDisable(true);
        goToEx.setDisable(true);

        addTeam.setOnAction(e -> addTeamActions(teamList));
        removeTeam.setOnAction(e -> removeTeamActions(teamList));
        editTeam.setOnAction(e -> editTeamActions(teamList));
        incrementFont.setOnAction(Scoreboard::incrementFont);
        decrementFont.setOnAction(Scoreboard::decrementFont);
        startGame.setOnAction(e -> startGameActions(pointAssigner, teamList));
        goToEx.setOnAction(e -> goToExActions());
        restoreData.setOnAction(e -> BackupHandler.restoreData(teamList));

        refreshScore.setOnAction(e -> {
            Scoreboard.refreshScoreboard();
            BackupHandler.backupData(teamList);
        });

        sendResults.setOnAction(e -> {
            pointAssigner.sendResults();
            refreshScore.fire();
            startGame.setDisable(false);
            if (currentEs == 5) {
                startGame.setText("Termina partita");
            }
            sendResults.setDisable(true);
        });

        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, startGame, goToEx, restoreData);

        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setBackground(new Background(new BackgroundFill(AccentParser.getAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Metodo di gestione del bottone per aggiungere le squadre.
     *
     * @param teamList Una ObservableList di Squadre
     */
    private void addTeamActions(@NotNull TeamList teamList) {
        try {
            Team team = new Team("", "", "");
            EditView editView = new EditView(team, false);
            editView.showAndWait();
            teamList.add(team);
        } catch (Exception e) {
            Logging.error("Inserimento della squadra fallito.");
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
    private void editTeamActions(TeamList teamList) {
        if (teamList.size() > 0) {
            VBox root = new VBox();
            root.getChildren().add(new Text("Scegli la squadra da modificare"));

            for (Team team : teamList) {
                root.getChildren().add(new Button(team.getTeamName()) {{
                    setOnAction(e -> new EditView(team, true).showAndWait());
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

            teamList.forEach(team -> root.getChildren().add(new Button(team.getTeamName()) {{
                setOnAction(e -> {
                    Logging.info("Team con nome " + team.getTeamName() + " rimosso con successo");
                    teamList.remove(team);
                    root.getChildren().remove(this);

                    if (teamList.size() <= 0) {
                        stage.close();
                    }
                });
            }}));

            root.setPadding(new Insets(16));
            root.setSpacing(16);
            stage.setScene(scene);
            stage.setTitle("Scegli la squadra");
            stage.show();
        } else {
            Logging.error("Nessuna squadra da rimuovere!");
        }
        if (teamList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }

    private void startGameActions(@NotNull PointAssigner pointAssigner, @NotNull TeamList teamList) {
        Common.currentEs++;
        Common.teamNumber = teamList.size();
        if (Common.currentEs == 1) {
            addTeam.setDisable(true);
            removeTeam.setDisable(true);
            pointAssigner.setPointAssigner(teamList);
            getChildren().addAll(sendResults, incrementFont, decrementFont);
        }
        if (Common.currentEs >= 1 && Common.currentEs <= MAX_EXERCISES) {
            Logging.info("Inizio esercizio " + currentEs + "");
            startGame.setText("Prossimo esercizio");
            sendResults.setDisable(false);
        } else {
            teamList.processVictory();
            sendResults.setDisable(true);
        }
        refreshScore.fire();
        restoreData.setDisable(true);
        startGame.setDisable(true);
        goToEx.setDisable(false);
    }

    private void goToExActions() {
        try {
            HBox root = new HBox();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            for (int i = 0; i < MAX_EXERCISES; i++) {
                int finalI = i + 1;
                root.getChildren().add(new Button("" + finalI) {{
                    setOnAction(e -> {
                        Common.currentEs = finalI;
                        startGame.fire();
                        Logging.info("Salto al livello " + currentEs + "");
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
    }
}
