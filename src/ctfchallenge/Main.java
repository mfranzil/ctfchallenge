package ctfchallenge;

import ctfchallenge.views.MainView;
import ctfchallenge.views.ScoreboardView;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.0
 * @since 07/05/2018
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextArea txt = new TextArea() {{
            setEditable(false);
        }};
        Scoreboard scoreboard = new Scoreboard(txt);
        SquadreHandler squadreHandler = new SquadreHandler();
        scoreboard.setItems(squadreHandler.squadreList);

        ScoreboardView scoreboardWindow = new ScoreboardView(scoreboard);
        scoreboardWindow.show();

        MainView main = new MainView(scoreboard, txt, squadreHandler);
        main.show();
    }


}
