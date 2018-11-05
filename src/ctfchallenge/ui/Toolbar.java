package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.*;
import ctfchallenge.views.EditView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Matteo Franzil
 * @version 20181105v2
 */
public final class Toolbar extends HBox {

    private final Button restoreData, editTeam, addTeam, removeTeam,
            startGame, incrementFont, decrementFont;
    private final Slider backupSpeed;
    private Thread backupThr;

    /**
     * Standard constructor for the Toolbar.
     *
     * @param assignerTable The score assigner.
     * @param teamList      The ObservableList of teams.
     */
    public Toolbar(AssignerTable assignerTable, TeamList teamList) {
        addTeam = new Button("Add team");
        removeTeam = new Button("Remove team");
        editTeam = new Button("Edit team");
        incrementFont = new Button("+");
        decrementFont = new Button("-");
        startGame = new Button("Start the match");
        restoreData = new Button("Restore from backup");
        backupSpeed = new Slider(1.5, 240, 30);

        removeTeam.setDisable(true);
        editTeam.setDisable(true);
        startGame.setDisable(true);

        addTeam.setOnAction(e -> addTeamActions(teamList));
        removeTeam.setOnAction(e -> removeTeamActions(teamList));
        editTeam.setOnAction(e -> editTeamActions(teamList));
        incrementFont.setOnAction(Scoreboard::incrementFont);
        decrementFont.setOnAction(Scoreboard::decrementFont);
        startGame.setOnAction(e -> startGameActions(assignerTable, teamList));
        restoreData.setOnAction(e -> {
            boolean res = BackupHandler.restoreData(teamList);
            if (res) {
                removeTeam.setDisable(false);
                editTeam.setDisable(false);
                restoreData.setDisable(true);
                startGame.setDisable(false);
            }
        });

        getChildren().addAll(addTeam, removeTeam, editTeam, startGame, restoreData);

        setPadding(new Insets(15));
        setSpacing(10);
        setBackground(new Background(new BackgroundFill(AccentParser.getAccentColor(),
                CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void addTeamActions(TeamList teamList) {
        Team team = new Team("", "", "", "", 0);
        try {
            EditView editView = new EditView(team, false, addTeam);
            editView.showAndWait();
            teamList.add(team);
        } catch (Exception e) {
            Logging.error("Team insertion failed.");
        }

        if (team.getName().equals("")
                && team.getPlayer1().equals("")
                && team.getPlayer2().equals("")
                && team.getPlayer3().equals("")) {
            teamList.remove(team);
        }

        if (teamList.isEmpty()) {
            removeTeam.setDisable(true);
            editTeam.setDisable(true);
            startGame.setDisable(true);
            restoreData.setDisable(false);
        } else {
            removeTeam.setDisable(false);
            editTeam.setDisable(false);
            startGame.setDisable(false);
            restoreData.setDisable(true);
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
            startGame.setDisable(true);
            restoreData.setDisable(false);
        }
    }

    private void startGameActions(AssignerTable assignerTable, TeamList teamList) {
        Common.teamNumber = teamList.size();

        addTeam.setDisable(true);
        removeTeam.setDisable(true);

        backupThr = new Thread(() -> {
            while (true) {
                BackupHandler.log(teamList);
                try {
                    Thread.sleep((long) (1000 * backupSpeed.getValue()));
                } catch (InterruptedException e) {
                    backupThr.interrupt();
                    Logging.fatal("An unhandled exception caused the Backup thread to stop. Please restart" +
                            " the program and restore from the backup.");
                }
            }
        });
        backupThr.start();

        startGame.setText("Termina partita");
        startGame.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Sei sicuro di voler interrompere la partita? L'operazione non è reversibile!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                BackupHandler.log(teamList);
                editTeam.setDisable(true);
                startGame.setDisable(true);
                assignerTable.dismantle();
                processVictory(teamList);
            }
        });

        backupSpeed.setShowTickMarks(true);
        backupSpeed.setShowTickLabels(true);
        backupSpeed.valueProperty().addListener((ov, old_val, new_val) -> {
            Logging.info("Backup speed set to " + new_val + " seconds");
        });

        assignerTable.setAssignerTable(teamList);
        getChildren().addAll(incrementFont, decrementFont, backupSpeed);
        Logging.info("Starting match");
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
                StringBuilder winnerStr = new StringBuilder();
                for (Team team : winners) {
                    winnerStr.append(team).append("\n");
                }
                Logging.info("MATCH FINISHED!\nThese teams are getting their share:\n" + winnerStr);
                break;
        }
    }

}
