package gridrunner;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlinkingWalls extends Group {

    double duration, currSlices; // slices in seconds
    double width, height;
    double startX, startY;
    List<Rectangle> walls;
    double transitionDuration = 0.1;

    public BlinkingWalls(double width, double height, double positionX, double positionY,
                         Color fillColor, Color strokeColor, double duration,
                         double rectW, double rectH,
                         List<Rectangle> playerWalls, Player player) {
        this.width = width; this.height = height;
        this.duration = duration;
        this.startX = positionX; this.startY = positionY;

        initRectangles(rectW, rectH, fillColor, strokeColor);

        setAnimation(duration, playerWalls, player);

        super.getTransforms().addAll(
                new Translate(startX, startY)
        );
        this.setOpacity(0);
    }

    private void initRectangles(double oneW, double oneH, Color fillColor, Color strokeColor) {

        walls = new ArrayList<>();

        int numR = (int)Math.ceil(width / oneW);
        int numC = (int)Math.ceil(height / oneH);
        for (int i = 0; i < numC; i++) {
            for (int j = 0; j < numR; j++) {
                Rectangle wall = new Rectangle(oneW, oneH);
                wall.setFill(fillColor);
                wall.setStroke(strokeColor);
                wall.setStrokeWidth ( oneW * 0.04 );
                wall.getTransforms ( ).addAll (
                        new Translate ( j * oneW, i * oneH )
                );

                this.getChildren().add(wall);
                walls.add(wall);
            }
        }
    }

    private void setAnimation(double duration,List<Rectangle> walls, Player player) {

        FadeTransition appear = new FadeTransition(Duration.seconds(transitionDuration), this);
        appear.setFromValue(0);
        appear.setToValue(1);

        FadeTransition disappear = new FadeTransition(Duration.seconds(transitionDuration), this);
        disappear.setFromValue(1);
        disappear.setToValue(0);

        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(duration), event->{
                    if (!playerInWallBounds(player)) {
                        walls.addAll(this.walls);
                        appear.play();
                    }
                }),
                new KeyFrame(Duration.seconds(duration * 2), event ->{
                    if (!playerInWallBounds(player)) {
                        walls.removeAll(this.walls);
                        disappear.play();
                    }
                })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private boolean playerInWallBounds(Player player) {
        Bounds playerToScene = player.localToScene(player.getBoundsInLocal());
        Bounds playerBounds = this.getParent().sceneToLocal(playerToScene);

        double playerX = playerBounds.getCenterX();
        double playerY = playerBounds.getCenterY();
        double playerRadius = player.getRadius();

        double left = startX;
        double up = startY;
        double right = startX + width;
        double down = startY + height;

        double closestX = Math.max ( left, Math.min ( playerX, right ) );
        double closestY = Math.max ( up, Math.min ( playerY, down ) );

        double dx = playerX - closestX;
        double dy = playerY - closestY;

        return (dx * dx + dy * dy < playerRadius * playerRadius);
    }

    public List<Rectangle> getWalls ( ) { return Collections.unmodifiableList ( this.walls ); }

}
