package astunc.models;

import astunc.controllers.GameController;
import astunc.models.gameObjects.Player;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class GameEngine {
    private static final int PROJECTILE_CLOCK = 10;

    private Player[] players;
    private GameController gameController;
    private int[] projectileClock;

    public GameEngine(GameController gameController) {
        players = new Player[3];
        this.gameController = gameController;
        projectileClock = new int[3];
    }

    public void setPlayers(ImageView[] ships) {
        for (int i = 0; i < 3; i++) {
            players[i] = new Player(ships[i]);
        }
        players[0].play();
    }

    public void calculatePlayerPositions(boolean[][] inputs) {
        for (int i = 0; i < 3; i++) {
            if (inputs[i][0])
                players[i].up();
            if (inputs[i][1])
                players[i].down();
            if (inputs[i][2])
                players[i].left();
            if (inputs[i][3])
                players[i].right();
            if (inputs[i][4] && projectileClock[i] <= 0){
                projectileClock[i] = PROJECTILE_CLOCK;
                int finalI = i;
                Platform.runLater(() -> gameController.spawnFriendlyProjectile(players[finalI].getX() + players[finalI].getWidth() / 2 , players[finalI].getY()));
            }
            if (inputs[i][5]){
                requestPause(i);
                inputs[i][5] = false;
            }

            if(projectileClock[i] > 0)
                projectileClock[i]--;
        }
    }

    public int getPlayerX(int i) {
        return players[i].getX();
    }

    public int getPlayerY(int i) {
        return players[i].getY();
    }

    private void requestPause(int requester) {
        if (players[requester].isPlay()) {
            Platform.runLater(() -> gameController.pause());
        } else {
            players[requester].play();
            Platform.runLater(() -> gameController.spawnPlayer(requester));
        }
    }
}
