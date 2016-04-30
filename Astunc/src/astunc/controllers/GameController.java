package astunc.controllers;

import astunc.MainView;
import astunc.models.GameEngine;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class GameController implements ControllableScreen {
    public static final int STEP_TIME = 20;

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView[] player;

    private MainView screenParent;

    private KeyCode[][] playerKeys;
    private boolean[][] playerPressedKeys;

    private Thread gameEngineThread;
    private Task<Void> notifier;
    private boolean active;

    private GameEngine gameEngine;
    @Override
    public void setScreenParent(MainView screenPage) {
        screenParent = screenPage;
    }

    @Override
    public void load() {
        player = new ImageView[3];

        for (int i = 0; i < 3; i++) {
            player[i] = new ImageView(new Image(getClass().getResource("../images/playerShips/" + screenParent.getDataManager().getSpaceship(i).toString() + ".png").toString()));
            player[i].setPreserveRatio(true);
            player[i].setFitHeight(100);
            player[i].setFitWidth(100);
        }

        gameEngine = new GameEngine();

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

        notifier = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameEngine.calculatePlayerPositions(playerPressedKeys);
                Platform.runLater(() -> update());
                Thread.sleep(STEP_TIME);
                if (active)
                    call();
                return null;
            }
        };

        gameEngineThread = new Thread(notifier);
        gameEngineThread.setDaemon(true);

        active = true;

        for (int i = 0; i < 3; i++)
            root.getChildren().add(player[i]);

        gameEngineThread.start();
    }

    @Override
    public void unload() {
        active = false;
        try {
            gameEngineThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playerKeys = null;
        playerPressedKeys = null;
        gameEngine = null;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    public void update() {
        for (int i = 0; i < 3; i++) {
            player[i].setX(gameEngine.getPlayerX(i));
            player[i].setY(gameEngine.getPlayerY(i));
        }
    }


}
