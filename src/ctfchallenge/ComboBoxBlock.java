/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author matte
 */
public class ComboBoxBlock {

    private final ArrayList<Text> cbox_text;
    private final ArrayList<ComboBox<String>> cbox;

    /**
     *
     */
    public ComboBoxBlock() {
        cbox_text = new ArrayList<>();
        cbox = new ArrayList<>();
    }

    void setComboBox(Pane gridView) {
        for (int i = 0; i < 5; i++) {
            ComboBox<String> p_box_temp = new ComboBox<>();
            Text p_temp = new Text(CTFChallenge.intToText(i + 1) + " classificato");
            cbox.add(p_box_temp);
            cbox_text.add(p_temp);
        }
        Iterator<Squadra> iter;
        for (int i = 0; i < 5; i++) {
            GridPane.setColumnIndex(cbox_text.get(i), 10);
            GridPane.setColumnIndex(cbox.get(i), 10);
            GridPane.setRowIndex(cbox_text.get(i), 2 + (2 * i));
            GridPane.setRowIndex(cbox.get(i), 3 + (2 * i));
            iter = CTFChallenge.getSquadre().iterator();
            while (iter.hasNext()) {
                String temp = (iter.next()).getNomesquadra();
                cbox.get(i).getItems().add(temp);
            }
            gridView.getChildren().addAll(cbox_text.get(i), cbox.get(i));
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<ComboBox<String>> getCbox() {
        return cbox;
    }

}
