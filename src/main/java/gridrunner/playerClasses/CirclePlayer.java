package gridrunner.playerClasses;

import gridrunner.Input;
import gridrunner.gameObjects.Heart;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.List;

public class CirclePlayer extends Player {

    protected Circle circle;
    protected double radius;
    protected double centerX, centerY;

    public CirclePlayer(double radius,
                        double positionX, double positionY,
                        double speed,
                        Color fillColor, Color strokeColor,
                        int numOfLives) {
        super (positionX + radius, positionY + radius, speed, numOfLives, fillColor , strokeColor, new Circle(radius));
        circle = (Circle)getChildren().get(0);
        this.setStrokeWidth ( radius * 0.03 );

        this.centerX = positionX + radius;
        this.centerY = positionY + radius;
        this.radius = radius;
    }

    public CirclePlayer(double radius,
                        double positionX, double positionY,
                        double speed,
                        Color fillColor, Color strokeColor,
                        List<Heart> myHearts) {
        this(radius, positionX, positionY, speed, fillColor, strokeColor, myHearts.size());
        this.myHearts = List.copyOf(myHearts);
        currLife = this.myHearts.size() - 1;
    }

    protected void moveAndResolve ( double dx, double dy, List<Rectangle> walls ) {
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

        return dx * dx + dy * dy <= radius * radius;
    }

    public boolean touches ( Rectangle goal ) {

        Bounds inMySystem = getBoundsInMySistem(goal);

        double goalMinX = inMySystem.getMinX();
        double goalMinY = inMySystem.getMinY();

        double left  = goalMinX;
        double right = goalMinX + goal.getWidth ( );
        double up    = goalMinY;
        double down  = goalMinY + goal.getHeight ( );

        boolean horizontal = ( this.centerX - radius ) >= left && ( this.centerX + radius ) <= right;
        boolean vertical   = ( this.centerY - radius ) >= up && ( this.centerY + radius ) <= down;

        return horizontal && vertical;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    protected void resetState() {
        super.resetState();
        centerX = this.startPosition.getX();
        centerY = this.startPosition.getY();
    }

    @Override
    protected Circle createImmunityCircle() {
        return new Circle(radius * 1.25);
    }
}