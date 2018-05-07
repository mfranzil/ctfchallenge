/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static ctfchallenge.CTFChallenge.txt;

/**
 *
 * @author matte
 */
public class SquadreHandler {

    public final ObservableList<Squadra> squadreList = FXCollections.observableArrayList();

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

    public void victoryHandler() {
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
