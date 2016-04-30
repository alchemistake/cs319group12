package astunc.models.gameObjects;

/**
 * Created by Alchemistake on 30/04/16.
 */
public interface Entity {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void up();
    void down();
    void left();
    void right();
    void validate();
}
