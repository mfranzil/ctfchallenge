/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author matte
 */
public class RadioButtons {

    private final ArrayList<ToggleGroup> radio_btn;
    private final ArrayList<Text> radio_text;
    private final ArrayList<RadioButton> vinto;
    private final ArrayList<RadioButton> perso;

    /**
     *
     */
    public RadioButtons() {
        radio_btn = new ArrayList<>();
        radio_text = new ArrayList<>();
        vinto = new ArrayList<>();
        perso = new ArrayList<>();
    }

    void setRadioButtons(Pane gridView, ObservableList<Squadra> squadre) {
        int media = (int) Math.floor(squadre.size() / 2);
        for (int i = 0; i < squadre.size(); i++) {
            RadioButton vinto_tmp = new RadioButton("Completato");
            RadioButton perso_tmp = new RadioButton("Non completato");
            ToggleGroup radio_btn_tmp = new ToggleGroup();
            Text radio_text_tmp = new Text(squadre.get(i).getNomesquadra());
            vinto_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setSelected(true);

            if (i < media) {
                CTFChallenge.setColumnRowIndex(radio_text_tmp, 5, 2 + (3 * i));
                CTFChallenge.setColumnRowIndex(vinto_tmp, 5, 3 + (3 * i));
                CTFChallenge.setColumnRowIndex(perso_tmp, 5, 4 + (3 * i));
            } else {
                CTFChallenge.setColumnRowIndex(radio_text_tmp, 8, 2 + (3 * (i - media)));
                CTFChallenge.setColumnRowIndex(vinto_tmp, 8, 3 + (3 * (i - media)));
                CTFChallenge.setColumnRowIndex(perso_tmp, 8, 4 + (3 * (i - media)));
            }

            vinto.add(vinto_tmp);
            perso.add(perso_tmp);
            radio_btn.add(radio_btn_tmp);
            radio_text.add(radio_text_tmp);
            gridView.getChildren().addAll(vinto_tmp, perso_tmp, radio_text_tmp);
        }
    }

    public void sendResults(ObservableList<Squadra> squadre, int punteggio) {
        for (int i = 0; i < squadre.size(); i++) {
            String label = ((Labeled) radio_btn.get(i).getSelectedToggle()).getText();
            Squadra current = squadre.get(i);
            if (label.equals("Completato")) {
                current.incrementPunteggio(punteggio);
                CTFChallenge.getTxt().appendText(
                        "La squadra " + current.getNomesquadra() + " ottiene " + punteggio + " punti\n");
            }
        }
        
    }

    /**
     *
     * @return
     */
    public ArrayList<ToggleGroup> getRadio_btn() {
        return radio_btn;
    }

}
