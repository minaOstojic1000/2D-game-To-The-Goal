package gridrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class Maps {
    public static final String[][] MAPS = {
            {
                    "####################",
                    "#S.........B..DDD..#",
                    "#..........B..DDD..#",
                    "#.U.########..DDD..#",
                    "#...#..........R...#",
                    "#...#..............#",
                    "#...########.......#",
                    "#..................#",
                    "#.AAA..............T",
                    "#.AAA...########.U.#",
                    "#.......#..........#",
                    "#....R..#..........#",
                    "#.......########...#",
                    "#................G.#",
                    "####################"
            },
            {
                    "####################",
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
                    "####################",
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
                    "####################",
                    "#S.........#.....G.#",
                    "#..........#.......#",
                    "#.U.########.......#",
                    "#...#..........R...#",
                    "#...#..............#",
                    "#...########BBBBBBB#",
                    "#..........#.......#",
                    "#..R.......#.......#",
                    "#.......########.U.#",
                    "#.......#.........U#",
                    "#....R..#..........#",
                    "#.......########...#",
                    "#..................#",
                    "####################"
            }
    };

    public static final Image[] MAP_BACKGROUNDS_IMAGE = {
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/gridrunner/mapBackgrounds/background1.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/gridrunner/mapBackgrounds/background8.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/gridrunner/mapBackgrounds/background2.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/gridrunner/mapBackgrounds/background4.jpg")))
    };

    public static final int DEFAULT_MAP_WIDTH = MAPS[0][0].length() * Constants.TILE_SIZE;
    public static final int DEFAULT_MAP_HEIGHT = MAPS[0].length * Constants.TILE_SIZE;

    public static final int WINDOW_WIDTH = DEFAULT_MAP_WIDTH;
    public static final int WINDOW_HEIGHT = DEFAULT_MAP_HEIGHT;

    public static final DropShadow HIGHLIGHT_MAP_IMG_SHADOW = Glows.CYAN_GLOW;
    public static final DropShadow DEFAULT_MAP_IMG_SHADOW = Glows.DEFAULT_SHADOW;
    public static final DropShadow SELECTED_MAP_IMG_SHADOW = Glows.AMBER_GLOW;

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