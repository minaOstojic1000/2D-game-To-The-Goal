package gridrunner.playerClasses;

import gridrunner.Input;
import gridrunner.constants.PlayersFeatures;
import gridrunner.gameObjects.Heart;
import javafx.animation.PauseTransition;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Player extends Group {

    protected Translate position, startPosition;

    protected List<Shape> shapes = new ArrayList<>();
    protected int currLife;
    protected List<Heart> myHearts;
    protected int points = 0;
    protected double speed;
    protected boolean immune = false;
    protected Circle immunityCircle = null;

    protected Player(Shape... shapes) {
        this.shapes.addAll(Arrays.asList(shapes));
        this.getChildren().addAll(shapes);
    }

    protected Player(double positionX, double positionY, double speed, int numOfLives,
                     Color fill, Color stroke, Shape... shapes) {
        this(shapes);
        for (Shape shape: this.shapes) {
            shape.setFill(fill);
            shape.setStroke(stroke);
        }
        this.speed = speed;
        this.currLife = numOfLives - 1;

        this.position = new Translate (positionX, positionY);

        this.startPosition = new Translate(positionX, positionY);

        super.getTransforms().addAll(
                this.position
        );
    }

    public void update(double dt, Input input, List<Rectangle> walls) {
        double dx = 0;
        double dy = 0;

        if (input.keyR()) {
            resetState();
            return;
        }

        if ( input.left ( ) )  { dx -= speed * dt; }
        if ( input.right ( ) ) { dx += speed * dt; }
        if ( input.up ( ) )    { dy -= speed * dt; }
        if ( input.down ( ) )  { dy += speed * dt; }

        // Keep consistent speed on diagonals
        if ( dx != 0 && dy != 0 ) {
            double factor = 1.0 / Math.sqrt(2.0);
            dx *= factor;
            dy *= factor;
        }

        // Resolve each axis independently to allow sliding along walls
        this.moveAndResolve ( dx, 0, walls );
        this.moveAndResolve ( 0, dy, walls );
    }

    protected abstract void moveAndResolve ( double dx, double dy, List<Rectangle> walls );

    protected void resetState() {
        this.position.setX(startPosition.getX());
        this.position.setY(startPosition.getY());
    }

    public void addLives(int numOfLives) {
        for (int i = 0; i < numOfLives; i++) {
            if (currLife > myHearts.size() - 2)
                return;
            myHearts.get(currLife + 1).getColor();
            currLife++;
        }
    }

    public void makeImmune(double seconds) {
        immune = true;
        if (immunityCircle == null) {
            immunityCircle = createImmunityCircle();
            immunityCircle.setFill(PlayersFeatures.IMMUNE_CIRCLE_FILL);
            immunityCircle.setStroke(PlayersFeatures.IMMUNE_CIRCLE_STROKE);
            immunityCircle.setStrokeWidth(PlayersFeatures.IMMUNE_CIRCLE_STROKE_WIDTH);
            immunityCircle.setEffect(PlayersFeatures.IMMUNE_CIRCLE_SHADOW);
            this.addShapes(immunityCircle);
        }
        PauseTransition timer = new PauseTransition(Duration.seconds(seconds));

        timer.setOnFinished(event -> {
            immune = false;
            immunityCircle.setVisible(false);
        });

        immunityCircle.setVisible(true);
        timer.play();
    }

    protected abstract Circle createImmunityCircle();

    public void takeDamage(int numOfLives) {
        if (immune)
            return;
        resetState();
        for (int i = 0; i < numOfLives; i++) {
            if (currLife < 0)
                return;
            myHearts.get(currLife).loseColor();
            currLife--;
        }
    }

    public void claimReward(int numOfPoints) {
        points += numOfPoints;
    }

    public boolean isAlive() {
        return currLife >= 0;
    }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public int getNumOfLives() {
        return currLife + 1;
    }

    public Translate getStartPosition() { return startPosition; }

    public Translate getPosition() { return position; }

    public int getCurrLife() { return currLife; }

    public List<Heart> getMyHearts() { return myHearts; }

    public void setLifeHearts(List<Heart> hearts) {
        this.myHearts = List.copyOf(hearts);
        currLife = this.myHearts.size() - 1;
    }

    protected void setFill(Color color) {
        for (Shape shape: shapes) {
            shape.setFill(color);
        }
    }
    protected void setStroke(Color color) {
        for (Shape shape: shapes) {
            shape.setStroke(color);
        }
    }

    protected void setStrokeWidth(double width) {
        for (Shape shape: shapes) {
            shape.setStrokeWidth(width);
        }
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    protected void addShapes(Shape... shapes) {
        this.shapes.addAll(Arrays.asList(shapes));
        this.getChildren().addAll(shapes);
    }

    protected Bounds getBoundsInMySistem(Rectangle rectangle) {
        Bounds rectToScene = rectangle.localToScene(rectangle.getBoundsInLocal());
        return this.getParent().sceneToLocal(rectToScene);
    }

    public abstract boolean touches(Rectangle goal);

    public static Player createPlayer(int playerNum, double fieldCenterX, double fieldCenterY) {
        if (playerNum < 0 || playerNum >= PlayersFeatures.PLAYERS.length)
            return null;
        Player newPlayer = null;
        PlayersFeatures.PlayerFeature features = PlayersFeatures.PLAYERS[playerNum];
        newPlayer = switch (playerNum) {
            case 0 -> new CirclePlayer(
                    features.getSizeDimension1(),
                    fieldCenterX - features.getSizeDimension1(),
                    fieldCenterY - features.getSizeDimension1(),
                    features.getSpeed(),
                    features.getFill(),
                    features.getStroke(),
                    features.getLives()
            );
            case 1 -> new ThornyCirclePlayer(
                    features.getSizeDimension1(),
                    fieldCenterX - features.getSizeDimension1(),
                    fieldCenterY - features.getSizeDimension1(),
                    features.getSpeed(),
                    features.getFill(),
                    features.getStroke(),
                    features.getLives()
            );
            case 2 -> new TankPlayer(
                    features.getSizeDimension1(),
                    features.getSizeDimension2(),
                    fieldCenterX - features.getSizeDimension1() / 2.,
                    fieldCenterY - features.getSizeDimension2() / 2.,
                    features.getSpeed(),
                    features.getFill(),
                    features.getStroke(),
                    features.getLives()
            );
            default -> newPlayer;
        };
        return newPlayer;
    }
}
