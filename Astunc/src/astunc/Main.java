package astunc;

import astunc.models.DataManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static final String MAIN_MENU_NAME = "mainmenu";
    private static final String MAIN_MENU_RESOURCES = MAIN_MENU_NAME + ".fxml";

    public static final String HIGH_SCORE_NAME = "highscore";
    private static final String HIGH_SCORE_RESOURCES = HIGH_SCORE_NAME + ".fxml";

    public static final String SETTINGS_NAME = "settings";
    private static final String SETTINGS_RESOURCES = SETTINGS_NAME + ".fxml";

    private DataManager dataManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        dataManager = new DataManager();

        MainView mainContainer = new MainView(dataManager);

        mainContainer.loadScreen(MAIN_MENU_NAME,MAIN_MENU_RESOURCES);
        mainContainer.loadScreen(HIGH_SCORE_NAME,HIGH_SCORE_RESOURCES);
        mainContainer.loadScreen(SETTINGS_NAME,SETTINGS_RESOURCES);

        mainContainer.setScreen(MAIN_MENU_NAME);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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
