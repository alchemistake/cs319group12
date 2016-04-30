package astunc.controllers;

import astunc.Main;
import astunc.MainView;
import com.sun.tools.javac.util.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alchemistake on 26/04/16.
 */
public class HighscoreController implements ControllableScreen {
    @FXML
    private VBox root;
    @FXML
    private Text title;
    @FXML
    private ListView<String> list;
    @FXML
    private Button exit;

    private MainView screenParent;

    @Override
    public void setScreenParent(MainView screenPage) {
        this.screenParent = screenPage;
    }

    @Override
    public void load() {
        this.list.setItems(getFormattedHighscores());
    }

    @Override
    public void unload() {}

    @Override
    public Node getRoot() {
        return root;
    }

    @FXML
    private void returnToMainMenu(ActionEvent actionEvent){
        screenParent.setScreen(Main.MAIN_MENU_NAME);
    }


    private ObservableList<String> getFormattedHighscores(){
        String[] formatted = new String[10];
        ArrayList<Pair<String,Long>> highscore = screenParent.getDataManager().getHighscores();

        for (int i = 0; i < 10; i++) {
            long millis = highscore.get(i).snd;
            formatted[i] = highscore.get(i).fst + " :  " + String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        }

        return FXCollections.observableArrayList(formatted);
    }
}
