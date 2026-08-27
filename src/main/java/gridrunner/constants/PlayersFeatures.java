package gridrunner.constants;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static gridrunner.constants.Constants.TILE_SIZE;

public class PlayersFeatures {

    public static class PlayerFeature {
        private final double sizeDimension1;
        private final double sizeDimension2;
        private final double speed;
        private final Color fill;
        private final Color stroke;
        private final int lives;

        public PlayerFeature(double sizeDimension1, double sizeDimension2,
                             double speed, Color fill, Color stroke, int lives) {
            this.sizeDimension1 = sizeDimension1;
            this.sizeDimension2 = sizeDimension2;
            this.speed = speed;
            this.fill = fill;
            this.stroke = stroke;
            this.lives = lives;
        }

        public double getSizeDimension1() {
            return sizeDimension1;
        }

        public double getSizeDimension2() {
            return sizeDimension2;
        }

        public double getSpeed() {
            return speed;
        }

        public Color getFill() {
            return fill;
        }

        public Color getStroke() {
            return stroke;
        }

        public int getLives() {
            return lives;
        }
    }

    public static final double CIRCLE_PLAYER_RADIUS = TILE_SIZE * 0.75 / 2;
    public static final double CIRCLE_PLAYER_SPEED  = 180;
    public static final int CIRCLE_PLAYER_LIVES  = 4;
    public static final Color CIRCLE_PLAYER_FILL = Color.web ( "#2255cc" );
    public static final Color CIRCLE_PLAYER_STROKE = Color.web ( "#1133aa" );

    public static final double THORNY_CIRCLE_PLAYER_RADIUS = TILE_SIZE * 0.75 / 2;
    public static final double THORNY_CIRCLE_PLAYER_SPEED  = 200;
    public static final int THORNY_CIRCLE_PLAYER_LIVES  = 3;
    public static final Color THORNY_CIRCLE_PLAYER_FILL = Color.PURPLE;
    public static final Color THORNY_CIRCLE_PLAYER_STROKE = Color.WHITE;

    public static final double TANK_PLAYER_WIDTH = 0.8 * Constants.TILE_SIZE;
    public static final double TANK_PLAYER_HEIGHT = 0.6 * Constants.TILE_SIZE;
    public static final double TANK_PLAYER_SPEED  = 150;
    public static final int TANK_PLAYER_LIVES  = 5;
    public static final Color TANK_PLAYER_FILL = Color.DARKOLIVEGREEN;
    public static final Color TANK_PLAYER_STROKE = Color.BLACK;

    public static final Color IMMUNE_CIRCLE_FILL = Color.TRANSPARENT;
    public static final Color IMMUNE_CIRCLE_STROKE = Color.DARKRED;
    public static final double IMMUNE_CIRCLE_STROKE_WIDTH = 0.5;
    public static final DropShadow IMMUNE_CIRCLE_SHADOW = Glows.RED_GLOW;

    public static final PlayerFeature[] PLAYERS = {
            new PlayerFeature(
                    CIRCLE_PLAYER_RADIUS,
                    -1,
                    CIRCLE_PLAYER_SPEED,
                    CIRCLE_PLAYER_FILL,
                    CIRCLE_PLAYER_STROKE,
                    CIRCLE_PLAYER_LIVES
            ),
            new PlayerFeature(
                    THORNY_CIRCLE_PLAYER_RADIUS,
                    -1,
                    THORNY_CIRCLE_PLAYER_SPEED,
                    THORNY_CIRCLE_PLAYER_FILL,
                    THORNY_CIRCLE_PLAYER_STROKE,
                    THORNY_CIRCLE_PLAYER_LIVES
            ),
            new PlayerFeature(
                    TANK_PLAYER_WIDTH,
                    TANK_PLAYER_HEIGHT,
                    TANK_PLAYER_SPEED,
                    TANK_PLAYER_FILL,
                    TANK_PLAYER_STROKE,
                    TANK_PLAYER_LIVES
            )
    };

    public static final DropShadow HIGHLIGHT_PLAYER_IMG_SHADOW = Glows.CYAN_GLOW;
    public static final DropShadow DEFAULT_PLAYER_IMG_SHADOW = Glows.DEFAULT_SHADOW;
    public static final DropShadow SELECTED_PLAYER_IMG_SHADOW = Glows.AMBER_GLOW;

    public static final String PLAYER_CHOICE_TITLE = "SELECT PLAYER";
    public static final Color PLAYER_CHOICE_TITLE_COLOR = Constants.DEFAULT_TITLE_COLOR;
    public static final Font PLAYER_CHOICE_TITLE_FONT = Constants.DEFAULT_TITLE_FONT;

    public static final String PLAYER_CHOICE_CONFIRM_BUTTON_TEXT = "START PLAYING";
    public static final Color PLAYER_CHOICE_CONFIRM_BUTTON_COLOR = Constants.DEFAULT_BUTTON_COLOR;
    public static final Color PLAYER_CHOICE_CONFIRM_BUTTON_TEXT_COLOR = Constants.DEFAULT_BUTTON_TEXT_COLOR;
    public static final Font PLAYER_CHOICE_CONFIRM_BUTTON_FONT = Constants.DEFAULT_BUTTON_FONT;
    public static final Color PLAYER_CHOICE_CONFIRM_BUTTON_STROKE = Constants.DEFAULT_BUTTON_STROKE;
    public static final DropShadow PLAYER_CHOICE_CONFIRM_BUTTON_HIGHLIGHT = Constants.DEFAULT_BUTTON_HIGHLIGHT;
    public static final DropShadow PLAYER_CHOICE_CONFIRM_BUTTON_SHADOW = Constants.DEFAULT_BUTTON_SHADOW;

    public static final Background PLAYER_CHOICE_BACKGROUND = new Background(new BackgroundFill(Color.web("#0a1128"), null, null));

}
