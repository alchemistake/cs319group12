package astunc.models.gameObjects;

import javafx.scene.image.ImageView;

/**
 * Created by Alchemistake on 30/04/16.
 */
public class Player implements FriendlyEntity {
    private int x, y, width, height;

    public Player(ImageView imageView) {
        width = (int) imageView.getBoundsInLocal().getWidth();
        height = (int) imageView.getBoundsInLocal().getWidth();
    }

    @Override
    public void validate() {
        if (x < -width / 2)
            x = -width / 2;
        else if (x > 600 + width / 2)
            x = 600 + width / 2;
        if (y < -height / 2)
            y = -height / 2;
        else if (y > 800 + height / 2)
            y = 800 + height / 2;
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
        y--;
    }

    @Override
    public void down() {
        y++;
    }

    @Override
    public void left() {
        x--;
    }

    @Override
    public void right() {
        x++;
    }
}
