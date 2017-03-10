package sample.sprites;

import sample.Layer;
import sample.Vector2D;

import java.util.*;

/**
 * Created by steve on 3/7/17.
 */
public abstract class Animal extends Sprite {

    protected Random random;

    public enum Sex {Male, Female}
    public State state;
    protected double currentEnergy;       // Energy remaining before dying

    public enum State {
        SEARCHING_FOOD, STARVING_SEARCHING_FOOD,
        SEARCHING_MATE,
        EATING, MATING,
        DEAD_EATEN, DEAD_STARVED, DEAD_OLD
    }

    public Sex sex;

    public Animal(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        this.random = new Random();
        this.currentEnergy = getStartingHealth();
        this.sex = random.nextBoolean() ? Sex.Female : Sex.Male;
    }

    public Animal(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Sex sex) {
        super(layer, location, velocity, acceleration, width, height);
        random = new Random();
        this.sex = sex;
        this.currentEnergy = getStartingHealth();
    }

    public abstract double getFoodDetectionRadius();
    public abstract double getFoodEatRadius();
    public abstract double getMateDetectionRadius();
    public abstract double getMateRadius();
    public abstract double getMaxSpeed();
    public abstract double getMaxHealth();
    public abstract double getStartingHealth();

    @Override
    public void move(){
        super.move();
        useEnergy();
        //updateState();
    }

    public abstract void updateState();

    public void eat(Food food){
        currentEnergy += food.beEaten();
        if(currentEnergy > getMaxHealth()) {
            currentEnergy = getMaxHealth();
        }
    }

    public void seekMate(List<? extends Sprite> others){
        seekClosest(others, getMateDetectionRadius(), getMateRadius(), true);
    }

    public void seekClosest(List<? extends Sprite> others, double radiusDetect, double radiusInteract, boolean mating ){
        if(others.isEmpty()) return;
        ArrayList<SpriteDistance> reduced = new ArrayList<>();

        others.forEach(sprite -> {
            Vector2D vector2D = Vector2D.subtract(sprite.getLocation(), location);
            double magnitude = vector2D.magnitude();
            if(magnitude <= radiusDetect && magnitude > 0 ){
                reduced.add(new SpriteDistance(magnitude, sprite, vector2D));
            }
        });

        reduced.sort(new DistanceComparator());

        SpriteDistance[] sprites = new SpriteDistance[reduced.size()];

        //Arrays.parallelSort(reduced.toArray(sprites), new DistanceComparator());

        if(reduced.isEmpty()) return;
        SpriteDistance closest = reduced.get(0);

        if(closest.distance < radiusInteract){
            interactWith(closest.sprite);
        } else {
            closest.vector.normalize();
            closest.vector.multiply(getMaxSpeed());
            Vector2D steer = Vector2D.subtract(closest.vector, velocity);
            applyForce(steer);
        }
    }

    class SpriteDistance{
        double distance;
        Vector2D vector;
        Sprite sprite;

        public SpriteDistance(double distance, Sprite sprite, Vector2D vector){
            this.distance = distance;
            this.sprite = sprite;
            this.vector = vector;
        }
    }

    public double getCurrentEnergy(){
        return this.currentEnergy;
    }


    public void seekFood(List<? extends Sprite> food){
        seekClosest(food, getFoodDetectionRadius(), getFoodEatRadius(), false);
    }

    public final void useEnergy(){
        this.currentEnergy -= getEnergyUsageRate();
        if(this.currentEnergy < 0) markForRemoval();
    }

    public final void useEnergyMating(){
        double newHealth = currentEnergy - 5*getEnergyUsageRate();
        if(newHealth < 0){
            newHealth = 2;
        }
        this.currentEnergy = newHealth;
    }

    class DistanceComparator implements Comparator<SpriteDistance> {
        @Override
        public int compare(SpriteDistance lhs, SpriteDistance rhs) {
            double mLhs = lhs.distance;
            double mRhs = rhs.distance;

            if (mRhs < mLhs)
                return 1;

            if (mLhs == mRhs)
                return 0;

            return -1;
        }
    }
}
