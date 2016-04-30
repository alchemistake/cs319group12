package astunc;

import astunc.models.DataManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static final String MAIN_MENU_NAME = "mainmenu";
    public static final String HIGH_SCORE_NAME = "highscore";
    public static final String SETTINGS_NAME = "settings";
    public static final String GAME_NAME = "game";

    private DataManager dataManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        dataManager = new DataManager();

        MainView mainContainer = new MainView(dataManager);

        mainContainer.loadScreen(MAIN_MENU_NAME);
        mainContainer.loadScreen(HIGH_SCORE_NAME);
        mainContainer.loadScreen(SETTINGS_NAME);
        mainContainer.loadScreen(GAME_NAME);

        mainContainer.setScreen(MAIN_MENU_NAME);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root,600,800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.initStyle(StageStyle.UTILITY);
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        mainContainer.prefWidthProperty().bind(scene.widthProperty());
        mainContainer.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        dataManager.writeJSONs();
        super.stop();
    }
}
