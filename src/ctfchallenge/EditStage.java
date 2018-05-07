package ctfchallenge;

import static ctfchallenge.CTFChallenge.txt;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
     * @param tmp
     */
    public EditStage(SquadreHandler squadreHandler, Squadra tmp) {
        Stage editStage = new Stage();
            GridPane editPane = new GridPane();
            Scene editScene = new Scene(editPane);
            editStage.setScene(editScene);
            editStage.setTitle("Modifica squadra...");

            TextField nome_field = new TextField(tmp.getNomesquadra());
            TextField m1_field = new TextField(tmp.getMembro1());
            TextField m2_field = new TextField(tmp.getMembro2());

            Button send = new Button("Invia");

            Text id_text = new Text("ID");
            Text id_field = new Text(String.valueOf(tmp.getId()));
            Text nome_text = new Text("Nome");
            Text m1_text = new Text("Membro 1");
            Text m2_text = new Text("Membro 2");
            CTFChallenge.setColumnRowIndex(id_text, 0, 0);
            CTFChallenge.setColumnRowIndex(nome_text, 0, 1);
            CTFChallenge.setColumnRowIndex(m1_text, 0, 2);
            CTFChallenge.setColumnRowIndex(m2_text, 0, 3);
            CTFChallenge.setColumnRowIndex(id_field, 1, 0);
            CTFChallenge.setColumnRowIndex(nome_field, 1, 1);
            CTFChallenge.setColumnRowIndex(m1_field, 1, 2);
            CTFChallenge.setColumnRowIndex(m2_field, 1, 3);
            CTFChallenge.setColumnRowIndex(send, 1, 4);

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
                squadreHandler.squadreList.remove(tmp);
                tmp.setNomesquadra(nome_field.getText());
                tmp.setMembro1(m1_field.getText());
                tmp.setMembro2(m2_field.getText());
                editStage.close();
                squadreHandler.squadreList.add(tmp);
                txt.appendText("Dettagli della squadra con id " +  tmp.getId() + " aggiornati: \n"
                        + "Nome: " + tmp.getNomesquadra() +  "\nMembri: " + tmp.getMembro1() + ", " + tmp.getMembro2() + "\n");

            });

            editPane.getChildren().addAll(id_text, nome_text, m1_text, m2_text,
                    id_field, nome_field, m1_field, m2_field, send);

            editStage.show(); 
    } 
    
    
}
