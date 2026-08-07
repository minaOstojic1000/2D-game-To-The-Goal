package gridrunner.gameObjects;

import gridrunner.Player;
import gridrunner.interfaces.IEnemy;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class RotatingEnemy extends Group implements IEnemy {

    private double speed;
    private Rectangle axle;
    private Rectangle stick;

    RotatingEnemy(double axleWidth,
                  double stickWidth, double stickHeight, double speed,
                  Color fillColor, Color strokeColor) {

        this.speed = speed;
        axle = new Rectangle(axleWidth, axleWidth, fillColor);
        stick = new Rectangle(stickWidth, stickHeight, fillColor);

        axle.setStroke(strokeColor);
        stick.setStroke(strokeColor);

        stick.setTranslateX((axleWidth - stickWidth) / 2.0);
        stick.setTranslateY(-(stickHeight - axleWidth) / 2.0);

        axle.getTransforms().add(
                new Rotate(45, axleWidth / 2.0, axleWidth / 2.0)
        );

        setRotation();

        this.getChildren().addAll(stick, axle);
        IEnemy.addEnemy(this);
    }

    RotatingEnemy(double axleWidth,
                  double stickWidth, double stickHeight,
                  double positionX, double positionY, double speed,
                  Color fillColor, Color strokeColor) {

        this(axleWidth, stickWidth, stickHeight, speed, fillColor, strokeColor);

        this.getTransforms().add(
                new Translate(positionX, positionY)
        );
    }

    private void setRotation() {
        double duration = 360.0 / speed;
        RotateTransition rotation = new RotateTransition(Duration.seconds(duration), stick);
        rotation.setByAngle(360);
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.play();
    }

    public void setPositionX(double positionX) {
        this.setTranslateX(positionX);
    }

    public void setPositionY(double positionY) {
        this.setTranslateY(positionY);
    }

    @Override
    public boolean touchesPlayer(Player player) {
        Shape intersectA = Shape.intersect(axle, player);
        Shape intersectS = Shape.intersect(stick, player);
        if (intersectA.getBoundsInLocal().getWidth() != -1 ||
                intersectS.getBoundsInLocal().getWidth() != -1) {
            return true;
        }
        return false;
    }
}
