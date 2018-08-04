package ctfchallenge.views;

import ctfchallenge.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainView extends Stage {
    public MainView(Scoreboard scoreboard, TextArea txt, TeamList teamList) {
        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        RadioButtonsBlock buttons = new RadioButtonsBlock();
        ComboBoxBlock comboBoxBlock = new ComboBoxBlock();

        Toolbar toolbar = new Toolbar(txt, buttons, comboBoxBlock, teamList, scoreboard);

        mainPane.setTop(toolbar);
        mainPane.setLeft(txt);
        mainPane.setCenter(buttons);
        mainPane.setRight(comboBoxBlock);

        setScene(mainScene);
        initGUI();
    }

    private void initGUI() {
        setMaximized(false);
        setWidth(1100);
        setHeight(600);
        setTitle("Main");
        setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Sei sicuro di voler uscire?",
                    ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });
        getIcons().add(new Image("file:logo.png"));
    }
}
