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
 * @version 20181108v1
 */
public final class AssignerTeam extends HBox {

    private Text caption;
    private Team team;
    /**
     * Standard constructor for the class.
     *
     * @param team The team to be associated with the AssignerTeam instance.
     */
    public AssignerTeam(Team team) {
        this.team = team;

        caption = new Text("\n" + team.getName() + "\nPt. " + team.getScore());
        getChildren().add(caption);

        for (int i = 1; i <= Common.MAX_ROUNDS; i++) {
            AssignerRound rnd = new AssignerRound(team, i);
            getChildren().add(rnd);
        }
        //addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> update());

        setPadding(new Insets(15));
        setSpacing(20);
    }

    /**
     * Recursively dismantles AssignerRounds.
     */
    public void dismantle() {
        getChildren().forEach(i -> {
            if (i instanceof AssignerRound) {
                ((AssignerRound) i).dismantle();
            }
        });
    }

    /**
     * Updates the score of the round.
     */
    public void update() {
        caption.setText("\n" + team.getName() + "\nPt. " + team.getScore());
    }
}
