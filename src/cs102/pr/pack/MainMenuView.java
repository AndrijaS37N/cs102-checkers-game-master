package cs102.pr.pack;

import java.net.URL;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.util.Duration;

public class MainMenuView {

    public MainMenuView() {
    }

    public static Scene scene;
    private final URL resource = getClass().getResource("assets/gameMusic.mp3");
    private final Media media = new Media(resource.toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(media);
    private BorderPane border = new BorderPane();

    public Scene MainMenuViewScene() {
        VBox hbox = new VBox(50);
        final MenuBar menuBar = new MenuBar();
        final Menu menu1 = new Menu("Game");
        final MenuItem exitItem = new MenuItem("Exit game");
        final MenuItem gameItem = new MenuItem("Set up game");
        final Menu menu2 = new Menu("Music");
        final MenuItem musicItem1 = new MenuItem("Play");
        final MenuItem musicItem2 = new MenuItem("Pause");
        final Menu menu3 = new Menu("Help");
        final MenuItem rulesItem = new MenuItem("View rules");
        final Menu menu4 = new Menu("Credits");
        final MenuItem creditsItem = new MenuItem("Author");

        Main.window.setResizable(false);
        scene = new Scene(border, 720, 480);
        menu1.getItems().addAll(gameItem, exitItem);
        menu2.getItems().addAll(musicItem1, musicItem2);
        menu3.getItems().add(rulesItem);
        menu4.getItems().add(creditsItem);
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
        border.setTop(hbox);
        border.setCenter(setCircleAnimation());
        hbox.getChildren().addAll(menuBar);
        setButtonActions(exitItem, musicItem1, musicItem2, rulesItem, gameItem, creditsItem);

        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        border.getStyleClass().add("general-layout");
        border.getStyleClass().add("main-menu-view");

        musicItem1.setDisable(true);
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        return scene;
    }

    private void setButtonActions(MenuItem m1, MenuItem m2, MenuItem m3, MenuItem m4, MenuItem m5, MenuItem m6) {
        m1.setOnAction(event -> {
            mediaPlayer.stop();
            System.exit(0);
        });
        m2.setOnAction(event -> {
            mediaPlayer.play();
            m2.setDisable(true);
        });
        m3.setOnAction(event -> {
            mediaPlayer.pause();
            m2.setDisable(false);
        });
        m4.setOnAction(event -> {
            RulesView view = new RulesView();
            Main.window.setScene(view.RulesViewScene());
            Main.window.centerOnScreen();
        });
        m5.setOnAction(event -> {
            SetupGameView view = new SetupGameView();
            Main.window.setScene(view.SetupGameView());
            Main.window.centerOnScreen();
        });
        m6.setOnAction(event -> {
            AuthorView view = new AuthorView();
            Main.window.setScene(view.AuthorViewScene());
            Main.window.centerOnScreen();
        });
    }

    private Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY, double rotate) {
        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radiusX + 1); // a full 360 degree circle path simulation
        arcTo.setY(centerY - radiusY);
        arcTo.setSweepFlag(false);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(radiusX);
        arcTo.setRadiusY(radiusY);
        arcTo.setXAxisRotation(rotate);

        Path path = PathBuilder.create().elements(new MoveTo(centerX - radiusX, centerY - radiusY), arcTo, new ClosePath()).build();
        path.setStroke(Color.WHITE);
        path.getStrokeDashArray().setAll(5d, 5d);
        return path;
    }

    private Group setCircleAnimation() {
        Group group = new Group();
        Path path = createEllipsePath(400, 200, 320, 120, 0);
        ArrayList<PathTransition> pathList = new ArrayList<>();
        ArrayList<Circle> circleList = new ArrayList<>();

        int time;
        time = 4000;
        for (int i = 0; i < 100; i++, time += 120) {
            PathTransition ptt = new PathTransition();
            Circle circle = new Circle(10);

            ptt.setDuration(Duration.millis(time));
            ptt.setPath(path);
            ptt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            ptt.setCycleCount(Timeline.INDEFINITE);
            ptt.setAutoReverse(false);
            ptt.setInterpolator(Interpolator.LINEAR);
            pathList.add(ptt);

            circle.setFill(Color.rgb(0, 120, 180));
            circleList.add(circle);
            pathList.get(i).setNode(circleList.get(i));
            group.getChildren().addAll(circleList.get(i));

            FadeTransition ft2 = new FadeTransition(Duration.millis(3000), circleList.get(i));
            ft2.setFromValue(1.0);
            ft2.setToValue(0.001);
            ft2.setCycleCount(Timeline.INDEFINITE);
            ft2.setAutoReverse(true);
            ft2.play();
            ptt.play();
        }
        group.getChildren().addAll(path);
        return group;
    }
}

