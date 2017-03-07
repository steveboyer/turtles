package sample.old;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by steve on 3/4/17.
 */
public class Animal extends LifeForm {

    public Animal(){
        super();
        this.shape = new Circle(20, Color.BLACK);
        shape.setTranslateX(40);
        shape.setTranslateY(40);
    }





}
