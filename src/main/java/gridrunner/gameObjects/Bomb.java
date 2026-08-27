package gridrunner.gameObjects;

import gridrunner.interfaces.IEnemy;
import gridrunner.playerClasses.Player;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.List;

public class Bomb extends Circle implements IEnemy {

    public Bomb(double radius,
                double startX, double startY,
                double endX, double endY, double flightDuration, double pauseDuration,
                Color fill, Color stroke) {
        super(radius, fill);
        this.setStroke(stroke);
        setAnimation(startX, startY, endX, endY, flightDuration, pauseDuration);

        IEnemy.addEnemy(this);
    }

    private void setAnimation(double startX, double startY, double endX, double endY,
                              double flightDuration, double pauseDuration) {

        double cX = startX + 0.25 * (endX - startX), cY = startY + 0.5 * (endX - startX);
        QuadCurve curve = new QuadCurve(startX, startY, cX, cY, endX, endY);

        PathTransition movement = new PathTransition(
                Duration.seconds(flightDuration), curve, this
        );

        PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

        movement.setOnFinished(event-> {
                    this.setVisible(false);
                    IEnemy.removeEnemy(this);
                }
        );

        pause.setOnFinished(event-> {
                    this.setVisible(true);
                    IEnemy.addEnemy(this);
                }
        );

        SequentialTransition loop = new SequentialTransition(
                movement,
                pause
        );

        loop.setCycleCount(SequentialTransition.INDEFINITE);
        loop.play();
    }

    @Override
    public List<Shape> getShapes() {

        return List.of(this);
    }

    @Override
    public void affect(Player player) {
        IEnemy.super.affect(player);
    }
}
