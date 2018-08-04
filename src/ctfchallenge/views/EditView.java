package ctfchallenge.views;

import ctfchallenge.Scoreboard;
import ctfchallenge.Team;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class EditView extends Stage {

    /**
     * Questa finestra viene mostrata in caso di aggiunta o modifica di una squadra
     *
     * @param txt        La finestra di log del programma.
     * @param scoreboard La finestea della scoreboard.
     * @param tmp        La squadra che verrà modificata.
     * @param isEdit     Metodo evocato in edit mode o addition mode (con punteggi bloccati)
     */
    public EditView(TextArea txt, Scoreboard scoreboard, Team tmp, boolean isEdit) {
        GridPane editPane = new GridPane();
        Scene editScene = new Scene(editPane);

        Button send = new Button("Invia");

        Text idField = new Text(String.valueOf(tmp.getId()));
        TextField nomeField = new TextField(tmp.getNomesquadra());
        TextField m1Field = new TextField(tmp.getMembro1());
        TextField m2Field = new TextField(tmp.getMembro2());
        TextField ptField = new TextField(String.valueOf(tmp.getPunteggio()));

        if (!isEdit) {
            ptField.setEditable(false);
            ptField.setDisable(true);
        }

        Text idText = new Text("ID");
        Text nomeText = new Text("Nome");
        Text m1Text = new Text("Membro 1");
        Text m2Text = new Text("Membro 2");
        Text ptText = new Text("Punteggio");

        editPane.add(idText, 0, 0);
        editPane.add(nomeText, 0, 1);
        editPane.add(m1Text, 0, 2);
        editPane.add(m2Text, 0, 3);
        editPane.add(ptText, 0, 4);
        editPane.add(idField, 1, 0);
        editPane.add(nomeField, 1, 1);
        editPane.add(m1Field, 1, 2);
        editPane.add(m2Field, 1, 3);
        editPane.add(ptField, 1, 4);
        editPane.add(send, 1, 5);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        editPane.getColumnConstraints().addAll(c1, c2);

        editPane.setHgap(8);
        editPane.setVgap(8);
        editPane.setPadding(new Insets(15, 12, 15, 12));

        GridPane.setHalignment(send, HPos.RIGHT);

        send.setOnAction((ActionEvent event) -> {
            tmp.setNomesquadra(nomeField.getText());
            tmp.setMembro1(m1Field.getText());
            tmp.setMembro2(m2Field.getText());
            tmp.setPunteggio(Integer.parseInt(ptField.getText()));
            this.close();
            scoreboard.refresh();
            txt.appendText("Team con ID " + tmp.getId() + (isEdit ? " aggiornata:" : " aggiunta:")
                    + "\nNome: " + tmp.getNomesquadra() + "\nMembri: " + tmp.getMembro1() + ", " + tmp.getMembro2()
                    + "\nPunteggio: " + tmp.getPunteggio() + "\n");
        });

        setScene(editScene);
        setTitle((isEdit ? "Modifica squadra..." : "Aggiungi squadra..."));
        getIcons().add(new Image("file:logo.png"));
    }

}
