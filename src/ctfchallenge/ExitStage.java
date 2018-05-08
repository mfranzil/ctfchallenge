/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Franzil
 */
public class ExitStage {

    public ExitStage() {
        final Stage dialog = new Stage();
        HBox root = new HBox();
        Scene dialogScene = new Scene(root);

        dialog.initModality(Modality.APPLICATION_MODAL);

        Button okBtn = new Button("Sì");
        okBtn.setOnAction((ActionEvent event1) -> {
            Platform.exit();
        });

        Button cancelBtn = new Button("No");
        cancelBtn.setOnAction((ActionEvent event1) -> {
            dialog.close();
        });

        HBox.setMargin(okBtn, new Insets(15, 12, 15, 12));
        HBox.setMargin(cancelBtn, new Insets(15, 12, 15, 12));

        root.setPrefSize(250, 50);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(okBtn, cancelBtn);
        
        dialog.setScene(dialogScene);
        dialog.setTitle("Esci?");
        dialog.show();
    }

}
