package gridrunner;

import gridrunner.gameObjects.Coin;
import gridrunner.gameObjects.Level;
import gridrunner.infoPanes.AdditionalInformation;
import gridrunner.infoPanes.EndOfGame;
import gridrunner.interfaces.IPickup;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

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

        level.startBlinkingWalls(Constants.BLINKING_WALL_DURATION, playerWalls, player);

        player.setLifeHearts(level.getHearts());

        Scene scene = new Scene ( root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT );
        scene.setFill ( Constants.BACKGROUND_COLOR );

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        AdditionalInformation info = new AdditionalInformation(
                Constants.WINDOW_WIDTH, Constants.TILE_SIZE, 0, 0
        );

        EndOfGame endOfGame = new EndOfGame(
                Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 0, 0
        );

        root.getChildren().add(info);

        root.getChildren().add(endOfGame);

        List<IPickup> pickups = IPickup.getPickups();

        AnimationTimer timer = new AnimationTimer ( ) {
            private double last;
            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                for (IPickup pickup : pickups) {
                    if (pickup.touchesPlayer(player)) {
                        pickup.affect(player);
                    }
                }

                Coin.cleanUsed();
                info.updateAll(now, player.getPoints());

                if (player.touches(level.getGoal())) {
                    this.stop();
                    endOfGame.show(Constants.WIN_MSG);
                }
                else if (!player.isAlive()) {
                    this.stop();
                    endOfGame.show(Constants.LOSE_MSG);
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