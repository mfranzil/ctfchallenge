package ctfchallenge;

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
 * @since 07/05/2018
 * @version 1.0
 * @author Matteo Franzil
 */
public class EditStage {

    /**
     *
     * @param squadreHandler
     * @param scoreboard
     * @param tmp
     * @param isEdit
     */
    public EditStage(TextArea txt, SquadreHandler squadreHandler, Scoreboard scoreboard, Squadra tmp, boolean isEdit) {
        Stage editStage = new Stage();
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

        CTFChallenge.setColumnRowIndex(idText, 0, 0);
        CTFChallenge.setColumnRowIndex(nomeText, 0, 1);
        CTFChallenge.setColumnRowIndex(m1Text, 0, 2);
        CTFChallenge.setColumnRowIndex(m2Text, 0, 3);
        CTFChallenge.setColumnRowIndex(ptText, 0, 4);
        CTFChallenge.setColumnRowIndex(idField, 1, 0);
        CTFChallenge.setColumnRowIndex(nomeField, 1, 1);
        CTFChallenge.setColumnRowIndex(m1Field, 1, 2);
        CTFChallenge.setColumnRowIndex(m2Field, 1, 3);
        CTFChallenge.setColumnRowIndex(ptField, 1, 4);
        CTFChallenge.setColumnRowIndex(send, 1, 5);

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
            editStage.close();
            scoreboard.refresh();
            txt.appendText("Squadra con ID " + tmp.getId() + (isEdit ? " aggiornata:" : " aggiunta:")
                    + "\nNome: " + tmp.getNomesquadra() + "\nMembri: " + tmp.getMembro1() + ", " + tmp.getMembro2()
                    + "\nPunteggio: " + tmp.getPunteggio() + "\n");
        });

        editPane.getChildren().addAll(idText, nomeText, m1Text, m2Text, ptText,
                idField, nomeField, m1Field, m2Field, ptField, send);

        editStage.setScene(editScene);
        editStage.setTitle((isEdit ? "Modifica squadra..." : "Aggiungi squadra..."));
        editStage.getIcons().add(new Image("file:logo.png"));
        editStage.show();
    }

}
