package astunc.controllers;

import astunc.Main;
import astunc.MainView;
import astunc.models.DataManager;
import astunc.models.GameEngine;
import astunc.models.gameObjects.Enemy;
import com.sun.tools.javac.util.Pair;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController implements ControllableScreen {
    private static final int STEP_TIME = 50;

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView[] player;

    private MainView screenParent;

    private KeyCode[][] playerKeys;
    private boolean[][] playerPressedKeys;

    private Thread gameEngineThread;
    private boolean active;

    private GameEngine gameEngine;

    private ArrayList<Line> friendlyProjetiles, enemyProjectiles;
    private ArrayList<ImageView> enemy;

    private MotionBlur motionBlur;

    @Override
    public void setScreenParent(MainView screenPage) {
        screenParent = screenPage;
    }

    @Override
    public void load() {
        motionBlur = new MotionBlur();
        motionBlur.setAngle(90);
        motionBlur.setRadius(30);

        friendlyProjetiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
        enemy = new ArrayList<>();

        player = new ImageView[3];

        for (int i = 0; i < 3; i++) {
            player[i] = new ImageView(new Image(getClass().getResource("../images/playerShips/" + screenParent.getDataManager().getSpaceship(i).toString() + ".png").toString()));
            player[i].setPreserveRatio(true);
            player[i].setFitHeight(100);
            player[i].setFitWidth(100);
        }

        gameEngine = new GameEngine(this);

        gameEngine.setPlayers(player);

        playerKeys = new KeyCode[3][6];
        playerPressedKeys = new boolean[3][6];
        for (int i = 0; i < 3; i++) {
            playerKeys[i] = screenParent.getDataManager().getPlayerKeys(i);
        }

        root.setFocusTraversable(true);
        root.requestFocus();

        root.setOnKeyReleased(event -> {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 6; j++)
                    if (event.getCode() == playerKeys[i][j])
                        playerPressedKeys[i][j] = false;
        });

        root.setOnKeyPressed(event -> {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 6; j++)
                    if (!playerPressedKeys[i][j] && event.getCode() == playerKeys[i][j])
                        playerPressedKeys[i][j] = true;
        });

        active = true;

        root.getChildren().add(player[0]);

        startEngine();
    }

    @Override
    public void unload() {
        motionBlur = null;

        friendlyProjetiles = null;
        enemyProjectiles = null;
        enemy = null;

        active = false;
        try {
            gameEngineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playerKeys = null;
        playerPressedKeys = null;
        gameEngine = null;

        root.setOnKeyReleased(null);
        root.setOnKeyPressed(null);
        root.getChildren().removeAll(root.getChildren());
    }

    @Override
    public Node getRoot() {
        return root;
    }

    private void update() {
        for (int i = 0; i < 3; i++) {
            TranslateTransition tt = new TranslateTransition(Duration.millis(STEP_TIME), player[i]);
            tt.setToX(gameEngine.getPlayerX(i));
            tt.setToY(gameEngine.getPlayerY(i));
            tt.setInterpolator(Interpolator.LINEAR);
            tt.play();
        }

        for (Line l :
                friendlyProjetiles) {
            l.setLayoutY(l.getLayoutY() - 50);
            if (l.getLayoutY() + l.getEndY() < 0) {
                Platform.runLater(() -> friendlyProjetiles.remove(l));
                root.getChildren().remove(l);
            }
        }

        for (int i = 0; i < enemy.size(); i++) {
            enemy.get(i).setX(gameEngine.getEnemyX(i));
            enemy.get(i).setY(gameEngine.getEnemyY(i));
            if(gameEngine.getEnemyFire(i))
                spawnEnemyProjectile(gameEngine.getEnemyX(i) + gameEngine.getEnemyWidth(i) / 2,gameEngine.getEnemyY(i));
        }

        for (Line l :
                enemyProjectiles) {
            l.setLayoutY(l.getLayoutY() + 50);
            if (l.getLayoutY() + l.getEndY() > 800) {
                Platform.runLater(() -> enemyProjectiles.remove(l));
                root.getChildren().remove(l);
            }

        }
    }

    private void spawn(ImageView view) {
        root.getChildren().add(view);
    }

    public void spawnPlayer(int p) {
        spawn(player[p]);
    }

    public void pause() {
        if (active) {

            Button game = new Button("Return to Game");
            game.setLayoutX(300);
            game.setLayoutY(300);
            game.setOnAction(event -> {

                root.getChildren().remove(0);
                root.getChildren().remove(0);

                active = true;
                startEngine();
            });

            Button menu = new Button("Back to Main Menu");
            menu.setLayoutX(300);
            menu.setLayoutY(500);
            menu.setOnAction(event -> screenParent.setScreen(Main.MAIN_MENU_NAME));

            root.getChildren().add(0, game);
            root.getChildren().add(0, menu);
            active = false;
        }
    }

    private void startEngine() {
        gameEngineThread = new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                while (active) {
                    gameEngine.calculatePlayerPositions(playerPressedKeys);
                    gameEngine.updateEnemies();

                    friendlyProjetiles.stream().filter(l -> gameEngine.checkEnemyCollision((int) (l.getLayoutX() + l.getEndX()), (int) (l.getLayoutY() + l.getStartY()))).forEach(l -> {

                        Platform.runLater(() -> {
                            root.getChildren().remove(l);
                            friendlyProjetiles.remove(l);
                        });
                    });

                    gameEngine.enemySpawner();

                    enemyProjectiles.stream().filter(l -> gameEngine.checkFriendlyCollision((int) (l.getLayoutX() + l.getEndX()), (int) (l.getLayoutY() + l.getStartY()))).forEach(l -> {

                        Platform.runLater(() -> {
                            root.getChildren().remove(l);
                            enemyProjectiles.remove(l);
                        });
                    });

                    Platform.runLater(() -> update());
                    try {
                        Thread.sleep(STEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        });
        gameEngineThread.setDaemon(true);

        gameEngineThread.start();
    }

    public void spawnFriendlyProjectile(int x, int y) {
        Line line = new Line(x, y, x, y + 20);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStroke(Color.RED);
        line.setStrokeWidth(10);

        line.setEffect(motionBlur);

        friendlyProjetiles.add(line);
        root.getChildren().add(0, line);
    }

    public void spawnEnemyProjectile(int x, int y) {
        Line line = new Line(x, y, x, y - 20);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(10);

        line.setEffect(motionBlur);

        enemyProjectiles.add(line);
        root.getChildren().add(0, line);
    }

    public void killEnemy(int i) {
        root.getChildren().remove(enemy.get(i));

        enemy.remove(i);
        gameEngine.killEnemy(i);
    }

    public void spawnEnemy() {
        ImageView iv = new ImageView(new Image(getClass().getResource("../images/alienShips/alien" + (int) ((Math.random() * 15) + 1) + ".png").toString()));
        iv.setPreserveRatio(true);
        iv.setFitHeight(100);
        iv.setFitWidth(100);

        enemy.add(iv);
        gameEngine.addEnemy(iv);
        root.getChildren().add(iv);
    }

    public void win(long l) {
        DataManager dm = screenParent.getDataManager();
        StringBuilder players = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if(gameEngine.getPlayerPlay(i)){
                players.append(dm.getPlayerName(i));
                players.append('&');
            }
        }
        dm.updateHighscores(new Pair<>(players.toString(),l));

        screenParent.setScreen(Main.MAIN_MENU_NAME);
    }
}
