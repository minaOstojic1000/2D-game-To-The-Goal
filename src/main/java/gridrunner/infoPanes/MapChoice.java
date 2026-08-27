package gridrunner.infoPanes;

import gridrunner.constants.Maps;
import gridrunner.interfaces.ITrigger;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapChoice extends ChoicePane {


    public MapChoice(double width, double height, double x, double y) {
        super(width, height, x, y);
    }

    protected void setImages() {
        StringBuilder name = new StringBuilder();
        name.append("/gridrunner/maps/map0.jpg");
        double width = this.getPrefWidth() * 0.65 / (Maps.MAPS.length / 2.);
        for (int i = 0; i < Maps.MAPS.length; i++) {
            name.replace(name.length() - 5, name.length() - 4, Integer.toString(i + 1));
            ImageView imgView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(name.toString()))));
            choiceImages.add(imgView);
            imgView.setFitWidth(width);
            imgView.setPreserveRatio(true);
        }
    }

    public String[] getMap() {
        return Maps.MAPS[selectedChoice];
    }

    public Image getMapBackground() {
        return Maps.MAP_BACKGROUNDS_IMAGE[selectedChoice];
    }

    @Override
    protected DropShadow getSelectedImgShadow() {
        return Maps.SELECTED_MAP_IMG_SHADOW;
    }

    @Override
    protected DropShadow getDefaultImgShadow() {
        return Maps.DEFAULT_MAP_IMG_SHADOW;
    }

    @Override
    protected DropShadow getHighlightImgShadow() {
        return Maps.HIGHLIGHT_MAP_IMG_SHADOW;
    }

    @Override
    protected String getTitle() {
        return Maps.MAP_CHOICE_TITLE;
    }

    @Override
    protected Color getTitleColor() {
        return Maps.MAP_CHOICE_TITLE_COLOR;
    }

    @Override
    protected Color getTitleBoxColor() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected Font getTitleFont() {
        return Maps.MAP_CHOICE_TITLE_FONT;
    }

    @Override
    protected Color getConfirmButtonColor() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_COLOR;
    }

    @Override
    protected String getConfirmButtonText() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_TEXT;
    }

    @Override
    protected Color getConfirmButtonTextColor() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_TEXT_COLOR;
    }

    @Override
    protected Font getConfirmButtonTextFont() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_FONT;
    }

    @Override
    protected Color getConfirmButtonStroke() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_STROKE;
    }

    @Override
    protected DropShadow getConfirmButtonShadow() {
        return Maps.MAP_CHOICE_CONFIRM_BUTTON_SHADOW;
    }

    @Override
    protected Background getPaneBackground() {
        return Maps.MAP_CHOICE_BACKGROUND;
    }

    @Override
    protected GridPane createCentralImgPane(double width, double height) {
        GridPane pane = new GridPane(width * 0.03, height * 0.03);
        for (int i = 0; i < choiceImages.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                pane.add(choiceImages.get(i * 2 + j), j, i);
            }
        }
        return pane;
    }
}
