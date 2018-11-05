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
 * @version 20181105v2
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
        // Ottengo le istanze singleton della finestra di Logging e della Scoreboard
        Logging logWindow = Logging.getInstance();
        Scoreboard scoreboard = Scoreboard.getInstance();

        // La teamList viene utilizzata durante tutto il programma e istanziata qua
        TeamList teamList = new TeamList();
        scoreboard.setItems(teamList);

        // Creo una finestra separata per gestire la Scoreboard
        ScoreboardView scoreboardWindow = new ScoreboardView(scoreboard);
        scoreboardWindow.show();

        MainView main = new MainView(logWindow, teamList);
        main.show();
    }
}
