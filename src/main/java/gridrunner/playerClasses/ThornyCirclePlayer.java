package gridrunner.playerClasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class ThornyCirclePlayer extends CirclePlayer {

    Path hexagon;
    List<Path> triangles = new ArrayList<>();

    public ThornyCirclePlayer(double radius, double positionX, double positionY, double speed, Color fillColor, Color strokeColor, int numOfLives) {
        super(radius, positionX, positionY, speed, fillColor, strokeColor, numOfLives);
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
        makeFigure(0.75 * radius, fillColor, Color.TRANSPARENT);
    }

    private void makeFigure(double innerRadius, Color fillColor, Color strokeColor) {
        hexagon = new Path();
        double ri = innerRadius, ro = getRadius();
        double x1 = ri, y1 = 0;
        double xt1 = x1 - ri + ro * 0.866, yt1 = y1 + ro * 0.5;
        double x2 = x1 - 0.5 * ri, y2 = y1 + 0.866 * ri;
        double xt2 = x1 - ri, yt2 = y1 + ro;
        double x3 = x1 - 1.5 * ri, y3 = y1 + 0.866 * ri;
        double xt3 = x1 - ri - ro * 0.866, yt3 = y1 + ro * 0.5;
        double x4 = x1 - 2.0 * ri, y4 = y1;
        double xt4 = xt3, yt4 = y1 - ro * 0.5;
        double x5 = x1 - 1.5 * ri, y5 = y1 - 0.866 * ri;
        double xt5 = xt2, yt5 = y1 - ro;
        double x6 = x1 - 0.5 * ri, y6 = y5;
        double xt6 = xt1, yt6 = yt4;
        hexagon.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x2, y2),
                new LineTo(x3, y3),
                new LineTo(x4, y4),
                new LineTo(x5, y5),
                new LineTo(x6, y6),
                new ClosePath()
        );
        hexagon.setFill(fillColor);
        hexagon.setStroke(strokeColor);
        this.getChildren().add(hexagon);
        makeTriangle(x1, y1, x2, y2, xt1, yt1, fillColor, strokeColor);
        makeTriangle(x2, y2, x3, y3, xt2, yt2, fillColor, strokeColor);
        makeTriangle(x3, y3, x4, y4, xt3, yt3, fillColor, strokeColor);
        makeTriangle(x4, y4, x5, y5, xt4, yt4, fillColor, strokeColor);
        makeTriangle(x5, y5, x6, y6, xt5, yt5, fillColor, strokeColor);
        makeTriangle(x6, y6, x1, y1, xt6, yt6, fillColor, strokeColor);
        this.getChildren().addAll(triangles);
    }

    private void makeTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color fill, Color stroke) {
        Path triangle = new Path();

        triangle.getElements().addAll(
                new MoveTo(x1, y1),
                new LineTo(x3, y3),
                new LineTo(x2, y2),
                new ClosePath()
        );
        triangle.setFill(fill);
        triangle.setStroke(fill);
        this.triangles.add(triangle);
    }

    @Override
    public List<Shape> getShapes() {
        return List.of(circle);
    }
}
