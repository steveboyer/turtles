package sample.sprites;

import javafx.scene.Node;
import sample.Layer;
import sample.Settings;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/7/17.
 */
public class Rabbit extends Animal {
    private double energyValue = 3;

    public Rabbit(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public double getFoodDetectionRadius() {
        return Settings.RABBIT_FOOD_DETECTION_RADIUS;
    }

    @Override
    public Node createView(){
        return Utils.createRabbitImageView((int) width);
    }


    @Override
    public double beEaten() {
        this.markForRemoval();
        return energyValue;
    }

}
