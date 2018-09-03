package cs102.pr.pack;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StarterView {

    public StarterView() {}

    private static Scene scene;
    public static final Color color = Color.rgb(0, 120, 180);
    public static Font font = new Font("Helvetica",18);

    public Scene StarterViewScene() {

        VBox vbox = new VBox(16);
        final Button start = new Button("Start");
        final Button exit = new Button("Exit");
        Text text = new Text("Checkers Game");

        start.setPrefSize(140.0, 30.0);
        exit.setPrefSize(140.0, 30.0);
        start.setFont(font);
        exit.setFont(font);
        vbox.setAlignment(Pos.CENTER);
        text.setFont(font);
        text.setFill(color);

        ImageView imageView = new ImageView(getClass().getResource("assets/starterPic.jpg").toExternalForm());
        String image = StarterView.class.getResource("assets/starterBackgroundPic.jpg").toExternalForm();
        vbox.setStyle("-fx-background-image: url('" + image + "'); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
        vbox.getChildren().addAll(imageView, text, start, exit);

        scene = new Scene(vbox, 720, 480);
        setActions(start, exit);
        return scene;
    }

    private void setActions(Button start, Button exit) {

        start.setOnAction(event -> {

            MainMenuView view = new MainMenuView();
            Main.window.setScene(view.MainMenuViewScene());
        });

        exit.setOnAction(event -> System.exit(0));
    }
}

