package gridrunner.interfaces;

import gridrunner.playerClasses.Player;

import java.util.ArrayList;
import java.util.List;

public interface IEnemy extends IPickup{

    List<IEnemy> enemies = new ArrayList<>();

    default void affect(Player player) {
        player.takeDamage(1);
    }

    static List<IEnemy> getEnemies() {
        return enemies;
    }

    static void addEnemy(IEnemy enemy) {
        enemies.add(enemy);
        pickups.add(enemy);
    }

    static void removeEnemy(IEnemy enemy) {
        enemies.remove(enemy);
        pickups.remove(enemy);
    }
}
