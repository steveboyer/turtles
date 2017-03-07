package sample.old;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

/**
 * Created by steve on 3/5/17.
 */
public class ControlBar extends HBox {
    Button start, pause, quit;
    Slider slider1, slider2;
    GameController gameController;
    boolean running = false;
    boolean paused = false;

    public ControlBar(GameController gameController){
        super(15);

        this.gameController = gameController;

        start = new Button("Start");
        pause = new Button("Reset");
        quit = new Button("Quit");

        start.setPrefWidth(75);
        pause.setPrefWidth(75);
        quit.setPrefWidth(75);

        start.setPrefHeight(20);
        pause.setPrefHeight(20);
        quit.setPrefHeight(20);

        start.setOnMouseClicked(event -> start());
        pause.setOnMouseClicked(event -> pause());
        quit.setOnMouseClicked(event -> quit());

        slider1 = new Slider();
        slider2 = new Slider();

        getChildren().addAll(start, pause, quit, slider1, slider2);
        setPrefHeight(50);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(0,0,0,15));
        setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-insets: 0,1,1,1");
    }

    public void start(){
        running = !running;
        gameController.start(running);
        start.setText(running ? "Stop" : "Start");
    }

    public void pause(){
        paused = !paused;
        gameController.pause(paused);
        pause.setText(paused ? "Unpause" : "Pause");
    }

    public void quit(){
        gameController.quit();
    }
}
