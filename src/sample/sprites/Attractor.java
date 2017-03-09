package sample.sprites;

import sample.Layer;
import sample.Vector2D;

/**
 * Created by steve on 3/6/17.
 */
public class Attractor extends Sprite {

    public Attractor(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public void interactWith(Sprite other) {

    }

    @Override
    public double getEnergyUsageRate() {
        return 0;
    }


}