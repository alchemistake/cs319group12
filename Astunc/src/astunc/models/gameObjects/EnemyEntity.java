package astunc.models.gameObjects;

/**
 * Created by Alchemistake on 30/04/16.
 */
public interface EnemyEntity extends Entity {
    boolean kill(int x, int y);
    boolean isAlive();
}
