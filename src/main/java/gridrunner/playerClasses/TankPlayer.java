package gridrunner.playerClasses;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.List;

public class TankPlayer extends Player {

    Rectangle body, pipe;
    Circle circleDecor;

    double relativePipeX, relativePipeY;

    Rotate rotation;

    public TankPlayer(double width, double height,
                         double positionX, double positionY,
                         double speed, Color fill, Color stroke, int numOfLives) {
        super(positionX, positionY, speed, numOfLives, fill, stroke,
                new Rectangle(width, height),
                new Circle(height / 2.0),
                new Rectangle(0.75 * width, 0.25 * height)
        );

        body = (Rectangle)getChildren().get(0);
        pipe = (Rectangle)getChildren().get(2);
        circleDecor = (Circle)getChildren().get(1);

        pipe.setStroke(fill);

        relativePipeX = width * 0.75;
        relativePipeY = (height - 0.25 * height) * 0.5;

        pipe.getTransforms().add(
                new Translate(relativePipeX, relativePipeY)
        );
        circleDecor.getTransforms().add(
                new Translate(width / 2.0, height / 2.0)
        );

        createDecorLines(stroke, relativePipeX, relativePipeY);

        this.rotation = new Rotate(0, width / 2.0, height / 2.0);

        this.getTransforms().addAll(
                rotation
        );
    }

    private void createDecorLines(Color color, double pX, double pY) {
        double w = pipe.getWidth(), h = pipe.getHeight();
        Line line1 = new Line(pX, pY, pX + w, pY);
        Line line2 = new Line(pX, pY + h, pX + w, pY + h);
        Line line3 = new Line(pX + w, pY, pX + w, pY + h);

        double strokeWidth = pipe.getStrokeWidth();

        line1.setStrokeWidth(strokeWidth);
        line2.setStrokeWidth(strokeWidth);
        line3.setStrokeWidth(strokeWidth);

        line1.setStroke(color);
        line2.setStroke(color);
        line3.setStroke(color);

        this.addShapes(line1, line2, line3);
    }

    protected void moveAndResolve ( double dx, double dy, List<Rectangle> walls ) {

        double newX = this.position.getX() + dx;
        double newY = this.position.getY() + dy;
        boolean clear = true;
        for (Rectangle wall : walls) {
            if (!overlaps (wall, newX, newY)) {
                continue;
            }

            clear = false;
        }

        if (!clear) {
            newX -= dx;
            newY -= dy;
        }

        this.position.setX(newX);
        this.position.setY(newY);

        if (dx < 0)
            this.rotation.setAngle(180);
        else if (dx > 0)
            this.rotation.setAngle(0);
        else if (dy < 0)
            this.rotation.setAngle(270);
        else if (dy > 0)
            this.rotation.setAngle(90);
    }

    @Override
    protected Circle createImmunityCircle() {
        return new Circle(body.getWidth() / 2., body.getHeight() / 2., body.getWidth());
    }

    private boolean overlaps(Rectangle wall, double newX, double newY) {
        Bounds inMySystem = getBoundsInMySistem(wall);

        double width = body.getWidth();
        double height = body.getHeight();

        double rotate = this.getRotate();

        if (Math.abs(rotate % 180) == 90) {
            width = body.getHeight();
            height = body.getWidth();
        }

        Bounds potentialBound = new BoundingBox(newX, newY, width, height);

        return potentialBound.intersects(inMySystem);
    }


    @Override
    public boolean touches ( Rectangle goal ) {

        Bounds inMySystem = getBoundsInMySistem(goal);

        double goalMinX = inMySystem.getMinX();
        double goalMinY = inMySystem.getMinY();

        double left  = goalMinX;
        double right = goalMinX + goal.getWidth ( );
        double up    = goalMinY;
        double down  = goalMinY + goal.getHeight ( );

        double posX = this.position.getX();
        double posY = this.position.getY();
        boolean horizontal = ( posX ) >= left && ( posX + body.getWidth() ) <= right;
        boolean vertical   = ( posY ) >= up && ( posY + body.getHeight() ) <= down;

        return horizontal && vertical;
    }
}
