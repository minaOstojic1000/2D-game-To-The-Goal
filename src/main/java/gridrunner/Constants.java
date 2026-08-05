package gridrunner;

import javafx.scene.paint.Color;

public class Constants {
    public static final String[] MAP = {
            "####################",
            "#S.................#",
            "#..................#",
            "#...########.......#",
            "#...#..............#",
            "#...#..............#",
            "#...########.......#",
            "#..................#",
            "#..................#",
            "#.......########...#",
            "#.......#..........#",
            "#.......#..........#",
            "#.......########...#",
            "#................G.#",
            "####################"
    };

    public static final int TILE_SIZE = 40;

    public static final int WINDOW_WIDTH  = Constants.MAP[0].length ( ) * Constants.TILE_SIZE;
    public static final int WINDOW_HEIGHT = Constants.MAP.length * Constants.TILE_SIZE;

    public static final Color BACKGROUND_COLOR    = Color.web ( "#c8c8c8" );
    public static final Color START_COLOR         = Color.web ( "#88c8ff" );
    public static final Color GOAL_COLOR          = Color.web ( "#44dd88" );
    public static final Color WALL_FILL_COLOR     = Color.web ( "#334466" );
    public static final Color WALL_STROKE_COLOR   = Color.web ( "#223355" );
    public static final Color PLAYER_FILL_COLOR   = Color.web ( "#2255cc" );
    public static final Color PLAYER_STROKE_COLOR = Color.web ( "#1133aa" );

    public static final Color BLINKING_WALL_COLOR = Color.YELLOW;
    public static final Color BLINKING_WALL_STROKE = Color.DARKORANGE;
    public static final double BLINKING_WALL_DURATION = 2;
    public static final double BLINKING_WALL_WIDTH = TILE_SIZE;
    public static final double BLINKING_WALL_HEIGHT = 2 * TILE_SIZE;
    public static final double BLINKING_WALL_UNIT_WIDTH = TILE_SIZE;
    public static final double BLINKING_WALL_UNIT_HEIGHT = TILE_SIZE;

    public static final double PLAYER_RADIUS = TILE_SIZE * 0.75 / 2;
    public static final double PLAYER_SPEED  = 180; // pixels per second

    public static final double UP_DOWN_ENEMY_WIDTH = TILE_SIZE;
    public static final double UP_DOWN_ENEMY_HEIGHT = TILE_SIZE;
    public static final Color UP_DOWN_ENEMY_COLOR = Color.RED;
    public static final Color UP_DOWN_ENEMY_STROKE = Color.BLACK;
    public static final double UP_DOWN_ENEMY_SPEED = 120;

    public static final double HEART_SIZE = TILE_SIZE / 2.0;
    public static final Color HEART_COLOR = Color.RED;
    public static final Color HEART_STROKE = Color.BLACK;
}
