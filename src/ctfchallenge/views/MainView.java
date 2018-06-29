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
import javafx.stage.WindowEvent;

public class MainView extends Stage {
    public MainView(Stage primaryStage, Scoreboard scoreboard, TextArea txt, SquadreHandler squadreHandler) {
        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        RadioButtonsBlock buttons = new RadioButtonsBlock();
        ComboBoxBlock comboBoxBlock = new ComboBoxBlock();

        Toolbar toolbar = new Toolbar(txt, buttons, comboBoxBlock, squadreHandler, scoreboard, primaryStage);

        mainPane.setTop(toolbar);
        mainPane.setLeft(txt);
        mainPane.setCenter(buttons);
        mainPane.setRight(comboBoxBlock);


        initGUI(primaryStage);
        primaryStage.setScene(mainScene);
    }

    private void initGUI(Stage primaryStage) {
        primaryStage.setMaximized(false);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Main");
        primaryStage.setOnCloseRequest((final WindowEvent event) -> {
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Sei sicuro di voler uscire?",
                    ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> Platform.exit());
        });
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.show();
    }
}
