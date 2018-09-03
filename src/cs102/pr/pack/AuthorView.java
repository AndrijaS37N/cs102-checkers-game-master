package cs102.pr.pack;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class AuthorView {

    public AuthorView() {}

    private static Scene scene;

    public Scene AuthorViewScene() {

        StackPane layout = new StackPane();
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox(60);
        Button back = new Button("Back");
        Text text = new Text("A checkers game by Andrija Djuric.");

        back.setFont(StarterView.font);
        back.setPrefSize(160.0, 40.0);
        vbox.setAlignment(Pos.CENTER);
        pane.setBottom(back);

        text.setFill(Color.WHEAT);
        text.setId("fancy-mancy");
        vbox.getChildren().addAll(text);
        layout.getChildren().addAll(vbox, pane);

        scene = new Scene(layout, 1024, 640);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        layout.getStyleClass().add("general-layout");
        layout.getStyleClass().add("author-view");

        setActions(back);
        return scene;
    }

    private void setActions(Button back) {

        back.setOnAction(event -> {

            Main.window.setScene(MainMenuView.scene);
            Main.window.centerOnScreen();
        });
    }
}
