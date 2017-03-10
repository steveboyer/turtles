package sample.sprites;

import javafx.scene.Node;
import sample.Layer;
import sample.Settings;
import sample.Utils;
import sample.Vector2D;

/**
 * Created by steve on 3/7/17.
 */
public class Rabbit extends Animal implements Food {
    public Rabbit(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    public Rabbit(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Sex sex) {
        super(layer, location, velocity, acceleration, width, height, sex);
    }

    @Override
    public double getFoodDetectionRadius() {
        return Settings.RABBIT_FOOD_DETECTION_RADIUS;
    }


    @Override
    public void updateState() {
        State nextState;
        switch (state){
            case SEARCHING_FOOD:
                // if found food
                    // eat
                // else
                    // if hungry
                        // search for food
                    // else
                        // search for mate
                nextState = State.SEARCHING_FOOD;
                break;
            case STARVING_SEARCHING_FOOD:
                // Energy usage rate down
                // if found food
                    // eat
                // else
                    // search for food
                nextState = State.SEARCHING_FOOD;
                break;
            case SEARCHING_MATE:
                // if found mate
                    // fuck
                // else
                    // H* ? SEARCHING_FOOD : SEARCHING_MATE
                nextState = State.SEARCHING_FOOD;
                break;
            case EATING:
                // Set velocity 0 until done
                // Energy rate 0 until done
                // Set velocity normal
                // H*
                nextState = State.SEARCHING_FOOD;
                break;
            case MATING:
                // Set velocity 0 until done
                // Set velocity normal
                // H*
                nextState = State.SEARCHING_FOOD;
                break;
            case DEAD_EATEN:
                nextState = State.DEAD_EATEN;
                break;
            case DEAD_OLD:
                nextState = State.DEAD_OLD;
                break;
            case DEAD_STARVED:
                nextState = State.DEAD_STARVED;
                break;
            default:
                throw new RuntimeException();
        }
        state = nextState;
    }

    @Override
    public Node createView(){
        return Utils.createRabbitImageView((int) width, sex);
    }

    @Override
    public void interactWith(Sprite sprite){
        if(sprite instanceof Rabbit){
            if(((Rabbit) sprite).sex != sex){

                if(random.nextDouble() > 0.969 ){
                    Settings.NEW_RABBITS += 1;
                }
                useEnergyMating();
                Rabbit r = (Rabbit) sprite;
                r.useEnergyMating();
            }
        } else if (sprite instanceof Food){
            eat((Food) sprite);
        }
    }

    @Override
    public double getFoodEatRadius() {
        return Settings.RABBIT_FOOD_EAT_RADIUS;
    }

    @Override
    public double getMateDetectionRadius() {
        return Settings.RABBIT_MATE_DETECTION_RADIUS;
    }

    @Override
    public double getMateRadius() {
        return Settings.RABBIT_MATE_RADIUS;
    }

    @Override
    public double getMaxSpeed() {
        return Settings.RABBIT_MAX_SPEED;
    }

    @Override
    public double getMaxHealth() {
        return Settings.RABBIT_MAX_ENERGY;
    }

    @Override
    public double getStartingHealth() {
        return Settings.RABBIT_STARTING_ENERGY;
    }

    @Override
    public double beEaten() {
        this.markForRemoval();
        return getFoodEnergy();
    }

    @Override
    public double getEnergyUsageRate() {
        return Settings.RABBIT_ENERGY_RATE;
    }

    @Override
    public double getFoodEnergy() {
        return Settings.RABBIT_AS_FOOD_ENERGY;
    }

}
