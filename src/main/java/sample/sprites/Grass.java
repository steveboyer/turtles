package sample.sprites;

import sample.Layer;
import sample.Settings;
import sample.Vector2D;

/**
 * Created by steve on 3/7/17.
 */
public class Grass extends Sprite implements Food {
    public Grass(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public void interactWith(Sprite other) {

    }

    @Override
    public double getEnergyUsageRate() {
        return 0;
    }

    @Override
    public double getFoodEnergy() {
        return Settings.GRASS_AS_FOOD_ENERGY;
    }

//    @Override
//    public Node createView() {
//        return Utils.createGrassImageView((int)width/2, (int)height/2);
//    }

    @Override
    public double beEaten() {
        this.markForRemoval();
        Settings.NEW_GRASS++;
        return getFoodEnergy();
    }
}
