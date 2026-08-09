package gridrunner;

import gridrunner.constants.Constants;
import gridrunner.gameObjects.Coin;
import gridrunner.gameObjects.Level;
import gridrunner.gameObjects.LifeBooster;
import gridrunner.infoPanes.AdditionalInformation;
import gridrunner.infoPanes.EndOfGame;
import gridrunner.infoPanes.MapChoice;

import gridrunner.interfaces.IPickup;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gridrunner.constants.Maps;

public class GameGenerator {

    private Group root;
    private final StackPane rootPane;
    private Level level;
    private Player player;
    private AnimationTimer timer;

    private Input input;
    private AdditionalInformation info;
    private EndOfGame endOfGame;

    private MapChoice mapChoice;

    GameGenerator(StackPane rootPane, Input input) {

        this.root = new Group();
        this.rootPane = rootPane;
        rootPane.setAlignment(Pos.TOP_LEFT);
        rootPane.getChildren().add(root);

        this.input = input;

        mapChoice = new MapChoice(Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0);


        endOfGame = new EndOfGame(
                Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0
        );

        root.getChildren().add(mapChoice);
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
        setBackground(mapChoice.getMapBackground());
    }

    private void setBackground(Image img) {
        BackgroundImage bckImg = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,

                new BackgroundSize(
                        BackgroundSize.AUTO, BackgroundSize.AUTO,
                        false, false,
                        true,
                        true
                )
        );
        this.rootPane.setBackground(new Background(bckImg));
    }

    private void generatePlayer() {
        this.player = new Player (
                Constants.PLAYER_RADIUS,
                level.getStartX ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                level.getStartY ( ) + Constants.TILE_SIZE / 2. - Constants.PLAYER_RADIUS,
                Constants.PLAYER_SPEED,
                Constants.PLAYER_FILL_COLOR,
                Constants.PLAYER_STROKE_COLOR,
                Constants.PLAYER_DEFAULT_LIVES
        );

        this.root.getChildren().add(player);
    }

    private void setGameTimer(List<Rectangle> playerWalls) {

        List<IPickup> pickups = IPickup.getPickups();
        Random random = new Random();
        final int numMoments = 10;
        final double[] nextHeartMoments = new double[numMoments];
        for (int i = 0; i < numMoments; i++) {
            nextHeartMoments[i] = random.nextDouble(15);
        }

        timer = new AnimationTimer ( ) {
            private double last;
            private double timeSlices = 0;
            private int currMoment = 0;
            private double momentsSum = 0;

            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                generateHearts(dt);

                for (IPickup pickup : pickups) {
                    if (pickup.touchesPlayer(player)) {
                        pickup.affect(player);
                    }
                }

                Coin.cleanUsed();
                LifeBooster.cleanExpired();

                info.updateAll(now, player.getPoints());

                if (player.touches(level.getGoal())) {
                    this.stop();
                    endOfGame.show(Constants.WIN_MSG);
                }
                else if (!player.isAlive()) {
                    this.stop();
                    endOfGame.show(Constants.LOSE_MSG);
                }

                player.update ( dt, input, playerWalls );
            }

            private void generateHearts(double dt) {

                timeSlices += dt;

                while ((timeSlices - momentsSum) >= nextHeartMoments[currMoment]) {

                    momentsSum += nextHeartMoments[currMoment];
                    currMoment = (currMoment + 1) % numMoments;

                    LifeBooster life = LifeBooster.generateRandomLifeBooster(
                            mapChoice.getMap(),
                            Constants.TILE_SIZE, Constants.TILE_SIZE,
                            Constants.HEART_SIZE,
                            Constants.HEART_COLOR, Constants.HEART_STROKE,
                            Constants.LIFE_BOOSTER_DURATION
                    );
                    root.getChildren().add(life);
                }
            }
        };
    }

    public void startGame() {

        List<Rectangle> playerWalls = new ArrayList<>(level.getWalls());
        level.startBlinkingWalls(Constants.BLINKING_WALL_DURATION, playerWalls, player);

        info = new AdditionalInformation(
                Maps.WINDOW_WIDTH, Constants.TILE_SIZE, 0, 0,
                player.getNumOfLives()
        );
        info.show();
        player.setLifeHearts(info.getHearts());

        root.getChildren().addAll(info, endOfGame);

        setGameTimer(playerWalls);
        timer.start();
    }

}
