package cs102.pr.pack;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameView {

    public GameView() {}

    // game database class
    private GameDB info;

    public GameView(String player1, String player2, int time) {
        
        this.player1 = player1;
        this.player2 = player2;
        this.time = time;
    }

    private static String player1;
    private static String player2;
    private int time;
    private int timer = 0;

    private static boolean isSurrenderWhite = false;
    private static boolean isSurrenderRed = false;
    private static boolean isEndTimer = false;

    private BorderPane border = new BorderPane();
    private static Text text = new Text();

    private Task task = taskCreator();
    private Thread thread = new Thread(task);

    private final Menu menu2 = new Menu("Match");
    private final Menu menu1 = new Menu("Game");
    private static Scene scene;

    private final String endingText = "\n\tCheck out the game database or the console for more info.\n";

    public Scene GameViewScene() {

        VBox hbox = new VBox(10);
        scene = new Scene(border, 600, 720);

        MenuBar menuBar = new MenuBar();
        final MenuItem exitItem = new MenuItem("Exit game");
        final MenuItem menuItem = new MenuItem("Back to main menu");
        final MenuItem surrItem = new MenuItem("Surrender white");
        final MenuItem surrItem2 = new MenuItem("Surrender red");

        Setup setup = new Setup();
        Main.window.setResizable(false);

        menu1.getItems().addAll(menuItem, exitItem);
        menu2.getItems().addAll(surrItem, surrItem2);
        menuBar.getMenus().addAll(menu1, menu2);

        border.setTop(hbox);
        hbox.getChildren().addAll(menuBar);
        border.setCenter(setup.createContent());
        text.setFont(StarterView.font);
        text.setFill(StarterView.color);
        border.setBottom(text);

        menu2.setDisable(false);
        surrItem.setDisable(false);
        surrItem2.setDisable(false);

        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        border.getStyleClass().add("general-layout");
        border.getStyleClass().add("game-view");

        setAction(exitItem, menuItem, surrItem, surrItem2);
        thread.start();
        return scene;
    }

    private void setAction(MenuItem m1, MenuItem m2, MenuItem m3, MenuItem m4) {

        m1.setOnAction(event -> System.exit(0));

        m2.setOnAction(event -> {

            Main.window.setScene(MainMenuView.scene);
            // stop created thread
            thread.stop();
            Main.window.centerOnScreen();
        });

        // ~ duplicates ---------------
        m3.setOnAction(event -> {

            m3.setDisable(true);
            m4.setDisable(true);
            isSurrenderWhite = true;
            text.setText("\t" + player1 + " has surrendered!" + endingText);
            stopThread();
        });

        m4.setOnAction(event -> {

            m3.setDisable(true);
            m4.setDisable(true);
            isSurrenderRed = true;
            text.setText("\t" + player2 + " has surrendered!" + endingText);
            stopThread();
        });
        // ----------------------------
    }

    private void stopThread() {

        thread.stop();
        printIt();

        try { pass(); }
        catch (SQLException ex) { Logger.getLogger(GameView.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private Task taskCreator() {

        return new Task() {

            @Override
            protected Object call() throws Exception {

                isEndTimer = false;
                isSurrenderRed = false;
                isSurrenderWhite = false;
                Setup.redPoints = 0;
                Setup.whitePoints = 0;

                for (timer = time; timer >= 0; timer--) {

                    text.setText("\tMatch time: " + time + "s\n\t" + player1 + " VS " + player2 + "\n\tClock: " + timer + "s\n");
                    Thread.sleep(1000);
                    System.out.println("Timer: " + timer);

                    if (timer == 0) isEndTimer = true;

                    if (isEndTimer == true)
                        text.setText("\tEnd of game." + endingText);

                    if (isEndTimer == true) {

                        menu2.setDisable(true);
                        printIt();
                        pass();
                    }
                }

                return true;
            }
        };
    }

    private String getWinner() { // woof

        if (isSurrenderWhite == false && isSurrenderRed == false) {

            if (Setup.whitePoints > Setup.redPoints) return player1;

            else if (Setup.whitePoints == Setup.redPoints) {

                return "The game ended in a draw.";

            } else return player2;

        } else if (isSurrenderWhite == true) {

            Setup.redPoints = 0;
            Setup.whitePoints = 0;
            return player2;

        } else if (isSurrenderRed == true) {

            Setup.redPoints = 0;
            Setup.whitePoints = 0;
            return player1;

        } else return "Console log: An error has occured;";
    }

    private void pass() throws SQLException {

        info = new GameDB(player1, player2, time, Setup.whitePoints, Setup.redPoints, isSurrenderWhite, isSurrenderRed, getWinner());
        System.out.println("Database info:\n" + info.toString());

        GameDB.createTable();
        GameDB.insert();
    }

    private void printIt() {

        System.out.println("Printing information:\n------------------------------\n" +
                "player1 = " + player1 + " player2 = " + player2 + " time = " + time + "\nSetup.redPoints = " + Setup.redPoints + " Setup.whitePoints = " + Setup.whitePoints + " isSurrenderWhite = " + isSurrenderWhite + " isSurrendeRed = " + isSurrenderRed + "\nWinner is = " + getWinner() + "\n" +
                "------------------------------");
    }
}
