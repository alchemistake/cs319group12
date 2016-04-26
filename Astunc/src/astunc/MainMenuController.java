package astunc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Alchemistake on 26/04/16.
 */
public class MainMenuController implements ControllableScreen{
    ScreensController screenParent;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        screenParent = screenPage;
    }

    @FXML
    public void closeGame(ActionEvent actionEvent){
        //TODO - House Keeping

        System.out.println("Bye bye! :/");

        Platform.exit();
    }

    @FXML
    public void showHighscores(ActionEvent actionEvent){
        System.out.println("hi score");

        screenParent.setScreen(Main.HIGH_SCORE_NAME);
    }
}
