package gridrunner;

import java.util.ArrayList;
import java.util.List;

public interface IEnemy {

    static List<IEnemy> enemies = new ArrayList<>();

    public void doDamage(Player player);

    public static List<IEnemy> getEnemies() {
        return enemies;
    }

    public static void addEnemy(IEnemy enemy) {
        enemies.add(enemy);
    }

    public static void removeEnemy(IEnemy enemy) {
        enemies.remove(enemy);
    }
}
