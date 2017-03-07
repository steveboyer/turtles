package sample.old;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * Created by steve on 3/5/17.
 */
public class Ball {
    private float x, y, lastX, lastY;
    private int width, height;
    private float xVelocity, yVelocity;
    private float speed;
    private GamePanel gamePanel;
    private float interpolation;
    private float ballX, ballY, lastBallX, lastBallY;
    private int ballWidth = 25;
    private float ballXVel, ballYVel;
    private float ballSpeed;
    private int fps = 60;
    private int frameCount = 0;
    private int lastDrawX, lastDrawY;
    private Circle circle;

    public Ball(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        width = (int) (Math.random() * 50 + 10);
        height = (int) (Math.random() * 50 + 10);
        x = (float) (Math.random() * (gamePanel.getWidth() - width) + width/2);
        y = (float) (Math.random() * (gamePanel.getHeight() - height) + height/2);
        lastX = x;
        lastY = y;
        xVelocity = (float) Math.random() * speed*2 - speed;
        yVelocity = (float) Math.random() * speed*2 - speed;

        this.circle = new Circle(25, Color.RED);
    }

    public void update(){
        lastBallX = ballX;
        lastBallY = ballY;

        ballX += ballXVel;
        ballY += ballYVel;

        if (ballX + ballWidth/2 >= gamePanel.getWidth())
        {
            ballXVel *= -1;
            ballX = (float)gamePanel.getWidth() - ballWidth/2;
            ballYVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
        }
        else if (ballX - ballWidth/2 <= 0)
        {
            ballXVel *= -1;
            ballX = ballWidth/2;
        }

        if (ballY + ballWidth/2 >= gamePanel.getHeight())
        {
            ballYVel *= -1;
            ballY = (float)gamePanel.getHeight() - ballWidth/2;
            ballXVel = (float) Math.random() * ballSpeed*2 - ballSpeed;
        }
        else if (ballY - ballWidth/2 <= 0)
        {
            ballYVel *= -1;
            ballY = ballWidth/2;
        }


        circle.setCenterX(ballY);
        circle.setCenterY(ballX);
    }

    public Shape getShape(){

        return this.circle;
    }
}
