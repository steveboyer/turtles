package sample.sprites;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import sample.Layer;
import sample.Vector2D;
import sample.sprites.Sprite;

/**
 * Created by steve on 3/6/17.
 */
public class Attractor extends Sprite {

    public Attractor(Layer layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height) {
        super(layer, location, velocity, acceleration, width, height);
    }

    @Override
    public Node createView() {

        double radius = width / 2;

        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.GREEN);
        circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.3));

        return circle;
    }

}