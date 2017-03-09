package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.sprites.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    static Random random = new Random();

    Layer playfield;

    List<Fox> foxes = new CopyOnWriteArrayList<>();
    List<Rabbit> rabbits = new CopyOnWriteArrayList<>();
    List<Grass> grass = new CopyOnWriteArrayList<>();
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

        // run animation loop
        startGame();
    }

    private void prepareGame() {

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
                    for(int i = 0; i < Settings.NEW_RABBITS; i++){
                        addRabbits();
                        Settings.NEW_RABBITS--;
                    }

                    for(int i = 0; i < Settings.NEW_FOXES; i++){
                        addFoxes();
                        Settings.NEW_FOXES--;
                    }

//                    double rate = Settings.GRASS_COUNT/(grass.size());

                    while(grass.size() < Settings.GRASS_COUNT &&
                            Settings.NEW_GRASS > 0 && random.nextDouble() > 0.85 ){
                        addGrass();
                        Settings.NEW_GRASS--;
                    }

                    if(!grass.isEmpty()) {
                        for (Grass g : grass) {
                            if (g.isToBeRemoved()) {
                                grass.remove(g);
                                playfield.getChildren().remove(g);
                            }
                        }
                    }

                    if(!rabbits.isEmpty()){
                        for(Rabbit r : rabbits){
                            if(r.isToBeRemoved()){
                                rabbits.remove(r);
                                playfield.getChildren().remove(r);
                            }
                        }
                    }

                    if(!foxes.isEmpty()) {
                        for (Fox f : foxes) {
                            if (f.isToBeRemoved()) {
                                foxes.remove(f);
                                playfield.getChildren().remove(f);
                            }
                        }
                    }


                    // seek attractor location, apply force to get towards it
                    //allVehicles.forEach(vehicle -> vehicle.seek( attractor.getLocation()));

                    // move sprite
                    //allVehicles.forEach(Sprite::move);
                    rabbits.forEach(animal -> {
                        if(animal.getCurrentEnergy() > Settings.RABBIT_ENERGY_TO_MATE){
                            animal.seekMate(rabbits);
                        } else {
                            animal.seekFood(grass);
                        }
                    });
                    rabbits.forEach(Sprite::move);

                    foxes.forEach(animal -> {
                        if(animal.getCurrentEnergy() > Settings.FOX_ENERGY_TO_MATE){
                            animal.seekMate(foxes);
                        } else {
                            animal.seekFood(rabbits);
                        }
                    });
                    foxes.forEach(Sprite::move);

                    if(random.nextDouble() > 0.8) System.out.println("foxes: " + foxes.size() +  " rabbits: " + rabbits.size() + " grass: " + grass.size());

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

    private void addGrass(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();
//        System.out.println("x: " + x + " y: " + y);

        double width = 6;
        double height = 6;

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

        double xv = random.nextBoolean() ? -1 * random.nextDouble() : random.nextDouble();
        double yv = random.nextBoolean() ? -1 * random.nextDouble() : random.nextDouble();

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(xv, yv);
        velocity.normalize();
        velocity.multiply(Settings.FOX_MAX_SPEED);
        Vector2D acceleration = new Vector2D(0,0);

        Fox f = new Fox(layer, location, velocity, acceleration, width, height, random.nextBoolean() ? Animal.Sex.Male : Animal.Sex.Female);
        foxes.add(f);
    }

    private void addRabbits(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();

        double width = 20;
        double height = 6;

        Vector2D location = new Vector2D(x, y);

        double xv = random.nextBoolean() ? -1 * random.nextDouble() : random.nextDouble();
        double yv = random.nextBoolean() ? -1 * random.nextDouble() : random.nextDouble();

        Vector2D velocity = new Vector2D(xv,yv);
        velocity.normalize();
        velocity.multiply(Settings.RABBIT_MAX_SPEED);
        Vector2D acceleration = new Vector2D(0,0);

        Rabbit r = new Rabbit(layer, location, velocity, acceleration, width, height, random.nextBoolean() ? Animal.Sex.Male : Animal.Sex.Female);
        rabbits.add(r);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
