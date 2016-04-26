package astunc;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static final String MAIN_MENU_NAME = "mainmenu";
    private static final String MAIN_MENU_RESOURCES = MAIN_MENU_NAME + ".fxml";

    public static final String HIGH_SCORE_NAME = "highscore";
    private static final String HIGH_SCORE_RESOURCES = HIGH_SCORE_NAME + ".fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreensController mainContainer = new ScreensController();

        mainContainer.loadScreen(MAIN_MENU_NAME,MAIN_MENU_RESOURCES);
        mainContainer.loadScreen(HIGH_SCORE_NAME,HIGH_SCORE_RESOURCES);

        mainContainer.setScreen(MAIN_MENU_NAME);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        mainContainer.prefWidthProperty().bind(scene.widthProperty());
        mainContainer.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
