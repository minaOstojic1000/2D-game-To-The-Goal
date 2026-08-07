package gridrunner.interfaces;

import gridrunner.Player;

import java.util.ArrayList;
import java.util.List;

public interface IPowerUp extends IPickup {
    List<IPowerUp> powerUps = new ArrayList<>();

    default void affect(Player player) {
        player.claimReward(1);
    }

    static List<IPowerUp> getPowerUps() {
        return powerUps;
    }

    static void addPowerUp(IPowerUp powerUp) {
        powerUps.add(powerUp);
        pickups.add(powerUp);
    }

    static void removePowerUp(IPowerUp powerUp) {
        powerUps.remove(powerUp);
        pickups.remove(powerUp);
    }
}
