package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.sprites.*;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {
    static Random random = new Random();
    static CopyOnWriteArrayList<Calendar> stamps;
    private Layer playfield;
    private List<Fox> foxes = new CopyOnWriteArrayList<>();
    private List<Rabbit> rabbits = new CopyOnWriteArrayList<>();
    private List<Grass> grass = new CopyOnWriteArrayList<>();
    private AnimationTimer gameLoop;
    private Scene scene;
    private Long last = (long)0;//Calendar.getInstance().getTimeInMillis();
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinHeight(680);

        // playfield for our Sprites
        playfield = new Layer( Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        // entire game as layers
        Pane layerPane = new Pane();
        layerPane.getChildren().addAll(playfield);
        layerPane.setStyle("-fx-border-color: black");

        // create containers
        BorderPane root = new BorderPane();
        root.setCenter(layerPane);
        root.setBottom(new ControlBar());

        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
        Main.stamps = new CopyOnWriteArrayList<>();

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
            addFoxes(0);
        }

        for (int i = 0; i < Settings.RABBIT_COUNT; i++){
            addRabbits(0);
        }
    }

    private void startGame() {

        // start_pause game
        gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                long time = now - last;
                if(Settings.running){
                    for(int i = 0; i < Settings.NEW_RABBITS; i++){
                        addRabbits(time);
                        Settings.NEW_RABBITS--;
                    };

                    for(int i = 0; i < Settings.NEW_FOXES; i++){
                        addFoxes(time);
                        Settings.NEW_FOXES--;
                    }

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

                    if(!foxes.isEmpty()) {
                        for (Fox f : foxes) {
                            if (f.isToBeRemoved()) {
                                foxes.remove(f);
                                playfield.getChildren().remove(f);
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

                    if(random.nextDouble() > 0.8)
                        System.out.println("foxes: " + foxes.size() +
                                " rabbits: " + rabbits.size() +
                                " grass: " + grass.size());

                    // update in fx scene
                    rabbits.forEach(Sprite::display);
                    foxes.forEach(Sprite::display);
                    grass.forEach(Sprite::display);
                }
                last = now;
            }
        };
        gameLoop.start();
    }

    private void addGrass(){
        Layer layer = playfield;

        double x = random.nextDouble() * layer.getWidth();
        double y = random.nextDouble() * layer.getHeight();

        double width = 6;
        double height = 6;

        Vector2D location = new Vector2D(x, y);
        Vector2D velocity = new Vector2D(0, 0);
        Vector2D acceleration = new Vector2D(0,0);

        Grass g = new Grass(layer, location, velocity, acceleration, width, height);

        grass.add(g);
    }

    private void addFoxes(long time){
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

        velocity.multiply(Settings.FOX_MAX_SPEED * (time > 0 ? time : 1));
        Vector2D acceleration = new Vector2D(0,0);

        Fox f = new Fox(layer, location, velocity, acceleration, width, height, random.nextBoolean() ? Animal.Sex.Male : Animal.Sex.Female);
        foxes.add(f);
    }

    private void addRabbits(long time){
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
        velocity.multiply(Settings.RABBIT_MAX_SPEED * (time > 0 ? time : 1));
        Vector2D acceleration = new Vector2D(0,0);

        Rabbit r = new Rabbit(layer, location, velocity, acceleration, width, height, random.nextBoolean() ? Animal.Sex.Male : Animal.Sex.Female);
        rabbits.add(r);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
