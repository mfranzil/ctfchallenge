package ctfchallenge;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

/**
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class SquadreHandler {

    /**
     *
     */
    public final ObservableList<Squadra> squadreList = FXCollections.observableArrayList();

    public SquadreHandler() {
        Squadra tmp1 = new Squadra(6, "Alfredo!!!", "Laura Scoccianti", "Marco Casari", 70);
        Squadra tmp2 = new Squadra(2, "Lolloxor", "Lorenzo Masciullo", "Riccardo Franceschini", 62);
        Squadra tmp3 = new Squadra(4, "E adesso che faggio?", "Riccardo Marchesin", "Claudia Collarin", 60);
        Squadra tmp4 = new Squadra(3, "Studenti con percorso d'eccellenza", "Paolo Teta", "Federica Carta", 59);
        Squadra tmp5 = new Squadra(1, "Gesuiti non euclidei", "Pietro Capovilla", "Giovanni Tognolini", 56);
        Squadra tmp6 = new Squadra(5, "Occhio di falco", "Angelo Valente", "Nicola Fraccarolo", 55);
        Squadra tmp7 = new Squadra(7, "Emag eht", "Ruben Bettoni", "Andrea Carrara", 65);
        Squadra tmp8 = new Squadra(8, "Non sono prompt", "Paolo Baiguera", "Matteo Franzil", 72);
        squadreList.addAll(tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7, tmp8);
    }

    /**
     *
     * @return
     */
    public ArrayList<Squadra> getLeader() {
        int punteggio_temp = 0;
        ArrayList<Squadra> temp = new ArrayList<>();
        for (Squadra i : squadreList) {
            if (i.getPunteggio() > punteggio_temp) {
                punteggio_temp = i.getPunteggio();
                temp.add(i);
            }
        }
        return temp;
    }

    /**
     *
     */
    public void victoryHandler(TextArea txt) {
        ArrayList<Squadra> winners = getLeader();
        switch (winners.size()) {
            case 0:
                txt.appendText("Sembra che non abbia vinto nessuno....guarda la classifica per ottenere il vincitore.\n");
                break;
            case 1:
                txt.appendText("PARTITA FINITA!\nVince la squadra " + winners.get(0));
                break;
            default:
                txt.appendText("PARTITA FINITA\nVincono le squadre: \n");
                winners.forEach((Squadra i) -> {
                    txt.appendText(i.getNomesquadra() + "\n");
                });
                break;
        }
    }

}
