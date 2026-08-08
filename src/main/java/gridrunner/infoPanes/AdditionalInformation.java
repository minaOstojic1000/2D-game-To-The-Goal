package gridrunner.infoPanes;

import gridrunner.constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Translate;

import static javafx.geometry.Pos.CENTER;

public class AdditionalInformation extends StackPane {

    private Label timeLabel;
    private Label pointsLabel;
    long startTime;

    public AdditionalInformation(double paneWidth, double paneHeight, double paneX, double paneY) {

        super();

        setLabels();

        this.setPrefSize(paneWidth, paneHeight);

        this.getTransforms().add(
                new Translate(paneX, paneY)
        );

        this.hide();
    }

    private void setLabels() {
        timeLabel = new Label(String.format(Constants.TIME_FORMAT, 0, 0));
        timeLabel.setTextFill(Constants.LABEL_TIME_COLOR);
        timeLabel.setFont(Constants.LABEL_TIME_FONT);
        timeLabel.setAlignment(CENTER);

        pointsLabel = new Label(String.format(Constants.POINTS_FORMAT, 0));
        pointsLabel.setTextFill(Constants.LABEL_POINTS_COLOR);
        pointsLabel.setFont(Constants.LABEL_POINTS_FONT);
        pointsLabel.setAlignment(Pos.CENTER_LEFT);

        setAlignment(timeLabel, Pos.CENTER);
        setAlignment(pointsLabel, Pos.CENTER_RIGHT);

        this.getChildren().addAll(timeLabel, pointsLabel);
    }


    public void updateTimeLabel(long now) {
        if (startTime == 0)
            startTime = now;
        long passedSeconds = (now - startTime) / 1_000_000_000;
        long minutes = passedSeconds / 60;
        long seconds = passedSeconds % 60;
        timeLabel.setText(String.format(Constants.TIME_FORMAT, minutes, seconds));
    }

    public void updatePointsLabel(int points) {
        pointsLabel.setText(String.format(Constants.POINTS_FORMAT, points));
    }

    public void updateAll(long now, int points) {
        updateTimeLabel(now);
        updatePointsLabel(points);
    }

    public String getTimeLabelText() {
        return timeLabel.getText();
    }

    public String getPointsLabelText() {
        return pointsLabel.getText();
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }
}
