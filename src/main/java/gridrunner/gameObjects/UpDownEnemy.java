package gridrunner.gameObjects;

import gridrunner.playerClasses.CirclePlayer;
import gridrunner.interfaces.IEnemy;
import gridrunner.playerClasses.Player;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.List;

public class UpDownEnemy extends Rectangle implements IEnemy {

    private double speed;
    private double yDistance = 0;
    private TranslateTransition moving;

    UpDownEnemy(double width, double height, double speed,
                Color fillColor, Color strokeColor) {
        super(width, height);
        this.setFill(fillColor);
        this.setStroke(strokeColor);

        this.speed = speed;

        IEnemy.addEnemy(this);
    }

    UpDownEnemy(double width, double height, double speed,
                double positionX, double startY, double yDistance,
                Color fillColor, Color strokeColor) {
        this(width, height, speed, fillColor, strokeColor);

        this.yDistance = yDistance;

        this.getTransforms().add(
                new Translate(positionX, startY)
        );

        setAnimation();
    }

    public void setAnimation() {
        double duration = yDistance / speed;

        moving = new TranslateTransition(
                Duration.seconds(duration), this
        );

        moving.setByY(yDistance);
        moving.setAutoReverse(true);
        moving.setCycleCount(TranslateTransition.INDEFINITE);
        moving.play();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getYDistance() {
        return yDistance;
    }

    public void setPositionX(double positionX) {
        this.setTranslateX(positionX);
    }

    public void setStartY(double startY) {
        this.setTranslateY(startY);
    }

    public void setYDistance(double endY) {
        this.yDistance = endY;
    }

    public void startAnimation() {
        setAnimation();
    }

    @Override
    public List<Shape> getShapes() {
        return List.of(this);
    }
}
