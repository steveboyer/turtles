package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

import java.util.Calendar;

/**
 * Created by steve on 3/5/17.
 */
public class ControlBar extends HBox {
    Button start_pause, reset, quit;
    Slider slider1, slider2;

    public ControlBar(){
        super(15);

        start_pause = new Button("START");
        start_pause.setPrefWidth(75);
        start_pause.setPrefHeight(20);
        start_pause.setOnMouseClicked(event -> start_pause());

        reset = new Button("RESET");
        reset.setPrefWidth(75);
        reset.setPrefHeight(20);
        reset.setOnMouseClicked(event -> reset());

        quit = new Button("QUIT");
        quit.setPrefWidth(75);
        quit.setPrefHeight(20);
        quit.setOnMouseClicked(event -> quit());

        slider1 = new Slider();
        slider2 = new Slider();

        getChildren().addAll(start_pause, reset, quit, slider1, slider2);
        setPrefHeight(50);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(0,0,0,15));
        setStyle("-fx-background-color: grey; -fx-border-color: black; -fx-border-insets: 0,1,1,1");
    }

    public void start_pause(){
        Settings.running = !Settings.running;
        Calendar last = Calendar.getInstance();
        if(!Settings.running) {
            for (Calendar cal : Main.stamps) {
                if(last != null) {
                    //System.out.println(cal.getTimeInMillis() - last.getTimeInMillis());
                    last = cal;
                }
            }
        }
        start_pause.setText(Settings.running ? "PAUSE" : "START");
    }

    public void reset(){
        //paused = !paused;
        Settings.running = !Settings.running;


    }

    public void quit(){

    }
}
