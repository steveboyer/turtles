package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Created by steve on 3/5/17.
 */
public class ControlBar extends HBox {
    private Button start_pause, reset, quit;
    private Slider slider1, slider2;
    private Text foxes = new Text(), rabbits = new Text(), grass = new Text();

    public ControlBar(){
        super(15);

        start_pause = new Button("START");
        start_pause.setPrefWidth(65);
        start_pause.setPrefHeight(20);
        start_pause.setOnMouseClicked(event -> start_pause());

        reset = new Button("RESET");
        reset.setPrefWidth(65);
        reset.setPrefHeight(20);
        reset.setOnMouseClicked(event -> reset());

        quit = new Button("QUIT");
        quit.setPrefWidth(65);
        quit.setPrefHeight(20);
        quit.setOnMouseClicked(event -> quit());

        slider1 = new Slider();
        slider2 = new Slider();

        Insets insets = new Insets(0, 0, 0, 15);

        slider1.setPadding(insets);
        slider1.setMin(0);
        slider1.setMax(Settings.FOX_MAX_ENERGY);
        slider1.setShowTickMarks(true);
        slider1.setMajorTickUnit(10);
        slider1.setMinorTickCount(20);

        slider2.setPadding(insets);
        slider2.setMin(0);
        slider2.setMax(Settings.RABBIT_MAX_ENERGY);
        slider2.setShowTickMarks(true);
        slider2.setMajorTickUnit(10);
        slider2.setMinorTickCount(20);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Mating min."
                );

        ComboBox comboBox_1 = new ComboBox<>(options);
        comboBox_1.setMaxHeight(15);
        ComboBox comboBox_2 = new ComboBox<>(options);
        comboBox_2.setMaxHeight(15);
        Text slider_1_name = new Text("Slider\t"),
                slider_2_name = new Text("Slider\t");
        Text slider_1_value = new Text("0000\t"),
                slider_2_value = new Text("0000\t");
        slider_1_name.setStroke(Color.WHITE);
        slider_2_name.setStroke(Color.WHITE);

        slider_1_value.setStroke(Color.WHITE);
        slider_2_value.setStroke(Color.WHITE);

        HBox label_slider_1 = new HBox(comboBox_1, slider_1_value, slider1);
        insets = new Insets(5,0,0,0);//15
        label_slider_1.setPadding(insets);
        HBox label_slider_2 = new HBox(comboBox_2, slider_2_value, slider2);
        label_slider_2.setPadding(insets);
        VBox sliders = new VBox(label_slider_1, label_slider_2);

        foxes.setStroke(Color.RED);
        rabbits.setStroke(Color.GREY);
        grass.setStroke(Color.GREEN);

        slider1.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            Settings.FOX_ENERGY_TO_MATE = new_val.doubleValue();
            slider_1_value.setText(String.format("%.5f", new_val));
        });

        slider2.valueProperty().addListener((ov, old_val, new_val) -> {
            slider_2_value.setText(String.format("%.5f", new_val));
        });

        updatePopulations(0, 0, 0);
        VBox vBox = new VBox(foxes, rabbits, grass);
        vBox.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(start_pause, reset, quit, sliders, vBox);
        setPrefHeight(50);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(0,0,0,15));
        setStyle("-fx-background-color: black; -fx-border-color: black; -fx-border-insets: 0,1,1,1");
    }

    // Toggle paused and update text
    public void start_pause(){
        Settings.running = !Settings.running;
        start_pause.setText(Settings.running ? "PAUSE" : "START");
    }

    public void reset(){
        //paused = !paused;
        //Settings.running = !Settings.running;
    }

    public void quit(){
        System.exit(1);
    }

    // Update text fields for each populaiton set
    public void updatePopulations(int numFoxes, int numRabbits, int numGrass){
        foxes.setText("FOXES:\t\t" + getPopString(numFoxes));
        rabbits.setText("RABBITS:\t\t" + getPopString(numRabbits));
        grass.setText("GRASS:\t\t" + getPopString(numGrass));
    }

    // Set the correct number of leading 0s
    public String getPopString(int num){
        if(num < 10){
            return "000" + Integer.toString(num);
        } else if (num < 100) {
            return "00" + Integer.toString(num);
        } else if (num < 1000) {
            return "0" + Integer.toString(num);
        } else {
            return Integer.toString(num);
        }
    }

}
