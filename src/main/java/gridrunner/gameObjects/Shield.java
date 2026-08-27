package gridrunner.gameObjects;

import gridrunner.constants.Constants;
import gridrunner.interfaces.IPowerUp;
import gridrunner.playerClasses.Player;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shield extends Path implements IPowerUp {

    private final double immunityDuration;
    private PauseTransition myTimer;
    private static List<Shield> expiredShields = new ArrayList<>();

    public Shield(double width, double positionX, double positionY,
                  double immunityDuration, double shieldDuration,
                  Color fill, Color stroke) {
        this.immunityDuration = immunityDuration;

        createHexagon(width, fill, stroke);

        setMyTimer(shieldDuration);

        IPowerUp.addPowerUp(this);

        this.getTransforms().add(
                new Translate(positionX, positionY)
        );
    }

    private void createHexagon(double width, Color fillColor, Color strokeColor) {
        double ri = width / 2.;
        double x1 = ri, y1 = 0;
        double x2 = x1 - 0.5 * ri, y2 = y1 + 0.866 * ri;
        double x3 = x1 - 1.5 * ri, y3 = y1 + 0.866 * ri;
        double x4 = x1 - 2.0 * ri, y4 = y1;
        double x5 = x1 - 1.5 * ri, y5 = y1 - 0.866 * ri;
        double x6 = x1 - 0.5 * ri, y6 = y5;
        this.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6),
                new ClosePath()
        );
        this.setFill(fillColor);
        this.setStroke(strokeColor);
    }

    public static Shield generateRandomShields(String map[],
                                                        double fieldWidth, double fieldHeight, double width,
                                                        Color fill, Color stroke,
                                                        double immunityDuration, double shieldDuration) {
        if (map.length < 3 || map[0].length() < 3)
            return null;

        int rowMin = 1, rowMax = map.length - 2;
        int colMin = 1, colMax = map[0].length() - 2;
        int row = 0, col = 0;
        Random rnd = new Random();
        row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
        col = rnd.nextInt(colMax - colMin + 1) + colMin;
        while (map[row].charAt(col) != '.'){
            row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
            col = rnd.nextInt(colMax - colMin + 1) + colMin;
        }

        Shield shield = new Shield(width,
                fieldWidth * (col + 1.0/2), fieldHeight * (row + 1.0/2),
                immunityDuration, shieldDuration, fill, stroke);

        shield.myTimer.play();

        return shield;
    }

    private void setMyTimer(double seconds) {
        myTimer = new PauseTransition(Duration.seconds(seconds));

        myTimer.setOnFinished(event -> {
            this.setVisible(false);
            expiredShields.add(this);
        });
    }

    @Override
    public void affect(Player player) {
        player.makeImmune(immunityDuration);
        this.setVisible(false);
        expiredShields.add(this);
    }

    @Override
    public List<Shape> getShapes() {
        return List.of(this);
    }

    public static void cleanExpired() {
        for (Shield shield : expiredShields)
            IPowerUp.removePowerUp(shield);
        expiredShields.clear();
    }

    public static List<Shield> getExpiredShields() {
        return expiredShields;
    }

    public static void setExpiredShields(List<Shield> expiredShields) {
        Shield.expiredShields = expiredShields;
    }
}
