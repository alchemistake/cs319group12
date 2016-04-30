package astunc.controllers;

import astunc.Main;
import astunc.MainView;
import astunc.models.DataManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Created by Alchemistake on 26/04/16.
 */
public class MainMenuController implements ControllableScreen{
    @FXML
    private VBox root;

    private MainView screenParent;
    private DataManager dataManager;

    @Override
    public void setScreenParent(MainView screenPage) {
        screenParent = screenPage;
    }

    @FXML
    public void closeGame(ActionEvent actionEvent){
        Platform.exit();
    }

    @FXML
    public void showHighscores(ActionEvent actionEvent){
        screenParent.setScreen(Main.HIGH_SCORE_NAME);
    }

    @FXML
    public void showSettings(ActionEvent actionEvent){
        screenParent.setScreen(Main.SETTINGS_NAME);
    }

    @FXML
    public void showGame(ActionEvent actionEvent){
        screenParent.setScreen(Main.GAME_NAME);
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {}

    @Override
    public Node getRoot() {
        return root;
    }
}
