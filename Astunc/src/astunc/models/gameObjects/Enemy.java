package astunc.models.gameObjects;

import javafx.scene.image.ImageView;

import java.util.Date;

/**
 * Created by Alchemistake on 30/04/16.
 */
public class Enemy implements EnemyEntity {
    private final int DELTA = 15;
    private final int PROJECTILE_COUNTER = 13;

    private int x, y, width, height, projectile_counter;
    private boolean alive;

    public Enemy(int x, int y, ImageView imageView) {
        this.x = x;
        this.y = y;

        alive = true;

        projectile_counter = PROJECTILE_COUNTER;

        width = (int) imageView.getBoundsInLocal().getWidth();
        height = (int) imageView.getBoundsInLocal().getWidth();
    }

    @Override
    public boolean kill(int x, int y) {
        if (this.x < x && x < this.x + width && this.y < y && y < this.y + height)
            alive = false;
        return !alive;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public int getX() {
        validate();
        return x;
    }

    @Override
    public int getY() {
        validate();
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void up() {
        y -= DELTA;
    }

    @Override
    public void down() {
        y += DELTA;
    }

    @Override
    public void left() {
        x -= DELTA;
    }

    @Override
    public void right() {
        x += DELTA;
    }


    @Override
    public void validate() {
        if (x < -width / 2)
            x = -width / 2;
        else if (x > 600 - width / 2)
            x = 600 - width / 2;
        if (y < -height / 2)
            y = -height / 2;
        else if (y > 800 - height / 2)
            y = 800 - height / 2;
    }

    public boolean fire(){
        projectile_counter--;
        if(projectile_counter < 0)
            projectile_counter = PROJECTILE_COUNTER;
        return  projectile_counter == PROJECTILE_COUNTER;
    }
}
