package sample;

/**
 * Created by steve on 3/6/17.
 */
public class Settings {

    public static final double SCENE_WIDTH = 1280;
    public static final double SCENE_HEIGHT = 600;



    public static final int FOX_COUNT = 8;
    public static final int RABBIT_COUNT = 8;
    public static final int GRASS_COUNT = 300;

    public static final double SPRITE_MAX_SPEED = 2;
    public static final double SPRITE_MAX_FORCE = 0.1;

    public static final double FOX_MAX_SPEED = 2;
    // @TODO Force, other speeds

    // Radii
    public static final double FOX_FOOD_DETECTION_RADIUS = 35;
    public static final double FOX_FOOD_EAT_RADIUS = 2;
    public static final double FOX_MATE_DETECTION_RADIUS = 30;
    public static final double FOX_MATE_RADIUS = 3;

    // Energies
    public static final double FOX_MAX_ENERGY = 7;
    public static final double FOX_STARTING_ENERGY = 4.29;
    public static final double FOX_ENERGY_RATE  = 0.011;
    public static final double FOX_ENERGY_TO_MATE = 3.11;


    public static final double RABBIT_MAX_SPEED = 1.25;
    // @TODO Force, other speeds

    // Radii
    public static final double RABBIT_FOOD_DETECTION_RADIUS = 30;
    public static final double RABBIT_FOOD_EAT_RADIUS = 5.0;
    public static final double RABBIT_MATE_DETECTION_RADIUS = 30;
    public static final double RABBIT_MATE_RADIUS = 3;

    // Energies
    public static final double RABBIT_MAX_ENERGY = 5.0;
    public static final double RABBIT_STARTING_ENERGY = 3.0;
    public static final double RABBIT_ENERGY_RATE = 0.0022;
    public static final double RABBIT_AS_FOOD_ENERGY = 4;
    public static final double RABBIT_ENERGY_TO_MATE = 1.8;

    public static final double GRASS_AS_FOOD_ENERGY = 0.9;

    // Track births
    public static volatile int NEW_RABBITS = 0;
    public static volatile int NEW_FOXES = 0;
    public static volatile int NEW_GRASS = 0;

    // distance at which the sprite moves slower towards the target
    public static final double SPRITE_SLOW_DOWN_DISTANCE = 100;

    // Controls running of game loop
    public static volatile boolean running = false;

    // Stuff from demo
//    public static final int ATTRACTOR_COUNT = 1;
//    public static final int VEHICLE_COUNT = 10;
}