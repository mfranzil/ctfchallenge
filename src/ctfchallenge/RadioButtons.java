/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import java.util.ArrayList;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
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

    void setRadioButtons(Pane gridView) {
        int media = (int) Math.floor(CTFChallenge.getSquadre().size() / 2);
        for (int i = 0; i < CTFChallenge.getSquadre().size(); i++) {
            RadioButton vinto_tmp = new RadioButton("Completato");
            RadioButton perso_tmp = new RadioButton("Non completato");
            ToggleGroup radio_btn_tmp = new ToggleGroup();
            Text radio_text_tmp = new Text(CTFChallenge.getSquadre().get(i).getNomesquadra());
            vinto_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setToggleGroup(radio_btn_tmp);
            perso_tmp.setSelected(true);
            if (i < media) {
                GridPane.setColumnIndex(vinto_tmp, 5);
                GridPane.setRowIndex(vinto_tmp, 3 + (3 * i));
                GridPane.setColumnIndex(perso_tmp, 5);
                GridPane.setRowIndex(perso_tmp, 4 + (3 * i));
                GridPane.setColumnIndex(radio_text_tmp, 5);
                GridPane.setRowIndex(radio_text_tmp, 2 + (3 * i));
            } else {
                GridPane.setColumnIndex(vinto_tmp, 8);
                GridPane.setRowIndex(vinto_tmp, 3 + (3 * (i - media)));
                GridPane.setColumnIndex(perso_tmp, 8);
                GridPane.setRowIndex(perso_tmp, 4 + (3 * (i - media)));
                GridPane.setColumnIndex(radio_text_tmp, 8);
                GridPane.setRowIndex(radio_text_tmp, 2 + (3 * (i - media)));
            }
            vinto.add(vinto_tmp);
            perso.add(perso_tmp);
            radio_btn.add(radio_btn_tmp);
            radio_text.add(radio_text_tmp);
            gridView.getChildren().addAll(vinto_tmp, perso_tmp, radio_text_tmp);
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
