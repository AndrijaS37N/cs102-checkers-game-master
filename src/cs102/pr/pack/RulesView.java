package cs102.pr.pack;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class RulesView {

    public RulesView() {}

    public static Scene scene;
    private final Hyperlink link = new Hyperlink("ItsYourTurn.com");
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    public Scene RulesViewScene() {
        
        StackPane layout = new StackPane();
        VBox vbox = new VBox(16);
        Button back = new Button("Back");
        Text text = new Text("Not sure how to play? Here's some advice! Check out this link:");

        back.setFont(StarterView.font);
        back.setPrefSize(160.0, 40.0);
        vbox.setAlignment(Pos.CENTER);

        text.setFill(Color.WHEAT);
        text.setFont(StarterView.font);
        text.setLineSpacing(6);
        link.setFont(StarterView.font);
        link.setTextFill(Color.LIGHTBLUE);

        vbox.getChildren().addAll(text, link, browser, back);
        layout.getChildren().add(vbox);

        setActions(back, link);
        scene = new Scene(layout, 1024, 768, Color.LIGHTGREEN);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        layout.getStyleClass().add("general-layout");
        layout.getStyleClass().add("rules-view");
        return scene;
    }

    private void setActions(Button back, Hyperlink link) {
        back.setOnAction(event -> {

            Main.window.setScene(MainMenuView.scene);
            Main.window.centerOnScreen();
        });

        link.setOnAction(event -> webEngine.load("http://www.itsyourturn.com/t_helptopic2030.html"));
    }
}
