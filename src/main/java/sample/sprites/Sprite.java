package sample.sprites;

import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.Layer;
import sample.Settings;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/6/17.
 */
public abstract class Sprite extends Region {
    protected volatile boolean remove;

    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;

    private double maxForce = Settings.SPRITE_MAX_FORCE;
    private double maxSpeed = Settings.SPRITE_MAX_SPEED;



    Node view;

    // view dimensions
    double width;
    double height;
    double centerX;
    double centerY;
    double radius;

    double angle;

    Layer layer = null;

    public abstract double getEnergyUsageRate();

    public Sprite( Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        this.layer = layer;

        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.width = width;
        this.height = height;
        this.centerX = width / 2;
        this.centerY = height / 2;

        this.view = createView();

        setPrefSize(width, height);

        // add view to this node
        getChildren().add( view);

        // add this node to layer
        layer.getChildren().add( this);
    }

    // Return default shape of a green circle
    public Node createView(){
        double radius = width / 2;

        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.GREEN);
        circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.3));

        return circle;
    }

    public abstract void interactWith(Sprite other);

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    public void move() {
        // set velocity depending on acceleration
        velocity.add(acceleration);

        // limit velocity to max speed
        velocity.limit(maxSpeed);

        // change location depending on velocity
        location.add(velocity );

        if(location.x < 0){
            location.x = location.x + Settings.SCENE_WIDTH;
        } else if(location.x > Settings.SCENE_WIDTH){
            location.x = location.x - Settings.SCENE_WIDTH;
        }

        if(location.y < 0){
            location.y = location.y + Settings.SCENE_HEIGHT;
        } else if(location.y > Settings.SCENE_HEIGHT){
            location.y = location.y - Settings.SCENE_HEIGHT;
        }

        // angle: towards velocity (ie target)
        angle = velocity.heading2D();

        // clear acceleration
        acceleration.multiply(0);
    }

    /**
     * Move sprite towards target
     */
    public void seek(Vector2D target) {

        Vector2D desired = Vector2D.subtract(target, location);

        // The distance is the magnitude of the vector pointing from location to target.

        double d = desired.magnitude();
        desired.normalize();

        // If we are closer than 100 pixels...
        if (d < Settings.SPRITE_SLOW_DOWN_DISTANCE) {

            // ...set the magnitude according to how close we are.
            double m = Utils.map(d, 0, Settings.SPRITE_SLOW_DOWN_DISTANCE, 0, maxSpeed);
            desired.multiply(m);

        }
        // Otherwise, proceed at maximum speed.
        else {
            desired.multiply(maxSpeed);
        }

        // The usual steering = desired - velocity
        Vector2D steer = Vector2D.subtract(desired, velocity);
        steer.limit(maxForce);

        applyForce(steer);
    }

    /**
     * Update node position
     */
    public void display() {
        relocate(location.x - centerX, location.y - centerY);

        setRotate(Math.toDegrees( angle));
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation( double x, double y) {
        location.x = x;
        location.y = y;
    }

    public void setLocationOffset( double x, double y) {
        location.x += x;
        location.y += y;
    }

    public void markForRemoval(){
        this.remove = true;
    }

    public void updateState(){

    }

    public boolean isToBeRemoved(){
        return this.remove;
    }
}