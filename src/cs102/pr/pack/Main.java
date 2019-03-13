package cs102.pr.pack;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;

    @Override
    public void start(Stage window) {
        this.window = window;
        window.setTitle("Checkers Game");
        window.centerOnScreen();
        StarterView view = new StarterView();
        window.setScene(view.StarterViewScene());
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
