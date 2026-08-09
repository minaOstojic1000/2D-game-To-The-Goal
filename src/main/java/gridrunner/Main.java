package gridrunner;

import gridrunner.constants.Constants;
import gridrunner.constants.Maps;
import gridrunner.gameObjects.Coin;
import gridrunner.gameObjects.Level;
import gridrunner.infoPanes.AdditionalInformation;
import gridrunner.infoPanes.EndOfGame;
import gridrunner.infoPanes.MapChoice;
import gridrunner.interfaces.IPickup;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start ( Stage stage ) {

        StackPane root = new StackPane();

        Input input = new Input();

        GameGenerator generator = new GameGenerator(root, input);

        Scene scene = new Scene ( root, Maps.WINDOW_WIDTH, Maps.WINDOW_HEIGHT );
        scene.setFill ( Constants.BACKGROUND_COLOR );

        scene.setOnKeyPressed ( input::keyPressed );
        scene.setOnKeyReleased ( input::keyReleased );

        generator.generateGame();

        stage.setTitle ( "Do cilja" );
        stage.setScene ( scene );
        stage.setResizable ( false );
        stage.show ( );
    }

    public static void main ( String[] args ) {
        launch ( args );
    }
}