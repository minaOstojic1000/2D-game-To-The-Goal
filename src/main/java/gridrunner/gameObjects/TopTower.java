package gridrunner.gameObjects;

import gridrunner.constants.Constants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopTower extends Rectangle {

    private List<Bomb> bombs;
    private double bombX, bombY;

    TopTower(double width, double height, double positionX, double positionY,
             Color fillColor, Color strokeColor, List<Rectangle> walls,
             int numOfBombs,
             double bombRadius, int bombMinFlightXD, int bombMaxFlightXD, boolean right,
             double bombFlightDuration, double bombPauseDuration, Color bombFill, Color bombStroke) {
        super(positionX, positionY, width, height);
        this.setFill(fillColor);
        this.setStroke(strokeColor);
        this.bombX = positionX + width / 2.;
        this.bombY = positionY + width / 2.;

        bombs = new ArrayList<>();
        createBombs(numOfBombs, bombRadius,
                bombMinFlightXD, bombMaxFlightXD, right,
                bombFlightDuration, bombPauseDuration,
                bombFill, bombStroke);

        if (walls == null)
            walls = new ArrayList<>();
        walls.add(this);
    }

    private void createBombs(int numOfBombs, double bombRadius, int bombMinFlightXD, int bombMaxFlightXD, boolean right,
                             double bombFlightDuration, double bombPauseDuration, Color bombFill, Color bombStroke) {
        Random rnd = new Random();

        int dir = (right) ? 1 : -1;

        for (int i = 0; i < numOfBombs; i++) {
            bombs.add(
                    new Bomb(
                            bombRadius,
                            bombX, bombY,
                            bombX + dir * (rnd.nextInt((bombMaxFlightXD - bombMinFlightXD) + 1) + bombMinFlightXD),
                            bombY,
                            bombFlightDuration,
                            bombPauseDuration * numOfBombs,
                            bombPauseDuration * i,
                            bombFill,
                            bombStroke
                    )
            );
        }
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
}
