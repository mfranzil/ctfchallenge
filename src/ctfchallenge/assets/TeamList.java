package ctfchallenge.assets;

import ctfchallenge.Team;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An ObservableList containing all the teams participating in the match.
 * Gets observed by a {@link ctfchallenge.ui.Scoreboard}.
 *
 * @author Matteo Franzil
 * @version 20181108v1
 */
public class TeamList extends SimpleListProperty<Team> {

    /**
     * Standard constructor, overriding the FXCollections one.
     */
    public TeamList() {
        super(FXCollections.observableArrayList());
    }

    /**
     * Method that searches for a Team within the Collection using the name.
     *
     * @param name The name of the team to search.
     * @return A Team, or null.
     */
    @Deprecated
    public Team getSquadraFromName(String name) {
        Team res = null;
        for (Team i : this) {
            if (name.equals(i.getName())) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * Method that returns the current leader (most points) in the collection.
     *
     * @return An ArrayList containing at least one Team.
     */
    public ArrayList<Team> getLeader() {
        Collections.sort(this);
        int punteggio_temp = this.get(0).getScore();

        ArrayList<Team> temp = new ArrayList<>();
        for (Team i : this) {
            if (i.getScore() < punteggio_temp) {
                break;
            } else {
                temp.add(i);
            }
        }
        return temp;
    }
}
