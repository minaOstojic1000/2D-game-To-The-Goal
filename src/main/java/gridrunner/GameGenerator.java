package gridrunner;

import gridrunner.constants.Constants;
import gridrunner.gameObjects.Coin;
import gridrunner.gameObjects.Level;
import gridrunner.infoPanes.AdditionalInformation;
import gridrunner.infoPanes.EndOfGame;
import gridrunner.infoPanes.MapChoice;
import gridrunner.interfaces.ITrigger;
import gridrunner.interfaces.IPickup;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import gridrunner.constants.Maps;

public class GameGenerator {

    Group root;
    Level level;
    Player player;
    AnimationTimer timer;

    Input input;
    AdditionalInformation info;
    EndOfGame endOfGame;

    MapChoice mapChoice;

    GameGenerator(Group root, Input input) {

        this.root = root;
        this.input = input;

        mapChoice = new MapChoice(Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0);

        info = new AdditionalInformation(
                Maps.WINDOW_WIDTH, Constants.TILE_SIZE, 0, 0
        );

        endOfGame = new EndOfGame(
                Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0
        );

        root.getChildren().addAll(mapChoice);
    }

    public void generateGame() {

        mapChoice.addAction(this::generateLevel);
        mapChoice.addAction(this::generatePlayer);
        mapChoice.addAction(this::startGame);

        mapChoice.show();
    }

    private void generateLevel() {
        this.level = new Level (
                mapChoice.getMap(),
                Constants.TILE_SIZE,
                Constants.WALL_FILL_COLOR,
                Constants.WALL_STROKE_COLOR,
                Constants.START_COLOR,
                Constants.GOAL_COLOR
        );
        this.root.getChildren().add(level);
    }

    private void generatePlayer() {
        this.player = new Player (
                Constants.PLAYER_RADIUS,
                level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                Constants.PLAYER_FILL_COLOR,
                Constants.PLAYER_STROKE_COLOR
        );

        player.setLifeHearts(level.getHearts());

        this.root.getChildren().add(player);
    }

    private void setGameTimer(List<Rectangle> playerWalls) {

        List<IPickup> pickups = IPickup.getPickups();

        timer = new AnimationTimer ( ) {
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
    }

    public void startGame() {

        List<Rectangle> playerWalls = new ArrayList<>(level.getWalls());
        level.startBlinkingWalls(Constants.BLINKING_WALL_DURATION, playerWalls, player);

        info.show();

        root.getChildren().addAll(info, endOfGame);

        setGameTimer(playerWalls);
        timer.start();
    }

}
