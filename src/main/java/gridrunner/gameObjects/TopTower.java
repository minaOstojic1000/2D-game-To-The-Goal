package gridrunner.gameObjects;

import gridrunner.constants.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TopTower extends Rectangle {

    private Bomb bomb;

    TopTower(double width, double height, double positionX, double positionY,
             Color fillColor, Color strokeColor, List<Rectangle> walls) {
        super(positionX, positionY, width, height);
        this.setFill(fillColor);
        this.setStroke(strokeColor);

        bomb = new Bomb(
                Constants.BOMB_RADIUS,
                positionX, positionY,
                positionX - Constants.BOMB_FLIGHT_XD,
                positionY,
                Constants.FLIGHT_BOMB_DURATION,
                Constants.PAUSE_BOMB_DURATION,
                Constants.BOMB_FILL,
                Constants.BOMB_STROKE
        );

        if (walls == null)
            walls = new ArrayList<>();
        walls.add(this);
    }

    public Bomb getBomb() {
        return bomb;
    }
}
