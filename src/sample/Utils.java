package sample;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import sample.sprites.Animal;

/**
 * Created by steve on 3/6/17.
 */
public class Utils {

    public static double map(double value, double currentRangeStart, double currentRangeStop, double targetRangeStart, double targetRangeStop) {
        return targetRangeStart + (targetRangeStop - targetRangeStart) * ((value - currentRangeStart) / (currentRangeStop - currentRangeStart));
    }

    /**
     * Create an imageview of a right facing arrow.
     * @param size The width. The height is calculated as width / 2.0.
     * @return
     */
    public static ImageView createArrowImageView( double size) {

        return createArrowImageView(size, size / 2.0, Color.BLUE, Color.BLUE.deriveColor(1, 1, 1, 0.3), 1);

    }

    /**
     * Create an imageview of a right facing arrow.
     * @param width
     * @param height
     * @return
     */
    public static ImageView createArrowImageView(double width, double height, Paint stroke, Paint fill, double strokeWidth) {
        return new ImageView( createArrowImage(width, height, stroke, fill, strokeWidth));
    }

    public static ImageView createGrassImageView(double x, double y){
        return new ImageView(createGrassImage(3, 30, x, y, Color.GREENYELLOW, Color.GREEN, 0));
    }

    public static ImageView createFoxImageView(double size, Animal.Sex sex){
        return createArrowImageView(size, size/2, Color.BLACK, Color.RED, 1);
    }

    public static ImageView createFoxImageView(double size){
        return createArrowImageView(size, size/2, Color.BLACK, Color.RED, 1);
    }

    public static ImageView createRabbitImageView(double size, Animal.Sex sex){
        return createArrowImageView(size, size/2, Color.BLACK, Color.GREY, 1);
    }

    public static ImageView createRabbitImageView(double size){
        return createArrowImageView(size, size/2, Color.BLACK, Color.GREY, 1);
    }

//    public static ImageView createGrassImageView(double width, double height, double x, double y, Paint stroke, Paint fill, double strokeWidth){
//        return new ImageView(createGrassImage(width, height, x, y, stroke, fill, strokeWidth ));
//    }

    /**
     * Create an image of a right facing arrow.
     * @param width
     * @param height
     * @return
     */
    public static Image createArrowImage(double width, double height, Paint stroke, Paint fill, double strokeWidth) {

        WritableImage wi;

        double arrowWidth = width - strokeWidth * 2;
        double arrowHeight = height - strokeWidth * 2;

        Polygon arrow = new Polygon( 0, 0, arrowWidth, arrowHeight / 2, 0, arrowHeight); // left/right lines of the arrow
        arrow.setStrokeLineJoin(StrokeLineJoin.MITER);
        arrow.setStrokeLineCap(StrokeLineCap.SQUARE);
        arrow.setStroke(stroke);
        arrow.setFill(fill);
        arrow.setStrokeWidth(strokeWidth);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) width;
        int imageHeight = (int) height;

        wi = new WritableImage( imageWidth, imageHeight);
        arrow.snapshot(parameters, wi);

        return wi;

    }


    public static Image createGrassImage(double width, double height, double x, double y, Paint stroke, Paint fill, double strokeWidth){
        WritableImage wi;

        double grassWidth = width - strokeWidth*2;
        double grassHeight = height - strokeWidth*2;

        Polygon grass = new Polygon(x, y, x + grassWidth, y, x + grassWidth, y + grassHeight, x, y + grassHeight);
        grass.setStrokeLineJoin(StrokeLineJoin.MITER);
        grass.setStrokeLineCap(StrokeLineCap.SQUARE);
        grass.setStroke(stroke);
        grass.setFill(fill);
        grass.setStrokeWidth(strokeWidth);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) width;
        int imageHeight = (int) height;

        wi = new WritableImage(imageWidth, imageHeight);
        grass.snapshot(parameters, wi);

        return wi;
    }

}