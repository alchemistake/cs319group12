package astunc.controllers;

import astunc.models.DataManager;
import astunc.Main;
import astunc.MainView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Alchemistake on 26/04/16.
 */
public class MainMenuController implements ControllableScreen{
    MainView screenParent;
    DataManager dataManager;

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
}
