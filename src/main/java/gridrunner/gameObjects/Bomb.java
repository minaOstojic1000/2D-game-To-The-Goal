package gridrunner.gameObjects;

import gridrunner.interfaces.IEnemy;
import gridrunner.playerClasses.Player;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.List;

public class Bomb extends Circle implements IEnemy {

    public Bomb(double radius,
                double startX, double startY,
                double endX, double endY,
                double flightDuration, double pauseDuration, double startDelay,
                Color fill, Color stroke) {
        super(radius, fill);
        this.setStroke(stroke);
        setAnimation(startX, startY, endX, endY, flightDuration, pauseDuration, startDelay);

        setVisible(false);
    }

    private void setAnimation(double startX, double startY, double endX, double endY,
                              double flightDuration, double pauseDuration, double startDelay) {

        double cX = startX + 0.25 * (endX - startX), cY = startY - 0.5 * Math.abs(endX - startX);
        QuadCurve curve = new QuadCurve(startX, startY, cX, cY, endX, endY);

        PathTransition movement = new PathTransition(
                Duration.seconds(flightDuration), curve, this
        );

        movement.setOnFinished(event-> {
            hide();
        });

        PauseTransition pause = new PauseTransition(Duration.seconds(pauseDuration));

        pause.setOnFinished(event-> {
            show();
        });

        SequentialTransition loop = new SequentialTransition(
                movement,
                pause
        );

        loop.setCycleCount(SequentialTransition.INDEFINITE);

        PauseTransition startDelayPause = new PauseTransition(Duration.seconds(startDelay));
        startDelayPause.setOnFinished(event -> {
            show();
            loop.play();
        });

        startDelayPause.play();
    }

    private void show() {
        this.setVisible(true);
        IEnemy.addEnemy(this);
    }

    private void hide() {
        this.setVisible(false);
        IEnemy.removeEnemy(this);
    }

    @Override
    public List<Shape> getShapes() {

        return List.of(this);
    }

    @Override
    public void affect(Player player) {
        player.takeDamage(player.getRestLives());
    }
}
