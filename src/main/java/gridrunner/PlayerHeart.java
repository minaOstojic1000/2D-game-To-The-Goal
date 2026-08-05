package gridrunner;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;

public class PlayerHeart extends Path {

    PlayerHeart(double size, double positionX, double positionY,
                Color fillColor, Color strokeColor) {
        makeHeart(size);
        this.getTransforms().add (
                new Translate(positionX, positionY)
        );
        setFill(fillColor);
        setStroke(strokeColor);
    }

    private void makeHeart(double size) {

        double p = size/2;

        double sX = 0, sY = -p, eX = 0, eY = p, mX = p, mY = 0;
        double cX1 = (1.0/3) * p, cY1 = -(4.0/2) * p, cX2 = 2.5 * p, cY2 = -p;
        double qX = 0, qY = (2.0/3) * p;

        this.getElements().addAll(
                new MoveTo(sX, sY),
                new CubicCurveTo(cX1, cY1, cX2, cY2, mX, mY),
                new QuadCurveTo(qX, qY, eX, eY),
                new QuadCurveTo(qX, qY, -mX, mY),
                new CubicCurveTo(-cX2, cY2, -cX1, cY1, sX, sY)
        );
    }

    public void takeDamage() {
        this.setFill(Color.BLACK);
    }
}
