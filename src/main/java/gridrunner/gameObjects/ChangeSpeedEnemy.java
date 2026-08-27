package gridrunner.gameObjects;

import gridrunner.interfaces.IPickup;
import gridrunner.playerClasses.CirclePlayer;
import gridrunner.constants.Constants;
import gridrunner.interfaces.IEnemy;
import gridrunner.playerClasses.Player;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

public class ChangeSpeedEnemy extends Rectangle implements IEnemy {

    private double acceleration;
    private boolean affected = false;
    private double oldSpeed;

    ChangeSpeedEnemy(double width, double height,
                     double positionX, double positionY,
                     double acceleration) {

        super(positionX, positionY, width, height);

        this.acceleration = acceleration;
        if (acceleration > 1) {
            this.setEffect(Constants.UP_SHADOW);
            this.setFill(Constants.RAISED_FILL_COLOR);
            this.setStroke(Constants.RAISED_STROKE_COLOR);
            this.setStrokeWidth(Constants.RAISED_STROKE_WIDTH);
        }
        else {
            this.setEffect(Constants.DOWN_SHADOW);
            this.setFill(Constants.SUNKEN_FILL_COLOR);
            this.setStroke(Constants.SUNKEN_STROKE_COLOR);
            this.setStrokeWidth(Constants.SUNKEN_STROKE_WIDTH);
        }
        IEnemy.addEnemy(this);
    }

    @Override
    public boolean touchesPlayer(Player player) {

        boolean touches = IEnemy.super.touchesPlayer(player);

        if (!touches && affected) {
            affected = false;
            player.setSpeed(oldSpeed);
        }

        return touches;
    }

    @Override
    public List<Shape> getShapes() {
        return List.of(this);
    }

    @Override
    public void affect(Player player) {
        if (!affected) {
            oldSpeed = player.getSpeed();
            player.setSpeed(oldSpeed * acceleration);
            affected = true;
        }
    }
}
