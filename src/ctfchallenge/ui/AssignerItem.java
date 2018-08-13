package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.Common;
import ctfchallenge.assets.Logging;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import static ctfchallenge.assets.Common.currentRound;

/**
 * This class represents a single instance of an Assigner item, tasked with choosing and assigning points and
 * bonuses for a single team.
 *
 * @author Matteo Franzil
 * @version 1.2
 */
public final class AssignerItem extends HBox {

    private Team team;
    private ToggleGroup tg;
    private ComboBox<String> position;

    /**
     * Standard constructor for the class.
     *
     * @param team The team to be associated with the AssignerItem instance.
     */
    public AssignerItem(Team team) {
        this.team = team;

        RadioButton completed = new RadioButton("Completed");
        RadioButton notCompleted = new RadioButton("Not completed");
        tg = new ToggleGroup();
        position = new ComboBox<>();

        Integer min = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
        for (int i = 0; i < min; i++) {
            position.getItems().add(String.valueOf(i + 1));
        }
        position.getItems().add("Out");

        position.setDisable(true);
        position.setValue("Out");

        completed.setToggleGroup(tg);
        notCompleted.setToggleGroup(tg);
        notCompleted.setSelected(true);

        completed.setOnAction(e -> position.setDisable(false));
        notCompleted.setOnAction(e -> {
            position.setDisable(true);
            position.setValue("Out");
        });

        getChildren().addAll(new Label(team.getName()) {{
            setMinWidth(125);
        }}, completed, notCompleted, new Label("") {{
            setMinWidth(35);
        }}, new Text("Position:"), position);

        setPadding(new Insets(15));
        setSpacing(20);
    }

    /**
     * This methods must be called from PointAssigner. It automatically updates the AssignerItem's team with
     * the corresponding score (with bonuses) for the round.
     */
    public void sendResults() {
        Integer score = Common.punteggioEs(currentRound);

        String output = "Team " + team.getName() + ":";

        if (((Labeled) tg.getSelectedToggle()).getText().equals("Completed")) {
            team.incrementScore(score);
            output += "\nCompletion points: " + score;
        } else if (((Labeled) tg.getSelectedToggle()).getText().equals("Not completed")) {
            output += "\nNo completion points";
        }

        Integer min = Math.min(Common.MAX_TEAMS_BONUS, Common.teamNumber);
        try {
            Integer bonusScore = Integer.parseInt(position.getValue());
            team.setScore(team.getScore() + min - bonusScore + 1);
            output += "\nBonus points: " + (min - bonusScore + 1);
        } catch (NumberFormatException ex) {
            output += "\nNo bonus points";
        }

        Logging.info(output);

        // This must be changed in case the toggles get swapped
        tg.selectToggle(tg.getToggles().get(1));
        position.setDisable(true);
        position.setValue("Out");
    }
}
