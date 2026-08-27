package gridrunner;

import gridrunner.constants.Constants;
import gridrunner.constants.PlayersFeatures;
import gridrunner.gameObjects.Coin;
import gridrunner.gameObjects.Level;
import gridrunner.gameObjects.LifeBooster;
import gridrunner.gameObjects.Shield;
import gridrunner.infoPanes.AdditionalInformation;
import gridrunner.infoPanes.EndOfGame;
import gridrunner.infoPanes.MapChoice;

import gridrunner.infoPanes.PlayerChoice;
import gridrunner.interfaces.IPickup;
import gridrunner.playerClasses.CirclePlayer;
import gridrunner.playerClasses.Player;
import gridrunner.playerClasses.TankPlayer;
import gridrunner.playerClasses.ThornyCirclePlayer;
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

    private final Group root;
    private final StackPane rootPane;
    private Level level;
    private Player player;
    private AnimationTimer timer;

    private final Input input;
    private AdditionalInformation info;
    private final EndOfGame endOfGame;

    private final MapChoice mapChoice;
    private final PlayerChoice playerChoice;

    GameGenerator(StackPane rootPane, Input input) {

        this.root = new Group();
        this.rootPane = rootPane;
        rootPane.setAlignment(Pos.TOP_LEFT);
        rootPane.getChildren().add(root);

        this.input = input;

        mapChoice = new MapChoice(Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0);

        playerChoice = new PlayerChoice(Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0);

        endOfGame = new EndOfGame(
                Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT, 0, 0
        );

        root.getChildren().add(mapChoice);
        root.getChildren().add(playerChoice);
    }

    public void generateGame() {
        mapChoice.addAction(this::generateLevel);
        mapChoice.addAction(this::openPlayerChoice);
        playerChoice.addAction(this::generatePlayer);
        playerChoice.addAction(this::startGame);

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
        this.level.setVisible(false);
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

    private void openPlayerChoice() {
        playerChoice.show();
    }

    private void generatePlayer() {

        int selectedPlayerNum = playerChoice.getSelectedPlayerNum();

        this.player = Player.createPlayer(
                selectedPlayerNum,
                level.startX + Constants.TILE_SIZE / 2.,
                level.startY + Constants.TILE_SIZE / 2.
        );
        this.root.getChildren().add(player);
    }

    private void setGameTimer(List<Rectangle> playerWalls) {

        List<IPickup> pickups = IPickup.getPickups();
        Random random = new Random();
        final int numMoments = 10;
        final double[] nextHeartMoments = new double[numMoments];
        final double[] nextShieldMoments = new double[numMoments];
        for (int i = 0; i < numMoments; i++) {
            nextHeartMoments[i] = random.nextDouble(15);
            nextShieldMoments[i] = random.nextDouble(30);
        }


        timer = new AnimationTimer ( ) {
            private double last;
            private double timeSlices = 0;
            private int currMomentH = 0, currMomentS;
            private double momentsSumH = 0, momentsSumS = 0;

            @Override
            public void handle ( long now ) {
                if ( this.last == 0 ) {
                    this.last = now;
                }

                double dt = ( now - this.last ) / 10e8;
                this.last = now;

                timeSlices += dt;
                generateHearts(dt);
                generateShields(dt);

                for (IPickup pickup : pickups) {
                    if (pickup.touchesPlayer(player)) {
                        pickup.affect(player);
                    }
                }

                Coin.cleanUsed();
                LifeBooster.cleanExpired();
                Shield.cleanExpired();

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

                while ((timeSlices - momentsSumH) >= nextHeartMoments[currMomentH]) {

                    momentsSumH += nextHeartMoments[currMomentH];
                    currMomentH = (currMomentH + 1) % numMoments;

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

            private void generateShields(double dt) {

                while ((timeSlices - momentsSumS) >= nextShieldMoments[currMomentS]) {

                    momentsSumS += nextShieldMoments[currMomentS];
                    currMomentS = (currMomentS + 1) % numMoments;

                    Shield shield = Shield.generateRandomShields(
                            mapChoice.getMap(),
                            Constants.TILE_SIZE, Constants.TILE_SIZE,
                            Constants.SHIELD_WIDTH,
                            Constants.SHIELD_FILL, Constants.SHIELD_STROKE,
                            Constants.IMMUNITY_DURATION,
                            Constants.SHIELD_DURATION
                    );
                    root.getChildren().add(shield);
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
        level.setVisible(true);
        timer.start();
    }

}
