package astunc.models.gameObjects;

import javafx.scene.image.ImageView;

public class Player implements FriendlyEntity {
    private final int DELTA = 25;

    private int x, y, width, height;
    private boolean play;

    public Player(ImageView imageView) {
        width = (int) imageView.getBoundsInLocal().getWidth();
        height = (int) imageView.getBoundsInLocal().getWidth();
        x = (600 - width) / 2;
        y = 700 - height;
        play = false;
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
        if(play)
            y -= DELTA;
    }

    @Override
    public void down() {
        if(play)
            y += DELTA;
    }

    @Override
    public void left() {
        if(play)
            x -= DELTA;
    }

    @Override
    public void right() {
        if(play)
            x += DELTA;
    }

    public void play(){
        play = true;
    }

    public boolean isPlay() {
        return play;
    }
}
