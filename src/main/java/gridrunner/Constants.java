package gridrunner;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Constants {
    public static final String[] MAP = {
            "HHH#################",
            "#S.........B.......#",
            "#..........B.......#",
            "#.U.########.......#",
            "#...#..........R...#",
            "#...#..............#",
            "#...########.......#",
            "#..................#",
            "#..................#",
            "#.......########.U.#",
            "#.......#..........#",
            "#....R..#..........#",
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
    public static final double BLINKING_WALL_HEIGHT = TILE_SIZE;
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

    public static final double ROTATING_ENEMY_AXLE_WIDTH = TILE_SIZE / 2.0;
    public static final double ROTATING_ENEMY_STICK_WIDTH = 6;
    public static final double ROTATING_ENEMY_STICK_HEIGHT = 160;
    public static final Color ROTATING_ENEMY_COLOR = Color.ORCHID;
    public static final Color ROTATING_ENEMY_STROKE = Color.BLACK;
    public static final double ROTATING_ENEMY_SPEED = 80;

    public static final Color COIN_COLOR = Color.GOLDENROD;
    public static final Color COIN_STROKE = Color.BLACK;
    public static final double COIN_RADIUS = TILE_SIZE / 4.0;

    public static final Color LABEL_POINTS_COLOR = Color.GOLD;
    public static final Font LABEL_POINTS_FONT = Font.font("Consolas", FontWeight.BOLD, 20);
    public static final Color LABEL_TIME_COLOR = Color.WHITE;
    public static final Font LABEL_TIME_FONT = Font.font("Consolas", FontWeight.BOLD, 20);

    public static final Color LABEL_END_GAME_COLOR = Color.WHITE;
    public static final Font LABEL_END_GAME_FONT = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 48);
    public static final double PANEL_END_GAME_OPACITY = 0.8;
    public static final Background PANEL_END_GAME_BACKGROUND = new Background(new BackgroundFill(Color.BLACK, null, null));
    public static final String WIN_MSG = "YOU WIN!";
    public static final String LOSE_MSG = "GAME OVER";
}
