package ctfchallenge.ui;

import ctfchallenge.Team;
import ctfchallenge.assets.Common;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * This class represents a single instance of an Assigner item, tasked with choosing and assigning points and
 * bonuses for a single team.
 *
 * @author Matteo Franzil
 * @version 1.3.1
 */
public final class AssignerTeam extends HBox {

    private Text score;
    /**
     * Standard constructor for the class.
     *
     * @param team The team to be associated with the AssignerTeam instance.
     */
    public AssignerTeam(Team team) {
        this.score = new Text("Pt. " + 0);

        getChildren().add(new Text("\n" + team.getName()));

        for (int i = 1; i <= Common.MAX_ROUNDS; i++) {
            AssignerRound rnd = new AssignerRound(team, i);
            getChildren().add(rnd);
        }

        setOnMouseClicked(e -> score.setText("Pt. " + team.getScore()));

        setPadding(new Insets(15));
        setSpacing(20);
    }

    public void dismantle() {
        getChildren().forEach(i -> ((AssignerRound) i).dismantle());
    }
}
