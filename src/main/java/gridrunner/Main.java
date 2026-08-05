package gridrunner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {

    private List<UpDownEnemy> setUpDownEnemies() {
        List<UpDownEnemy> upDownEnemies = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            upDownEnemies.add(
                    new UpDownEnemy(
                            Constants.UP_DOWN_ENEMY_WIDTH,
                            Constants.UP_DOWN_ENEMY_HEIGHT,
                            Constants.UP_DOWN_ENEMY_SPEED,
                            Constants.UP_DOWN_ENEMY_COLOR,
                            Constants.UP_DOWN_ENEMY_STROKE
                    )
            );
        }

        upDownEnemies.get(0).setPositionX(
                2 * Constants.TILE_SIZE
        );

        upDownEnemies.get(0).setStartY(
                5 * Constants.TILE_SIZE
        );

        upDownEnemies.get(0).setYDistance(
                3 * Constants.TILE_SIZE
        );

        upDownEnemies.get(1).setPositionX(
                17 * Constants.TILE_SIZE
        );

        upDownEnemies.get(1).setStartY(
                9 * Constants.TILE_SIZE
        );

        upDownEnemies.get(1).setYDistance(
                3 * Constants.TILE_SIZE
        );

        for (UpDownEnemy enemy : upDownEnemies) {
            enemy.startAnimation();
        }
        return upDownEnemies;
    }

    private BlinkingWalls createBlinkingWall(Player player, List<Rectangle> playerWalls) {
        return new BlinkingWalls(
                Constants.BLINKING_WALL_WIDTH,
                Constants.BLINKING_WALL_HEIGHT,
                11 * Constants.TILE_SIZE,
                Constants.TILE_SIZE,
                Constants.BLINKING_WALL_COLOR,
                Constants.BLINKING_WALL_STROKE,
                Constants.BLINKING_WALL_DURATION,
                Constants.BLINKING_WALL_UNIT_WIDTH,
                Constants.BLINKING_WALL_UNIT_HEIGHT,
                playerWalls,
                player
        );
    }

    private List<PlayerHeart> setUpHearts(int num) {
        List<PlayerHeart> hearts = new ArrayList<>();
        double sX = Constants.TILE_SIZE / 2.0,
                sY = Constants.TILE_SIZE / 2.0,
                span = Constants.TILE_SIZE;
        for (int i = 0; i < num; i++) {
            hearts.add(new PlayerHeart(
                    Constants.HEART_SIZE,
                    sX + i * span, sY,
                    Constants.HEART_COLOR,
                    Constants.HEART_STROKE
            ));
        }
        return hearts;
    }

    @Override
    public void start ( Stage stage ) {
        Group root = new Group ( );

        Input input = new Input();

        Level level = new Level (
                Constants.MAP,
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );

        root.getChildren ( ).add ( level );

        Player player = new Player (
               Constants.PLAYER_RADIUS,
               level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
               Constants.PLAYER_FILL_COLOR,
               Constants.PLAYER_STROKE_COLOR
        );
        root.getChildren ( ).add ( player );

        List<Rectangle> playerWalls = new ArrayList<>(level.getWalls());

        root.getChildren().add(createBlinkingWall(player, playerWalls));

        root.getChildren().addAll(setUpDownEnemies());

        List<PlayerHeart> hearts = setUpHearts(3);
        root.getChildren().addAll(hearts);
        player.setLifeHearts(hearts);

        Scene scene = new Scene ( root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
        scene.setFill ( Constants.BACKGROUND_COLOR );

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        List<IEnemy> enemies = IEnemy.getEnemies();

        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                for (IEnemy enemy : enemies) {
                    if (player.overlaps((Rectangle) enemy)) {
                        enemy.doDamage(player);
                    }
                }

                if ( player.touches ( level.getGoal ( ) ) || !player.isAlive()) {
                    return;
                }

                player.update ( dt, Constants.PLAYER_SPEED, input, playerWalls );
            }
        };

        timer.start ( );

        stage.setTitle ( "Do cilja" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ( );
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}