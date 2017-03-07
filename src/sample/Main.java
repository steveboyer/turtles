package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.sprites.Attractor;
import sample.sprites.Sprite;
import sample.sprites.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    static Random random = new Random();

    Layer playfield;

    List<Attractor> allAttractors = new ArrayList<>();
    List<Vehicle> allVehicles = new ArrayList<>();

    AnimationTimer gameLoop;

    Vector2D mouseLocation = new Vector2D( 0, 0);

    Scene scene;

    MouseGestures mouseGestures = new MouseGestures();

    @Override
    public void start(Stage primaryStage) {

        // create containers
        BorderPane root = new BorderPane();

        // playfield for our Sprites
        playfield = new Layer( Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        // entire game as layers
        Pane layerPane = new Pane();

        layerPane.getChildren().addAll(playfield);

        root.setCenter(layerPane);

        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        // add content
        prepareGame();

        // add mouse location listener
        addListeners();

        // run animation loop
        startGame();
    }

    private void prepareGame() {

        // add vehicles
        for( int i = 0; i < Settings.VEHICLE_COUNT; i++) {
            addVehicles();
        }

        // add attractors
        for( int i = 0; i < Settings.ATTRACTOR_COUNT; i++) {
            addAttractors();
        }


    }

    private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // currently we have only 1 attractor
                Attractor attractor = allAttractors.get(0);

                // seek attractor location, apply force to get towards it
                allVehicles.forEach(vehicle -> {

                    vehicle.seek( attractor.getLocation());

                });

                // move sprite
                allVehicles.forEach(Sprite::move);

                // update in fx scene
                allVehicles.forEach(Sprite::display);
                allAttractors.forEach(Sprite::display);

            }
        };

        gameLoop.start();

    }

    /**
     * Add single vehicle to list of vehicles and to the playfield
     */
    private void addVehicles() {

        Layer layer = playfield;

        // random location
        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();

        // dimensions
        double width = 50;
        double height = width / 2.0;

        // create vehicle data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create sprite and add to layer
        Vehicle vehicle = new Vehicle( layer, location, velocity, acceleration, width, height);

        // register vehicle
        allVehicles.add(vehicle);

    }

    private void addAttractors() {

        Layer layer = playfield;

        // center attractor
        double x = layer.getWidth() / 2;
        double y = layer.getHeight() / 2;

        // dimensions
        double width = 100;
        double height = 100;

        // create attractor data
        Vector2D location = new Vector2D( x,y);
        Vector2D velocity = new Vector2D( 0,0);
        Vector2D acceleration = new Vector2D( 0,0);

        // create attractor and add to layer
        Attractor attractor = new Attractor( layer, location, velocity, acceleration, width, height);

        // register sprite
        allAttractors.add(attractor);

    }

    private void addListeners() {

        // capture mouse position
        scene.addEventFilter(MouseEvent.ANY, e -> {
            mouseLocation.set(e.getX(), e.getY());
        });

        // move attractors via mouse
        for( Attractor attractor: allAttractors) {
            mouseGestures.makeDraggable(attractor);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
