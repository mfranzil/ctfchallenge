package ctfchallenge;

import ctfchallenge.assets.Logging;
import ctfchallenge.assets.TeamList;
import ctfchallenge.ui.Scoreboard;
import ctfchallenge.views.MainView;
import ctfchallenge.views.ScoreboardView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Matteo Franzil
 * @version 1.1
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
        Logging logWindow = Logging.getInstance();

        Scoreboard scoreboard = Scoreboard.getInstance();
        TeamList teamList = new TeamList();
        scoreboard.setItems(teamList);

        ScoreboardView scoreboardWindow = new ScoreboardView(scoreboard);
        scoreboardWindow.show();

        MainView main = new MainView(logWindow, teamList);
        main.show();
    }


}
