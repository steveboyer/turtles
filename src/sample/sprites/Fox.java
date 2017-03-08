package sample.sprites;

import javafx.scene.Node;
import sample.Layer;
import sample.Settings;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/7/17.
 */
public class Fox extends Animal {
    public Fox(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height){
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public double getFoodDetectionRadius() {
        return Settings.FOX_FOOD_DETECTION_RADIUS;
    }

    @Override
    public Node createView(){
        return Utils.createFoxImageView((int) width);
    }
}
