package gridrunner.infoPanes;

import gridrunner.constants.Maps;
import gridrunner.constants.PlayersFeatures;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class PlayerChoice extends ChoicePane{

    public PlayerChoice(double width, double height, double x, double y) {
        super(width, height, x, y);
    }

    public int getSelectedPlayerNum() {
        return selectedChoice;
    }

    @Override
    protected void setImages() {
        for (int i = 0; i < PlayersFeatures.PLAYERS.length; i++) {
            ImageView imgView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gridrunner/puppy.jpg"))));
            choiceImages.add(imgView);
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);
        }
    }

    @Override
    protected DropShadow getSelectedImgShadow() {
        return PlayersFeatures.SELECTED_PLAYER_IMG_SHADOW;
    }

    @Override
    protected DropShadow getDefaultImgShadow() {
        return PlayersFeatures.DEFAULT_PLAYER_IMG_SHADOW;
    }

    @Override
    protected DropShadow getHighlightImgShadow() {
        return PlayersFeatures.HIGHLIGHT_PLAYER_IMG_SHADOW;
    }

    @Override
    protected String getTitle() {
        return PlayersFeatures.PLAYER_CHOICE_TITLE;
    }

    @Override
    protected Color getTitleColor() {
        return PlayersFeatures.PLAYER_CHOICE_TITLE_COLOR;
    }

    @Override
    protected Color getTitleBoxColor() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected Font getTitleFont() {
        return PlayersFeatures.PLAYER_CHOICE_TITLE_FONT;
    }

    @Override
    protected Color getConfirmButtonColor() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected String getConfirmButtonText() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_TEXT;
    }

    @Override
    protected Color getConfirmButtonTextColor() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_TEXT_COLOR;
    }

    @Override
    protected Font getConfirmButtonTextFont() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_FONT;
    }

    @Override
    protected Color getConfirmButtonStroke() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_STROKE;
    }

    @Override
    protected DropShadow getConfirmButtonShadow() {
        return PlayersFeatures.PLAYER_CHOICE_CONFIRM_BUTTON_SHADOW;
    }

    @Override
    protected Background getPaneBackground() {
        return PlayersFeatures.PLAYER_CHOICE_BACKGROUND;
    }

    @Override
    protected GridPane createCentralImgPane(double width, double height) {
        GridPane pane = new GridPane(width * 0.03, height * 0.03);
        for (int i = 0; i < choiceImages.size(); i++) {
            pane.add(choiceImages.get(i), i, 0);
        }
        return pane;
    }

}
