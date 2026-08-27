package gridrunner.gameObjects;

import gridrunner.interfaces.IPowerUp;
import gridrunner.playerClasses.Player;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Coin extends Circle implements IPowerUp {

    static List<Coin> usedCoins = new ArrayList<>();

    Coin (double radius, double x, double y, Color fill, Color stroke) {
        super(radius, fill);
        this.setStroke(stroke);
        this.getTransforms().add(
                new Translate(x, y)
        );

        IPowerUp.addPowerUp(this);
    }

    private record IntPair(int a, int b) {}

    public static List<Coin> generateRandomCoins(String map[], int num,
                                                 double fieldWidth, double fieldHeight, double radius,
                                                 Color fill, Color stroke) {
        if (map.length < 3 || map[0].length() < 3)
            return null;
        List<Coin> coins = new ArrayList<>();
        int rowMin = 1, rowMax = map.length - 2;
        int colMin = 1, colMax = map[0].length() - 2;
        int row = 0, col = 0;
        Random rnd = new Random();
        List<IntPair> taken = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
            col = rnd.nextInt(colMax - colMin + 1) + colMin;
            IntPair newPair = new IntPair(row, col);
            while (map[row].charAt(col) != '.' || taken.contains(newPair)){
                row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
                col = rnd.nextInt(colMax - colMin + 1) + colMin;
            }
            taken.add(newPair);
            coins.add(new Coin(radius, fieldWidth * (col + 1.0/2), fieldHeight * (row + 1.0/2),
                    fill, stroke));
        }
        return coins;
    }
    /*
    @Override
    public boolean touchesPlayer(Player player) {

        Bounds playerToScene = player.localToScene(player.getBoundsInLocal());
        Bounds playerBounds = this.getParent().sceneToLocal(playerToScene);

        Point2D positionOfCoin = this.localToScene(this.getCenterX(), this.getCenterY());
        Point2D positionOfPlayer = player.localToScene(player.getCenterX(), player.getCenterY());

        double distance = positionOfCoin.distance(positionOfPlayer);
        return distance <= this.getRadius() + playerBounds.getWidth() / 2.0;
    }
    */
    @Override
    public List<Shape> getShapes() {
        return List.of(this);
    }

    @Override
    public void affect(Player player) {
        player.claimReward(1);
        this.setVisible(false);
        usedCoins.add(this);
    }

    public static void cleanUsed() {
        for (Coin c : usedCoins) {
            IPowerUp.removePowerUp(c);
        }
        usedCoins.clear();
    }
}
