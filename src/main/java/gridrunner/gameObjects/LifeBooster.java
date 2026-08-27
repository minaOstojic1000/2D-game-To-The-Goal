package gridrunner.gameObjects;

import gridrunner.interfaces.IPowerUp;
import gridrunner.playerClasses.Player;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LifeBooster extends Heart implements IPowerUp {

    private PauseTransition myTimer;
    private static List<LifeBooster> expiredHearts = new ArrayList<>();

    LifeBooster(double size, double positionX, double positionY, Color fillColor, Color strokeColor, double duration) {
        super(size, positionX, positionY, fillColor, strokeColor);

        setMyTimer(duration);

        IPowerUp.addPowerUp(this);
    }

    public static LifeBooster generateRandomLifeBooster(String map[],
                                                 double fieldWidth, double fieldHeight, double size,
                                                 Color fill, Color stroke, double duration) {
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

        LifeBooster life = new LifeBooster(size,
                fieldWidth * (col + 1.0/2), fieldHeight * (row + 1.0/2),
                fill, stroke, duration);

        life.myTimer.play();

        return life;
    }

    private void setMyTimer(double seconds) {
        myTimer = new PauseTransition(Duration.seconds(seconds));

        myTimer.setOnFinished(event -> {
            this.setVisible(false);
            expiredHearts.add(this);
        });
    }

    @Override
    public void affect(Player player) {
        player.addLives(1);
        this.setVisible(false);
        expiredHearts.add(this);
    }

    @Override
    public List<Shape> getShapes() {
        return List.of(this);
    }

    public static void cleanExpired() {
        for (LifeBooster life : expiredHearts)
            IPowerUp.removePowerUp(life);
        expiredHearts.clear();
    }

}
