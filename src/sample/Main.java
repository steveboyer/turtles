package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.sprites.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {

    static Random random = new Random();

    Layer playfield;

    List<Attractor> allAttractors = new ArrayList<>();
    List<Vehicle> allVehicles = new ArrayList<>();
    List<Animal> foxes = new ArrayList<>();
    List<Animal> rabbits = new ArrayList<>();
    List<Grass> grass = new ArrayList<>();
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
        root.setBottom(new ControlBar());

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
            //addVehicles();
        }
//
//        // add attractors
        for( int i = 0; i < Settings.ATTRACTOR_COUNT; i++) {
            //addAttractors();
        }
////
//        for( int i = 0; i < Settings.VEHICLE_COUNT; i++){
//            addAnimals();
//        }
//
        for(int i = 0; i  < Settings.GRASS_COUNT; i++){
            addGrass();
        }

        for(int i = 0; i < Settings.FOX_COUNT; i++){
            addFoxes();
        }

        for (int i = 0; i < Settings.RABBIT_COUNT; i++){
            addRabbits();
        }
    }

    private void startGame() {

        // start game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if(Settings.running){
                    foxes.removeIf(Sprite::isToBeRemoved);
                    rabbits.removeIf(Sprite::isToBeRemoved);
                    grass.removeIf(Sprite::isToBeRemoved);

                    // seek attractor location, apply force to get towards it
                    //allVehicles.forEach(vehicle -> vehicle.seek( attractor.getLocation()));

                    // move sprite
                    //allVehicles.forEach(Sprite::move);
                    rabbits.forEach(animal -> animal.seekFood(grass));
                    rabbits.forEach(Sprite::move);

                    foxes.forEach(animal -> animal.seekFood(rabbits));
                    foxes.forEach(Sprite::move);

                    // update in fx scene
                    //allVehicles.forEach(Sprite::display);
                    //allAttractors.forEach(Sprite::display);
                    rabbits.forEach(Sprite::display);
                    foxes.forEach(Sprite::display);
                    grass.forEach(Sprite::display);
                }
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

//        System.out.println("x: " + x + " y: " + y);
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

    private void addAnimals(){
        Layer layer = playfield;

//        double x = layer.getWidth()/2;
//        double y = layer.getHeight()/2;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();
        //System.out.println("x: " +  x + " y: " + y);

        double width = 10;
        double height = width;

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(random.nextInt(20), random.nextInt(20));
        Vector2D acceleration = new Vector2D(random.nextInt(20), random.nextInt(20));

        Animal a = new Fox(layer, location, velocity, acceleration, width, height);

        //animals.add(a);
    }

    private void addGrass(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();
//        System.out.println("x: " + x + " y: " + y);

        double width = 4;
        double height = 20;

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(0, 0);
        Vector2D acceleration = new Vector2D(0,0);

        Grass g = new Grass(layer, location, velocity, acceleration, width, height);

        grass.add(g);
    }

    private void addFoxes(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();

        double width = 30;
        double height = 6;

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(0, 0);
        Vector2D acceleration = new Vector2D(0,0);

        Fox f = new Fox(layer, location, velocity, acceleration, width, height);
        foxes.add(f);
    }

    private void addRabbits(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();

        double width = 20;
        double height = 6;

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(0, 0);
        Vector2D acceleration = new Vector2D(0,0);

        Rabbit r = new Rabbit(layer, location, velocity, acceleration, width, height);
        rabbits.add(r);
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
