package gridrunner.gameObjects;

import gridrunner.constants.Constants;
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
    private List<Coin> coins;

    public Level ( String map[], double tileSize, Color wallFillColor, Color wallStrokeColor, Color startColor, Color goalColor ) {
        this.walls = new ArrayList<> ( );
        this.blinkingWallsList = new ArrayList<>();

        for ( int row = 0; row < map.length; row++ ) {
            for ( int column = 0; column < map[row].length(); column++ ) {
                double positionX = column * tileSize;
                double positionY = row * tileSize;

                switch ( map[row].charAt( column ) ) {
                    case '#': {
                        Rectangle wall = new Rectangle ( tileSize, tileSize );
                        wall.getTransforms ( ).addAll (
                                new Translate ( positionX, positionY )
                        );
                        wall.setFill ( wallFillColor );
                        wall.setStroke ( wallStrokeColor );
                        wall.setStrokeWidth ( tileSize * 0.04 );

                        this.walls.add ( wall );

                        super.getChildren ( ).add ( wall );

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

                        double height = getCFigureHeight(map, row, column, 'B');
                        if (height < 0)
                            continue;

                        double width = getCFigureWidth(map, row, column, 'B');
                        if (width < 0)
                            continue;

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
                    case 'A', 'D': {

                        double height = getCFigureHeight(map, row, column, map[row].charAt(column));
                        if (height < 0)
                            continue;

                        double width = getCFigureWidth(map, row, column, map[row].charAt(column));
                        if (width < 0)
                            continue;
                        double acceleration = Constants.DEFAULT_ACCELERATION;
                        if (map[row].charAt(column) == 'D')
                            acceleration = 1.0 / acceleration;
                        ChangeSpeedEnemy changeSpeedEnemy = new ChangeSpeedEnemy(
                                width, height,
                                positionX, positionY,
                                acceleration
                        );
                        this.getChildren().add(changeSpeedEnemy);
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

    private double getCFigureWidth(String[] map, int row, int column, char c) {
        if (column > 0 && map[row].charAt(column - 1) == c)
            return -1;
        double width = Constants.TILE_SIZE;
        int tCol = column + 1;
        while (tCol < map[row].length() && map[row].charAt(tCol) == c) {
            width += Constants.TILE_SIZE;
            tCol++;
        }
        return width;
    }

    private double getCFigureHeight(String[] map, int row, int column, char c) {
        if (row > 0 && map[row - 1].charAt(column) == c)
            return -1;
        double height = Constants.TILE_SIZE;
        int tRow = row + 1;
        while (tRow < map.length && map[tRow].charAt(column) == c) {
            height += Constants.TILE_SIZE;
            tRow++;
        }
        return height;
    }
}