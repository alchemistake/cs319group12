package astunc.models;

import astunc.controllers.GameController;
import astunc.models.gameObjects.Enemy;
import astunc.models.gameObjects.Player;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GameEngine {
    private static final int PROJECTILE_CLOCK = 10;
    private static final int ENEMY_CLOCK = 20;
    private static final int ENEMY_COUNT = 20;
    private static final int PENALTY = 5000;

    private Player[] players;
    private GameController gameController;
    private int[] projectileClock;
    private int enemyClock, enemyCount;

    private ArrayList<Enemy> enemies;

    private Random random;

    private long start;
    private int penalty_count;

    public GameEngine(GameController gameController) {
        players = new Player[3];
        this.gameController = gameController;
        projectileClock = new int[3];
        enemyClock = ENEMY_CLOCK;
        enemyCount = ENEMY_COUNT;

        enemies = new ArrayList<>();
        random = new Random();

        penalty_count = 0;

        start = System.currentTimeMillis();
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
            if (inputs[i][4] && projectileClock[i] <= 0) {
                projectileClock[i] = PROJECTILE_CLOCK;
                int finalI = i;
                Platform.runLater(() -> gameController.spawnFriendlyProjectile(players[finalI].getX() + players[finalI].getWidth() / 2, players[finalI].getY()));
            }
            if (inputs[i][5]) {
                requestPause(i);
                inputs[i][5] = false;
            }

            if (projectileClock[i] > 0)
                projectileClock[i]--;
        }
    }

    public void updateEnemies() {
        for (Enemy e : enemies) {
            if (random.nextBoolean())
                e.up();
            if (random.nextBoolean())
                e.down();
            if (random.nextBoolean())
                e.left();
            if (random.nextBoolean())
                e.right();
        }
    }

    public void enemySpawner() {
        if (enemyClock < 0)
            spawnEnemy();
        enemyClock--;
    }

    private void spawnEnemy() {
        enemyClock = ENEMY_CLOCK;
        Platform.runLater(() -> gameController.spawnEnemy());
    }

    public boolean checkEnemyCollision(int x, int y) {
        for (int i = 0; i < enemies.size(); i++)
            if (enemies.get(i).kill(x, y)) {
                int finalI = i;
                Platform.runLater(() -> gameController.killEnemy(finalI));
                return true;
            }
        return false;
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

    public int getEnemyX(int i) {
        return enemies.get(i).getX();
    }

    public int getEnemyY(int i) {
        return enemies.get(i).getY();
    }

    public void addEnemy(ImageView iv) {
        enemies.add(new Enemy(random.nextInt(600), random.nextInt(400), iv));
    }

    public void killEnemy(int i) {
        enemies.remove(i);
        enemyCount--;
        if(enemyCount < 0){
            Platform.runLater(()->gameController.win((System.currentTimeMillis() - start) + PENALTY * penalty_count));
        }
    }

    public boolean getEnemyFire(int i) {
        return enemies.get(i).fire();
    }

    public boolean checkFriendlyCollision(int x, int y) {
        for (int i = 0; i < 3; i++)
            if (players[i].isPlay() && players[i].penalty(x,y)) {
                penalty_count++;
                return true;
            }
        return false;
    }

    public boolean getPlayerPlay(int i) {
        return players[i].isPlay();
    }

    public int getEnemyWidth(int i) {
        return enemies.get(i).getWidth();
    }
}
