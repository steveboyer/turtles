package sample.sprites;

import javafx.scene.Node;
import sample.Layer;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/6/17.
 */
public class Vehicle extends Sprite {

    public Vehicle(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {
        return Utils.createArrowImageView( (int) width);
    }

}