package gridrunner;

import gridrunner.gameObjects.Heart;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class Player extends Circle {

    private Translate position, startPosition;
    private double centerX, centerY, radius;
    int currLife;
    List<Heart> myHearts;
    int points = 0;
    double speed;

    public Player ( double radius,
                    double positionX, double positionY,
                    double speed,
                    Color fillColor, Color strokeColor,
                    int numOfLives) {
        super ( radius, fillColor );
        super.setStroke ( strokeColor );
        super.setStrokeWidth ( radius * 0.03 );

        this.centerX = positionX + radius;
        this.centerY = positionY + radius;
        this.radius  = radius;
        this.speed = speed;
        this.currLife = numOfLives - 1;

        this.position = new Translate (this.centerX, this.centerY);

        this.startPosition = new Translate(this.centerX, this.centerY);

        super.getTransforms ( ).addAll (
                this.position
        );
    }

    public Player ( double radius,
                    double positionX, double positionY,
                    double speed,
                    Color fillColor, Color strokeColor,
                    List<Heart> myHearts) {
        this(radius, positionX, positionY, speed, fillColor, strokeColor, myHearts.size());
        this.myHearts = List.copyOf(myHearts);
        currLife = this.myHearts.size() - 1;
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

    private void resetState() {
        this.centerX = startPosition.getX();
        this.centerY = startPosition.getY();
        this.position.setX(centerX);
        this.position.setY(centerY);
    }

    private void moveAndResolve ( double dx, double dy, List<Rectangle> walls ) {
        this.centerX += dx;
        this.centerY += dy;
        boolean clear = true;
        for ( Rectangle wall : walls ) {
            if ( !overlaps ( wall ) ) {
                continue;
            }

            clear = false;
        }

        if ( !clear ) {
            this.centerX -= dx;
            this.centerY -= dy;
        }

        this.position.setX ( this.centerX );
        this.position.setY ( this.centerY );
    }

    public boolean overlaps ( Rectangle rectangle ) {

        Bounds inMySystem = getBoundsInMySistem(rectangle);

        double wallMinX = inMySystem.getMinX();
        double wallMinY = inMySystem.getMinY();

        double left  = wallMinX;
        double right = wallMinX + rectangle.getWidth ( );
        double up    = wallMinY;
        double down  = wallMinY + rectangle.getHeight ( );

        // looking for rectangle point that is closest to center of circle(player)

        double closestX = Math.max ( left, Math.min ( this.centerX, right ) );
        double closestY = Math.max ( up, Math.min ( this.centerY, down ) );

        double dx = this.centerX - closestX;
        double dy = this.centerY - closestY;

        return dx * dx + dy * dy <= this.radius * this.radius;
    }

    public boolean touches ( Rectangle goal ) {

        Bounds inMySystem = getBoundsInMySistem(goal);

        double goalMinX = inMySystem.getMinX();
        double goalMinY = inMySystem.getMinY();

        double left  = goalMinX;
        double right = goalMinX + goal.getWidth ( );
        double up    = goalMinY;
        double down  = goalMinY + goal.getHeight ( );

        boolean horizontal = ( this.centerX - this.radius ) >= left && ( this.centerX + radius ) <= right;
        boolean vertical   = ( this.centerY - this.radius ) >= up && ( this.centerY + radius ) <= down;

        return horizontal && vertical;
    }

    private Bounds getBoundsInMySistem(Rectangle rectangle) {
        Bounds rectToScene = rectangle.localToScene(rectangle.getBoundsInLocal());
        return this.getParent().sceneToLocal(rectToScene);
    }

    public void setLifeHearts(List<Heart> hearts) {
        this.myHearts = List.copyOf(hearts);
        currLife = this.myHearts.size() - 1;
    }

    public void takeDamage(int numOfLives) {
        resetState();
        for (int i = 0; i < numOfLives; i++) {
            if (currLife < 0)
                return;
            myHearts.get(currLife).loseColor();
            currLife--;
        }
    }

    public void addLives(int numOfLives) {
        for (int i = 0; i < numOfLives; i++) {
            if (currLife > myHearts.size() - 2)
                return;
            myHearts.get(currLife + 1).getColor();
            currLife++;
        }
    }

    public void claimReward(int numOfPoints) {
        points += numOfPoints;
    }

    public boolean isAlive() {
        return currLife >= 0;
    }

    public int getPoints() { return points; }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public int getNumOfLives() {
        return currLife + 1;
    }
}