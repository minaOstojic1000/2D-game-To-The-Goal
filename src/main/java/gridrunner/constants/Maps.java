package gridrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Maps {
    public static final String[][] MAPS = {
            {
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
            },
            {
                    "HHH#################",
                    "#S.................#",
                    "#............R.....#",
                    "#...#######........#",
                    "#.......#..........#",
                    "#.......#..........#",
                    "#.U.########...R...#",
                    "#..................#",
                    "#..................#",
                    "#.......########...#",
                    "#.......#..........#",
                    "#....R..#..........#",
                    "#.......########...#",
                    "#.......BB.......G.#",
                    "####################"
            },
            {
                    "HHH#################",
                    "#S.........B.......#",
                    "#..........B.......#",
                    "#.U........B.......#",
                    "#...#......B...R...#",
                    "#...#......B.......#",
                    "#...########.......#",
                    "#......U...........#",
                    "#..................#",
                    "#.......########.U.#",
                    "#.......#..........#",
                    "#....R..#..........#",
                    "#.......########...#",
                    "#................G.#",
                    "####################"
            },
            {
                    "HHH#################",
                    "#S.........#.....G.#",
                    "#..........#.......#",
                    "#.U.########.......#",
                    "#...#..........R...#",
                    "#...#..............#",
                    "#...########BBBBBBB#",
                    "#..........#.......#",
                    "#..R.......#.......#",
                    "#.......########.U.#",
                    "#.......#..........#",
                    "#....R..#.........U#",
                    "#.......########...#",
                    "#..................#",
                    "####################"
            }
    };

    public static final int DEFAULT_MAP_WIDTH = MAPS[0][0].length() * Constants.TILE_SIZE;
    public static final int DEFAULT_MAP_HEIGHT = MAPS[0].length * Constants.TILE_SIZE;

    public static final int WINDOW_WIDTH = DEFAULT_MAP_WIDTH;
    public static final int WINDOW_HEIGHT = DEFAULT_MAP_HEIGHT;

    public static final DropShadow HIGHLIGHT_MAP_IMG_SHADOW = Constants.CYAN_GLOW;
    public static final DropShadow DEFAULT_MAP_IMG_SHADOW = Constants.DEFAULT_SHADOW;

    public static final String MAP_CHOICE_TITLE = "SELECT GAME MAP";
    public static final Color MAP_CHOICE_TITLE_COLOR = Constants.DEFAULT_TITLE_COLOR;
    public static final Font MAP_CHOICE_TITLE_FONT = Constants.DEFAULT_TITLE_FONT;

    public static final String MAP_CHOICE_CONFIRM_BUTTON_TEXT = "NEXT";
    public static final Color MAP_CHOICE_CONFIRM_BUTTON_COLOR = Constants.DEFAULT_BUTTON_COLOR;
    public static final Color MAP_CHOICE_CONFIRM_BUTTON_TEXT_COLOR = Constants.DEFAULT_BUTTON_TEXT_COLOR;
    public static final Font MAP_CHOICE_CONFIRM_BUTTON_FONT = Constants.DEFAULT_BUTTON_FONT;
    public static final Color MAP_CHOICE_CONFIRM_BUTTON_STROKE = Constants.DEFAULT_BUTTON_STROKE;
    public static final DropShadow MAP_CHOICE_CONFIRM_BUTTON_HIGHLIGHT = Constants.DEFAULT_BUTTON_HIGHLIGHT;
    public static final DropShadow MAP_CHOICE_CONFIRM_BUTTON_SHADOW = Constants.DEFAULT_BUTTON_SHADOW;

    public static final Background MAP_CHOICE_BACKGROUND = new Background(new BackgroundFill(Color.web("#0a1128"), null, null));


}