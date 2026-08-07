package gridrunner.gameObjects;

import gridrunner.Constants;
import gridrunner.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends Group {

    private List<Rectangle> walls;
    private Rectangle goal;
    public double startX, startY;
    private Rectangle start;
    private List<BlinkingWalls> blinkingWallsList;
    private List<PlayerHeart> hearts;
    private List<Coin> coins;

    public Level ( String map[], double tileSize, Color wallFillColor, Color wallStrokeColor, Color startColor, Color goalColor ) {
        this.walls = new ArrayList<> ( );
        this.blinkingWallsList = new ArrayList<>();
        this.hearts = new ArrayList<>();

        for ( int row = 0; row < map.length; row++ ) {
            for ( int column = 0; column < map[row].length ( ); column++ ) {
                double positionX = column * tileSize;
                double positionY = row * tileSize;

                switch ( map[row].charAt( column ) ) {
                    case 'H', '#': {
                        Rectangle wall = new Rectangle ( tileSize, tileSize );
                        wall.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        wall.setFill ( wallFillColor );
                        wall.setStroke ( wallStrokeColor );
                        wall.setStrokeWidth ( tileSize * 0.04 );

                        this.walls.add ( wall );

                        super.getChildren ( ).add ( wall );

                        if (map[row].charAt( column ) == 'H') {
                            PlayerHeart heart = new PlayerHeart(
                                    Constants.HEART_SIZE,
                                    (column + 1.0/2) * Constants.TILE_SIZE,
                                    (row + 1.0/2) * Constants.TILE_SIZE,
                                    Constants.HEART_COLOR,
                                    Constants.HEART_STROKE
                            );
                            hearts.add(heart);
                            this.getChildren().add(heart);
                        }

                        break;
                    }
                    case 'S': {
                        this.start = new Rectangle ( tileSize, tileSize );
                        this.start.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.start.setFill ( startColor );

                        super.getChildren ( ).add ( this.start );

                        this.startX = positionX;
                        this.startY = positionY;

                        break;
                    }
                    case 'G':{
                        this.goal = new Rectangle ( tileSize, tileSize );
                        this.goal.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        this.goal.setFill ( goalColor );

                        super.getChildren ( ).add ( this.goal );

                        break;
                    }
                    case 'U': {
                        UpDownEnemy upDownEnemy = new UpDownEnemy(
                                Constants.UP_DOWN_ENEMY_WIDTH,
                                Constants.UP_DOWN_ENEMY_HEIGHT,
                                Constants.UP_DOWN_ENEMY_SPEED,
                                column * Constants.TILE_SIZE,
                                row * Constants.TILE_SIZE,
                                3 * Constants.TILE_SIZE,
                                Constants.UP_DOWN_ENEMY_COLOR,
                                Constants.UP_DOWN_ENEMY_STROKE
                        );
                        this.getChildren().add(upDownEnemy);
                        break;
                    }
                    case 'R': {
                        RotatingEnemy rotatingEnemy = new RotatingEnemy(
                                Constants.ROTATING_ENEMY_AXLE_WIDTH,
                                Constants.ROTATING_ENEMY_STICK_WIDTH,
                                Constants.ROTATING_ENEMY_STICK_HEIGHT,
                                column * Constants.TILE_SIZE,
                                row * Constants.TILE_SIZE,
                                Constants.ROTATING_ENEMY_SPEED,
                                Constants.ROTATING_ENEMY_COLOR,
                                Constants.ROTATING_ENEMY_STROKE
                        );
                        this.getChildren().add(rotatingEnemy);
                        break;
                    }
                    case 'B': {
                        if (row > 0 && map[column].charAt(row - 1) == 'B')
                            continue;
                        double height = Constants.TILE_SIZE;
                        int tRow = row + 1;
                        while (tRow < map.length && map[tRow].charAt(column) == 'B') {
                            height += Constants.TILE_SIZE;
                            tRow++;
                        }
                        if (column > 0 && map[row].charAt(column - 1) == 'B')
                            continue;
                        double width = Constants.TILE_SIZE;
                        int tCol = column + 1;
                        while (tCol < map[row].length() && map[row].charAt(tCol) == 'B') {
                            width += Constants.TILE_SIZE;
                            tCol++;
                        }
                        BlinkingWalls blinkingWalls = new BlinkingWalls(
                                width,
                                height,
                                column * Constants.TILE_SIZE,
                                row * Constants.TILE_SIZE,
                                Constants.BLINKING_WALL_COLOR,
                                Constants.BLINKING_WALL_STROKE,
                                Constants.BLINKING_WALL_DURATION,
                                Constants.BLINKING_WALL_UNIT_WIDTH,
                                Constants.BLINKING_WALL_UNIT_HEIGHT
                        );
                        this.getChildren().add(blinkingWalls);
                        this.blinkingWallsList.add(blinkingWalls);
                        break;
                    }
                }
            }
        }

        coins = Coin.generateRandomCoins(map, 3, Constants.TILE_SIZE, Constants.TILE_SIZE,
                Constants.COIN_RADIUS, Constants.COIN_COLOR, Constants.COIN_STROKE);
        if (coins == null) return;
        this.getChildren().addAll(coins);
    }

    public List<Rectangle> getWalls ( ) { return Collections.unmodifiableList ( this.walls ); }

    public Rectangle getGoal ( ) { return this.goal; }

    public double getStartX ( ) { return this.startX; }
    public double getStartY ( ) { return this.startY; }

    public void startBlinkingWalls(double duration, List<Rectangle> walls, Player player) {
        for (BlinkingWalls wall : blinkingWallsList) {
            wall.startBlinking(duration, walls, player);
        }
    }

    public List<PlayerHeart> getHearts() {
        return hearts;
    }
}