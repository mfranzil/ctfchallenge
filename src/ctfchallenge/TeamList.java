package ctfchallenge;

import ctfchallenge.assets.Logging;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Matteo Franzil
 * @version 1.1
 */
public class TeamList extends SimpleListProperty<Team> {
    /**
     * Una ObservableList aggiornata man mano con le squadre della partita. Viene osservata dalla Scoreboard per
     * aggiornarne i contenuti.
     */

    public TeamList() {
        super(FXCollections.observableArrayList());
    }

    /**
     * Metodo che prende in entrata un nome e ritorna la squadra corrispondente al nome.
     *
     * @param name Il nome della squadra da cercare
     * @return Una Team che per prima ha un match con il valore cercato.
     */
    @Nullable
    public Team getSquadraFromName(@NotNull String name) {
        Team res = null;
        for (Team i : this) {
            if (name.equals(i.getTeamName())) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * Metodo per ottenere, all'interno della lista delle squadre, il Team con punteggio maggiore.
     *
     * @return Un ArrayList contenente 1 o più squadre con il punteggio maggiore.
     */
    @NotNull
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

    /**
     * Metodo per terminare la partita; si comporta in maniera diversa a seconda del numero dei vincitori.
     */
    public void processVictory() {
        ArrayList<Team> winners = getLeader();
        switch (winners.size()) {
            case 0:
                Logging.error("Sembra che non abbia vinto nessuno...." +
                        "guarda la classifica per ottenere il vincitore.");
                break;
            case 1:
                Logging.info("PARTITA FINITA!\nVince la squadra " + winners.get(0));
                break;
            default:
                Logging.info("PARTITA FINITA\nVincono le squadre:");
                winners.forEach(team -> Logging.info(team.getTeamName()));
                break;
        }
    }
}
