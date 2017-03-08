package sample;

/**
 * Created by steve on 3/6/17.
 */
public class Settings {

    public static double SCENE_WIDTH = 1280;
    public static double SCENE_HEIGHT = 720;

    public static int ATTRACTOR_COUNT = 1;
    public static int VEHICLE_COUNT = 10;

    public static int FOX_COUNT = 1;
    public static int RABBIT_COUNT = 5;
    public static int GRASS_COUNT = 1000;

    public static double SPRITE_MAX_SPEED = 2;
    public static double SPRITE_MAX_FORCE = 0.1;

    public static double FOX_MAX_SPEED = 2;
    public static double FOX_PATROL_SPEED = 1;
    public static double FOX_MAX_FORCE = 0.1;
    public static double FOX_FOOD_DETECTION_RADIUS = 5;

    public static double RABBIT_MAX_SPEED = 3;
    public static double RABBIT_MAX_FORCE = 0.3;
    public static double RABBIT_FOOD_DETECTION_RADIUS = 2;

    // distance at which the sprite moves slower towards the target
    public static double SPRITE_SLOW_DOWN_DISTANCE = 100;

    public static volatile boolean running = false;

}