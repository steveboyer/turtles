package sample.sprites;

import javafx.scene.Node;
import sample.Layer;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/7/17.
 */
public class Grass extends Sprite {
    private double energyValue = 1.0;
    private boolean alive = true;

    public Grass(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {
        return Utils.createGrassImageView((int)width/2, (int)height/2);
    }

    @Override
    public double beEaten() {
        this.remove = true;
        return energyValue;
    }

    public boolean toBeRemoved(){
        return this.remove;
    }
}
