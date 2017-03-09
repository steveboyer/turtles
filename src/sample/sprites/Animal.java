package sample.sprites;

import sample.Layer;
import sample.Vector2D;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by steve on 3/7/17.
 */
public abstract class Animal extends Sprite {

    protected Random random;

    public enum Sex {Male, Female}
    public enum State { SEARCHING_FOOD,
                        SEARCHING_MATE,
                        EATING, MATING,
                        EATEN, STARVED,
                        STARVING}


    public Sex sex;

    public Animal(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
        random = new Random();
    }

    public Animal(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, Sex sex) {
        super(layer, location, velocity, acceleration, width, height);
        random = new Random();
        this.sex = sex;
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
        updateState();
    }

    public abstract void updateState();

    public void eat(Food food){
        currentEnergy += food.beEaten();
        if(currentEnergy > getMaxHealth()) {
            currentEnergy = getMaxHealth();
        }
    }

    public void seekMate(List<? extends Sprite> others){
        seekClosest(others, getMateDetectionRadius(), getMateRadius(),1);
    }

    public void seekClosest(List<? extends Sprite> others, double radiusDetect, double radiusInteract, int x){
        if(others.isEmpty()) return;

        others.sort(new DistanceComparator());
        Sprite[] sprites = new Sprite[others.size()];
        Arrays.parallelSort(others.toArray(sprites), new DistanceComparator());
        Vector2D shortest = Vector2D.subtract(sprites[0].getLocation(), location);
        double d = shortest.magnitude();
        shortest.normalize();

        if(d < radiusInteract){
            try {
                interactWith(others.get(x));
            } catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("All deads");
            }
        } else if(d < radiusDetect){
            shortest.multiply(getMaxSpeed());
            Vector2D steer = Vector2D.subtract(shortest, velocity);
            applyForce(steer);
        }
    }

    public void seekFood(List<? extends Sprite> food){
        seekClosest(food, getFoodDetectionRadius(), getFoodEatRadius(), 0);
    }

    class DistanceComparator implements Comparator<Sprite> {
        @Override
        public int compare(Sprite lhs, Sprite rhs) {
            double mLhs = Vector2D.subtract(lhs.getLocation(), location).magnitude();
            double mRhs = Vector2D.subtract(rhs.getLocation(), location).magnitude();

            if (mRhs < mLhs)
                return 1;

            if (mLhs == mRhs)
                return 0;

            return -1;
        }
    }
}
