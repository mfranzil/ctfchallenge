/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import static ctfchallenge.CTFChallenge.getSquadre;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author matte
 */
public class Scoreboard {

    private final TableView<Object> table;
    private final TableColumn<Object, String> nomesquadra;
    private final TableColumn<Object, TableColumn<Object, String>> giocatori;
    private final TableColumn<Object, String> membro1;
    private final TableColumn<Object, String> membro2;
    private final TableColumn<Object, String> punteggio;

    public Scoreboard() {
        table = new TableView<>();
        nomesquadra = new TableColumn<>("Nome squadra");
        giocatori = new TableColumn<>("Giocatori");
        membro1 = new TableColumn<>("Membro 1");
        membro2 = new TableColumn<>("Membro 2");
        punteggio = new TableColumn<>("Punteggio");

        nomesquadra.setCellValueFactory(new PropertyValueFactory<>("nomesquadra"));
        membro1.setCellValueFactory(new PropertyValueFactory<>("membro1"));
        membro2.setCellValueFactory(new PropertyValueFactory<>("membro2"));
        punteggio.setCellValueFactory(new PropertyValueFactory<>("punteggio"));

        nomesquadra.setMinWidth(250);
        nomesquadra.setMaxWidth(350);
        membro1.setMinWidth(250);
        membro1.setMaxWidth(350);
        membro2.setMinWidth(250);
        membro2.setMaxWidth(350);
        punteggio.setMinWidth(250);
        punteggio.setMaxWidth(350);
        
        nomesquadra.setStyle("-fx-font: 30px Arial;");
        membro1.setStyle("-fx-font: 30px Arial;");
        membro2.setStyle("-fx-font: 30px Arial;");
        punteggio.setStyle("-fx-font: 30px Arial;");
        giocatori.setStyle("-fx-font: 30px Arial;");
        
        
        
        giocatori.getColumns().addAll(membro1, membro2);
        table.getColumns().addAll(nomesquadra, giocatori, punteggio);
    }

    public TableView getTable() {
        return table;
    }

    public static void refreshScoreboard(Pane scoreboardPane, Stage scoreboardWindow) {
        TableView table = new Scoreboard().getTable();
        table.setItems(getSquadre());
        if (!(scoreboardPane.getChildren().contains(table))) {
            scoreboardPane.getChildren().add(table);
            scoreboardWindow.show();
        } else {
            scoreboardPane.getChildren().remove(table);
            scoreboardPane.getChildren().add(table);
        }
    }

}
