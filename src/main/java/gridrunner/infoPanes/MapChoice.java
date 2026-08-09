package gridrunner.infoPanes;

import gridrunner.constants.Maps;
import gridrunner.interfaces.ITrigger;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapChoice extends StackPane implements ITrigger {

    Label title;
    Button confirm;
    GridPane mapsPane;
    HBox titleBox, buttonBox;
    VBox mainBox;
    List<ImageView> mapImages;
    int selectedMap = -1;

    public MapChoice(double width, double height, double x, double y) {

        this.setPrefSize(width, height);

        setImages();

        setBoxes(width, height);

        setHandlers();

        this.getTransforms().addAll(
                new Translate(x, y)
        );

        this.setBackground(Maps.MAP_CHOICE_BACKGROUND);
        this.hide();
    }

    private void setImages() {
        mapImages = new ArrayList<>();
        for (int i = 0; i < Maps.MAPS.length; i++) {
            ImageView imgView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gridrunner/puppy.jpg"))));
            mapImages.add(imgView);
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);
        }
    }

    private void setBoxes(double width, double height) {
        mainBox = new VBox(height * 0.05);

        titleBox = new HBox(width * 0.05);
        title = new Label(Maps.MAP_CHOICE_TITLE);
        title.setTextFill(Maps.MAP_CHOICE_TITLE_COLOR);
        title.setFont(Maps.MAP_CHOICE_TITLE_FONT);
        title.setAlignment(Pos.CENTER);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        titleBox.setBackground(new Background(new BackgroundFill(Maps.MAP_CHOICE_CONFIRM_BUTTON_COLOR, null, null)));


        mapsPane = new GridPane(width * 0.03, height * 0.03);
        for (int i = 0; i < mapImages.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                mapsPane.add(mapImages.get(i * 2 + j), i, j);
            }
        }
        mapsPane.setAlignment(Pos.CENTER);

        buttonBox = new HBox(width * 0.05);
        confirm = new Button(Maps.MAP_CHOICE_CONFIRM_BUTTON_TEXT);
        confirm.setTextFill(Maps.MAP_CHOICE_CONFIRM_BUTTON_TEXT_COLOR);
        confirm.setFont(Maps.MAP_CHOICE_CONFIRM_BUTTON_FONT);
        confirm.setBackground(new Background(new BackgroundFill(Maps.MAP_CHOICE_CONFIRM_BUTTON_COLOR, null, null)));
        confirm.setBorder(Border.stroke(Maps.MAP_CHOICE_CONFIRM_BUTTON_STROKE));
        confirm.setAlignment(Pos.CENTER);
        confirm.setEffect(Maps.MAP_CHOICE_CONFIRM_BUTTON_SHADOW);
        confirm.setDisable(true);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(confirm);

        mainBox.getChildren().addAll(titleBox, mapsPane, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        setAlignment(mainBox, Pos.CENTER);

        this.getChildren().add(mainBox);
    }

    private void setHandlers() {
        final int[] selectedI = {-1};
        final ImageView[] prevMap = {null};
        for (int i = 0; i < mapImages.size(); i++) {
            ImageView map = mapImages.get(i);
            selectedI[0] = i;
            map.setOnMouseClicked(
                    event -> {
                        selectedMap = mapImages.indexOf(map);
                        highlightMapImg(prevMap[0], map, true);
                        confirm.setDisable(false);
                        prevMap[0] = map;
                    }
            );
            map.setOnMouseEntered(
                    event -> {
                        highlightMapImg(prevMap[0], map, false);
                    }
            );
            map.setOnMouseExited(
                    event -> {
                        unhighlightMapImg(prevMap[0], map);
                    }
            );
        }
        confirm.setOnMouseEntered(
                event -> {
                    confirm.setEffect(Maps.MAP_CHOICE_CONFIRM_BUTTON_HIGHLIGHT);
                }
        );
        confirm.setOnMouseExited(
                event -> {
                    confirm.setEffect(Maps.MAP_CHOICE_CONFIRM_BUTTON_SHADOW);
                }
        );
        confirm.setOnMouseClicked(
                event -> {
                    this.hide();
                    trigger();
                }
        );
    }

    private void highlightMapImg(ImageView prevMap, ImageView map, boolean selected) {
        if (!selected)
            map.setEffect(Maps.HIGHLIGHT_MAP_IMG_SHADOW);
        else {
            map.setEffect(Maps.SELECTED_MAP_IMG_SHADOW);

            if (prevMap != null)
                unhighlightMapImg(null, prevMap);
        }
    }

    private void unhighlightMapImg(ImageView prevMap, ImageView map) {
        if (map == prevMap)
            map.setEffect(Maps.SELECTED_MAP_IMG_SHADOW);
        else
            map.setEffect(Maps.DEFAULT_MAP_IMG_SHADOW);
    }

    public String[] getMap() {
        return Maps.MAPS[selectedMap];
    }

    public Image getMapBackground() {
        return Maps.MAP_BACKGROUNDS_IMAGE[selectedMap];
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }
}
