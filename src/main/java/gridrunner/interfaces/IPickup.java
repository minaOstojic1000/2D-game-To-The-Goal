package gridrunner.interfaces;

import gridrunner.playerClasses.Player;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public interface IPickup {

    List<IPickup> pickups = new ArrayList<>();

    void affect(Player player);

    static List<IPickup> getPickups() {
        return pickups;
    }

    static void addPickup(IPickup pickup) { pickups.add(pickup); }

    static void removePickup(IPickup pickup) { pickups.remove(pickup); }

    default boolean touchesPlayer(Player player) {
        List<Shape> shapes = this.getShapes();
        for (Shape playerShape : player.getShapes()) {
            for (Shape myShape : shapes) {
                Shape intersect = Shape.intersect(myShape, playerShape);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    List<Shape> getShapes();

}
