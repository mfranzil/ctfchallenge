/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author matte
 */
public final class Scoreboard extends TableView<Squadra> {

    private final TableColumn<Squadra, String> nomesquadra;
    private final TableColumn<Squadra, TableColumn<Squadra, String>> giocatori;
    private final TableColumn<Squadra, String> membro1;
    private final TableColumn<Squadra, String> membro2;
    private final TableColumn<Squadra, String> punteggio;
    
    private final int fontsizes[] = {6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 21, 24, 36, 48, 60, 72, 84, 96};
    private int pointer = 11; // Pt = 24

    @SuppressWarnings("unchecked")
    public Scoreboard() {
        nomesquadra = new TableColumn<>("Nome squadra");
        giocatori = new TableColumn<>("Giocatori");
        membro1 = new TableColumn<>("Membro 1");
        membro2 = new TableColumn<>("Membro 2");
        punteggio = new TableColumn<>("Punteggio");

        nomesquadra.setCellValueFactory(new PropertyValueFactory<>("nomesquadra"));
        membro1.setCellValueFactory(new PropertyValueFactory<>("membro1"));
        membro2.setCellValueFactory(new PropertyValueFactory<>("membro2"));
        punteggio.setCellValueFactory(new PropertyValueFactory<>("punteggio"));
        
        nomesquadra.prefWidthProperty().bind(widthProperty().divide(6.0));
        membro1.prefWidthProperty().bind(widthProperty().divide(3.0));
        membro2.prefWidthProperty().bind(widthProperty().divide(3.0));
        punteggio.prefWidthProperty().bind(widthProperty().divide(6.0));

        resizeFont(24);
        
        giocatori.getColumns().addAll(membro1, membro2);
        getColumns().addAll(nomesquadra, giocatori, punteggio);
    }
    
    public void decrementFont() {
        --pointer;
        if (pointer >= 0 && pointer <= fontsizes.length) {
            resizeFont(fontsizes[pointer]);
        } else {
            CTFChallenge.getTxt().appendText("Impossibile settare la grandezza desiderata\n");
            ++pointer;
        }
    }
    
    public void incrementFont() {
        ++pointer;
        if (pointer >= 0 && pointer <= fontsizes.length) {
            resizeFont(fontsizes[pointer]);
        } else {
            CTFChallenge.getTxt().appendText("Impossibile settare la grandezza desiderata\n");
            --pointer;
        }
    }

    public void resizeFont(int size) {
        if (size > 0 && size < 100) {
            String style = ("-fx-font: " + size + "px Arial; -fx-alignment: CENTER");
            nomesquadra.setStyle(style);
            membro1.setStyle(style);
            membro2.setStyle(style);
            punteggio.setStyle(style);
            giocatori.setStyle(style);
            CTFChallenge.getTxt().appendText("Grandezza del font settata a " + size +  "\n");
        } else {
            CTFChallenge.getTxt().appendText("Grandezza del font non valida!" + "\n");
        }

    }

}
