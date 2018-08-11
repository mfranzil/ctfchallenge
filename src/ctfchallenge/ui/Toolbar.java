package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.*;
import ctfchallenge.views.EditView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static ctfchallenge.assets.Common.MAX_ROUNDS;
import static ctfchallenge.assets.Common.currentRound;

/**
 * @author Matteo Franzil
 * @version 1.2
 */
public final class Toolbar extends HBox {

    private final Button restoreData, editTeam, addTeam, removeTeam, refreshScore,
            startGame, sendResults, incrementFont, decrementFont;

    /**
     * Standard constructor for the Toolbar.
     *
     * @param pointAssigner The score assigner.
     * @param teamList      The ObservableList of teams.
     */
    public Toolbar(@NotNull PointAssigner pointAssigner, @NotNull TeamList teamList) {
        addTeam = new Button("Add team");
        removeTeam = new Button("Remove team");
        editTeam = new Button("Edit team");
        incrementFont = new Button("+");
        decrementFont = new Button("-");
        startGame = new Button("Start the match");
        restoreData = new Button("Restore from backup");
        refreshScore = new Button("Update the scoreboard");
        sendResults = new Button("Send results");

        removeTeam.setDisable(true);
        editTeam.setDisable(true);

        addTeam.setOnAction(e -> addTeamActions(teamList));
        removeTeam.setOnAction(e -> removeTeamActions(teamList));
        editTeam.setOnAction(e -> editTeamActions(teamList));
        incrementFont.setOnAction(Scoreboard::incrementFont);
        decrementFont.setOnAction(Scoreboard::decrementFont);
        startGame.setOnAction(e -> startGameActions(pointAssigner, teamList));
        restoreData.setOnAction(e -> BackupHandler.restoreData(teamList));

        refreshScore.setOnAction(e -> {
            Scoreboard.refreshScoreboard();
            BackupHandler.backupData(teamList);
        });

        sendResults.setOnAction(e -> {
            pointAssigner.sendResults();
            refreshScore.fire();
            startGame.setDisable(false);
            if (currentRound == Common.MAX_ROUNDS) {
                startGame.setText("End match");
            }
            sendResults.setDisable(true);
        });

        getChildren().addAll(addTeam, removeTeam, editTeam, refreshScore, startGame, restoreData);

        setPadding(new Insets(15));
        setSpacing(10);
        setBackground(new Background(new BackgroundFill(AccentParser.getAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void addTeamActions(@NotNull TeamList teamList) {
        try {
            Team team = new Team("", "", "", 0);
            EditView editView = new EditView(team, false, addTeam);
            editView.showAndWait();
            teamList.add(team);
        } catch (Exception e) {
            Logging.error("Team insertion failed.");
        }
        if (teamList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        } else {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
        }
    }

    private void editTeamActions(TeamList teamList) {
        if (teamList.size() > 0) {
            VBox root = new VBox();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.getChildren().add(new Text("Choose the team to edit:"));
            root.setPadding(new Insets(16));
            root.setSpacing(16);

            for (Team team : teamList) {
                root.getChildren().add(new Button(team.getName()) {{
                    setOnAction(e -> new EditView(team, true, this).showAndWait());
                }});
            }

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(editTeam.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Choose the team");
            stage.show();
        } else {
            Logging.error("No team to edit!");
        }
    }

    private void removeTeamActions(TeamList teamList) {
        if (teamList.size() > 0) {
            VBox root = new VBox();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            root.getChildren().add(new Text("Chooose the team to remove:"));
            root.setPadding(new Insets(16));
            root.setSpacing(16);

            teamList.forEach(team -> root.getChildren().add(new Button(team.getName()) {{
                setOnAction(e -> {
                    Logging.info("Team with name " + team.getName() + " successfully removed");
                    teamList.remove(team);
                    root.getChildren().remove(this);

                    if (teamList.size() <= 0) {
                        stage.close();
                        removeTeam.setDisable(true);
                        editTeam.setDisable(true);
                    }
                });
            }}));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(removeTeam.getScene().getWindow());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Choose the team");
            stage.show();
        } else {
            Logging.error("No team to remove!");
        }
        if (teamList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
        }
    }

    private void startGameActions(@NotNull PointAssigner pointAssigner, @NotNull TeamList teamList) {
        Common.currentRound++;
        Common.teamNumber = teamList.size();
        if (Common.currentRound == 1) {
            addTeam.setDisable(true);
            removeTeam.setDisable(true);
            pointAssigner.setPointAssigner(teamList);
            getChildren().addAll(sendResults, incrementFont, decrementFont);
            getChildren().remove(restoreData);
        }
        if (Common.currentRound >= 1 && Common.currentRound <= MAX_ROUNDS) {
            Logging.info("Starting round " + currentRound);
            startGame.setText("Next round");
            sendResults.setDisable(false);
        } else {
            processVictory(teamList);
        }
        refreshScore.fire();
        startGame.setDisable(true);
        // goToEx.setDisable(false);
    }

    private void processVictory(TeamList teamList) {
        ArrayList<Team> winners = teamList.getLeader();
        switch (winners.size()) {
            case 0:
                Logging.error("Whoops, looks like nobody has won. Take a look at the scoreboard for" +
                        " information on the winner.");
                break;
            case 1:
                Team winner = winners.get(0);
                Logging.info("MATCH FINISHED!\nWinner: " + winner);
                break;
            default:
                String winnerStr = "";
                for (Team team : winners) {
                    winnerStr = winnerStr + team + "\n";
                }
                Logging.info("MATCH FINISHED!\nThese teams are getting their share:\n" + winnerStr);
                break;
        }
        sendResults.setDisable(true);
        editTeam.setDisable(true);
        refreshScore.setDisable(true);
    }

    @Deprecated
    private void goToExActions() {
        // Code to insert this in the program:
        //
        //Button goToEx = new Button("Modifica numero esercizio");
        //goToEx.setDisable(true);
        //goToEx.setOnAction(e -> goToExActions());
        //getChildren().add(goToEx);

        try {
            HBox root = new HBox();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            for (int i = 0; i < MAX_ROUNDS; i++) {
                int finalI = i;
                root.getChildren().add(new Button("" + finalI) {{
                    setOnAction(e -> {
                        Common.currentRound = finalI;
                        startGame.fire();
                        Logging.info("Jump to round " + currentRound + "");
                        stage.close();
                    });
                }});
            }
            root.setPadding(new Insets(16));
            root.setSpacing(16);

            stage.setScene(scene);
            stage.setTitle("Choose the round.");
            stage.show();
        } catch (Exception ex) {
            Logging.warning("Round number is invalid!");
        }
    }
}
