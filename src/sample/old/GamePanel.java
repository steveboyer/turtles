package sample.old;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Created by steve on 3/5/17.
 */
public class GamePanel extends Pane {

    private volatile float interpolation;
    private float ballX, ballY, lastBallX, lastBallY;
    private int ballWidth = 25, ballHeight = 25;
    private float ballXVel, ballYVel;
    private float ballSpeed;
    private int fps = 60;
    private int frameCount = 0;
    private int lastDrawX, lastDrawY;

    private Circle g;

    public GamePanel(){
        super();
        g = new Circle();
        g.setCenterX(0);
        g.setCenterY(0);
        g.setFill(Color.RED);
        getChildren().addAll(g);

        System.out.println("GamePanel()");
    }

    public void setInterpolation(float interpolation){
        this.interpolation = interpolation;
    }

    public void paintComponent() {
        System.out.println("paintComponent");
//        int drawX = (int) ((ballX - lastBallX) * interpolation + lastBallX - ballWidth / 2);
//        int drawY = (int) ((ballY - lastBallY) * interpolation + lastBallY - ballWidth / 2);
//
//        System.out.println("drawX: " + drawX + ", drawY: " + drawY);
//        if(drawX != lastDrawX || drawY != lastDrawY ){
//            lastDrawX = drawX;
//            lastDrawY = drawY;
//            g.setCenterX(drawX);
//            g.setCenterY(drawY);
//
//
//            frameCount++;
//        }
        g.setCenterX(lastDrawX-1);
        g.setCenterY(lastDrawY-1);
        g.setFill(Color.BLACK);
        //g.fillRect(lastDrawX-1, lastDrawY-1, ballWidth+2, ballHeight+2);
        //g.fillRect(5, 0, 75, 30);

        //g.setColor(Color.RED);
        int drawX = (int) ((ballX - lastBallX) * interpolation + lastBallX - ballWidth/2);
        int drawY = (int) ((ballY - lastBallY) * interpolation + lastBallY - ballHeight/2);
        //g.fillOval(drawX, drawY, ballWidth, ballHeight);

        lastDrawX = drawX;
        lastDrawY = drawY;

        System.out.println("FPS: " + fps);
        System.out.println("drawX: " + drawX + ", drawY: " + drawY);
        frameCount++;
        //getChildren().addAll(g);
    }

//    public void start(){
//        while(Game.running){
//            paintComponent();
//        }
//    }
}
