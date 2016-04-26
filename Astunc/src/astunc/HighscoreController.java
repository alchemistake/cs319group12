package astunc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Alchemistake on 26/04/16.
 */
public class HighscoreController implements ControllableScreen {
    ScreensController screenParent;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.screenParent = screenPage;
    }

    @FXML
    private void returnToMainMenu(ActionEvent actionEvent){
        screenParent.setScreen(Main.MAIN_MENU_NAME);
    }
}
