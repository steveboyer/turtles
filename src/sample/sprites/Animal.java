package sample.sprites;

import sample.Layer;
import sample.Vector2D;

import java.util.List;

/**
 * Created by steve on 3/7/17.
 */
public abstract class Animal extends Sprite {
    protected double fedLevel;

    enum Sex {Male, Female}

    public Sex sex;

    public Animal(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    public void eat(Sprite food){
        food.beEaten();
    }

    public abstract double getFoodDetectionRadius();

    public void starve(){
        this.markForRemoval();
    }

    public void seekFood(List<? extends Sprite> food){
        Sprite foodItem = null;
        Vector2D min = null;
        Double minMag = null;

        for(Sprite g : food) {
            Vector2D distanceVector = Vector2D.subtract(g.getLocation(), location);
            double distance = distanceVector.magnitude();

            if(distance < this.getFoodDetectionRadius())
                distanceVector.normalize();
                if(minMag == null || distance < minMag){
                    minMag = distance;
                    min = distanceVector;
                    foodItem = g;
                }
        }

        if(min == null) { // there is no food nearby
            wander();
        } else if(minMag == 0) {
            eat(foodItem);
        } else {
            // Desired vector toward closest food
            Vector2D desired = Vector2D.subtract(min, location);

            // Steer toward food
            Vector2D steer = Vector2D.subtract(desired, velocity);
            applyForce(steer);
        }

    }

    public void wander(){

    }
}
