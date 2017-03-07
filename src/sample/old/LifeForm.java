package sample.old;

import javafx.scene.shape.Shape;

/**
 * Created by steve on 3/4/17.
 */
public class LifeForm {
    protected Shape shape;


    public LifeForm(){

    }

    public void setPosition(){
        shape.setTranslateY(15);
    }

    public void move(){

    }

    public Shape getShape(){
        return shape;
    }

}
