package gridrunner.infoPanes;

import gridrunner.constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;

public class EndOfGame extends StackPane {

    Label text;

    public EndOfGame(double width, double height, double x, double y) {

        super();

        text = new Label();
        text.setTextFill(Constants.LABEL_END_GAME_COLOR);
        text.setFont(Constants.LABEL_END_GAME_FONT);

        this.getChildren().add(text);
        setAlignment(text, Pos.CENTER);

        this.setPrefSize(width, height);
        this.getTransforms().addAll(
                new Translate(x, y)
        );

        this.setBackground(Constants.PANEL_END_GAME_BACKGROUND);
        this.setOpacity(Constants.PANEL_END_GAME_OPACITY);
        this.hide();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return this.text.getText();
    }

    public void show() {
        setVisible(true);
    }

    public void show(String text) {
        setText(text);
        show();
    }

    public void hide() {
        setVisible(false);
    }
}
