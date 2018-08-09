package ctfchallenge.views;

import ctfchallenge.Scoreboard;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.1
 */
public class ScoreboardView extends Stage {
    public ScoreboardView(Scoreboard scoreboard) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root);

        root.getChildren().add(scoreboard);

        setScene(scene);
        setTitle("Classifica");
        setWidth(500);
        setHeight(600);
        setOnCloseRequest(Event::consume);
        getIcons().add(new Image("file:logo.png"));
    }
}
