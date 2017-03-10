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

    public Fox(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Sex sex){
        super(layer, location, velocity, acceleration, width, height, sex);
    }

    @Override
    public double getFoodDetectionRadius() {
        return Settings.FOX_FOOD_DETECTION_RADIUS;
    }

    @Override
    public double getFoodEatRadius() {
        return Settings.FOX_FOOD_EAT_RADIUS;
    }

    @Override
    public double getMateDetectionRadius() {
        return Settings.FOX_MATE_DETECTION_RADIUS;
    }

    @Override
    public double getMateRadius() {
        return Settings.FOX_MATE_RADIUS;
    }

    @Override
    public double getMaxSpeed() {
        return Settings.FOX_MAX_SPEED;
    }

    @Override
    public double getMaxHealth() {
        return Settings.FOX_MAX_ENERGY;
    }

    @Override
    public double getStartingHealth() {
        return Settings.FOX_STARTING_ENERGY;
    }

    @Override
    public void updateState() {

    }

    @Override
    public Node createView(){
        return Utils.createFoxImageView((int) width, sex);
    }

    @Override
    public void interactWith(Sprite sprite) {
        if(sprite instanceof Fox){
            if(((Fox)sprite).sex != sex){
                if(random.nextDouble() > 0.9) {
                    Settings.NEW_FOXES += 1;
                }
                useEnergyMating();
                Fox f = (Fox)sprite;
                f.useEnergyMating();
            }
        } else if (sprite instanceof Food) {
            eat((Food)sprite);
        }
    }

    @Override
    public double getEnergyUsageRate() {
        return Settings.FOX_ENERGY_RATE;
    }

}
