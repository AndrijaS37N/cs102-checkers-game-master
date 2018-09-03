package cs102.pr.pack;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SetupGameView {

    public SetupGameView() {}

    private static Scene scene;
    private TextField textField1;
    private TextField textField2;
    private ComboBox time;
    private VBox vbox;
    private Text text;
    // could be a bool
    private int pressed;

    public Scene SetupGameView() {

        GridPane grid = new GridPane();
        vbox = new VBox(14);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(12);
        grid.setVgap(10);

        vbox.setPrefSize(400, 400);
        vbox.setAlignment(Pos.CENTER);

        // block of GUI elements --------------------------------------------
        Label lab1 = new Label("Player 1: ");
        grid.setConstraints(lab1, 0, 0);

        textField1 = new TextField("");
        grid.setConstraints(textField1, 1, 0);

        Label lab2 = new Label("Player 2: ");
        grid.setConstraints(lab2, 0, 1);

        textField2 = new TextField();
        grid.setConstraints(textField2, 1, 1);

        Button begin = new Button("Begin");
        grid.setConstraints(begin, 2, 8);

        Button back = new Button("Back");
        grid.setConstraints(back, 3, 8);

        Label lab3 = new Label("Set match time (seconds): ");
        grid.setConstraints(lab3, 0, 2);

        time = new ComboBox();
        time.getItems().addAll("30", "45", "60");
        time.setValue("30");
        grid.setConstraints(time, 1, 2);
        // ---------------------------------------------------- // end of block

        scene = new Scene(vbox, 500, 300);
        grid.setPadding(new Insets(8, 8, 8, 8));
        vbox.setPadding(new Insets(8, 8, 8, 8));

        Text label = new Text("Enter player names and then press\nbegin to start the game.");
        label.setFill(StarterView.color);
        label.setFont(StarterView.font);

        text = new Text("\nMust enter names bigger then 2 characters.");
        text.setFill(Color.RED);
        text.setFont(StarterView.font);

        FlowPane flow = new FlowPane();
        begin.setPrefSize(140, 30);
        back.setPrefSize(140, 30);

        begin.setFont(StarterView.font);
        back.setFont(StarterView.font);
        lab1.setFont(StarterView.font);
        lab2.setFont(StarterView.font);

        flow.setAlignment(Pos.CENTER);
        flow.setHgap(10);

        flow.getChildren().addAll(back, begin);
        vbox.getChildren().addAll(label, grid, flow);
        grid.getChildren().addAll(lab1, textField1, lab2, textField2, lab3, time);

        pressed = 0;
        setActions(back, begin);
        return scene;
    }

    private void setActions(Button back, Button begin) {

        back.setOnAction(event -> {

            Main.window.setScene(MainMenuView.scene);
            Main.window.centerOnScreen();
        });

        begin.setOnAction(event -> {

            try {

                while (textField1.getText().length() < 3 || textField2.getText().length() < 3)
                    throw new NoNamesException("Console log: Minimum name length is 2 characters.");

                String name1 = textField1.getText();
                String name2 = textField2.getText();
                String timeString;
                timeString = (String) time.getValue();
                int timeMinutes = Integer.parseInt(timeString);

                GameView view = new GameView(name1, name2, timeMinutes);

                Main.window.setScene(view.GameViewScene());
                Main.window.centerOnScreen();

            } catch (Exception ex) {

                System.err.println("Console log: Must enter player names longer then 2 characters."); // another print
                System.out.println("Error origin: " + ex);
                pressed++;
                if (pressed == 1) vbox.getChildren().add(text);  // exception on scene
            }
        });
    }
}
