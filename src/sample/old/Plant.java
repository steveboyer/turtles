package sample.old;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Created by steve on 3/4/17.
 */
public class Plant extends LifeForm {

    public Plant(){
        super();
        this.shape = new Polygon(
                0.0, 0.0,
                        0.0, 10.0,
                        10.0, 10.0,
                        10.0, 0.0);


        shape.setFill(Color.GREEN);
    }
}
