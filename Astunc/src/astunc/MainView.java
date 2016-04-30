package astunc;


import astunc.controllers.ControllableScreen;
import astunc.models.DataManager;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;

public class MainView extends StackPane {
    private static final double BY_VALUE = 0.1, DURATION = 0.5;

    private HashMap<String, ControllableScreen> controls = new HashMap<>();
    private DataManager dataManager;
    private ControllableScreen currentControllableScreen;

    public MainView(DataManager dataManager) {
        super();
        this.dataManager = dataManager;
    }

    public void add(String name, ControllableScreen controllableScreen) {
        controls.put(name, controllableScreen);
    }

    public ControllableScreen get(String name) {
        return controls.get(name);
    }

    public boolean loadScreen(String name) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("fxml/" + name + ".fxml"));
            Parent loadScreen = (Parent) myLoader.load();
            ControllableScreen myScreenControler = myLoader.getController();
            myScreenControler.setScreenParent(this);
            add(name, myScreenControler);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (get(name) != null && get(name) != currentControllableScreen) {
            if (!getChildren().isEmpty()) {
                currentControllableScreen.unload();
                get(name).load();
                getChildren().add(1, get(name).getRoot());     //add the screen

                FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(DURATION), currentControllableScreen.getRoot());
                fadeOutTransition.setFromValue(1);
                fadeOutTransition.setInterpolator(Interpolator.EASE_OUT);
                fadeOutTransition.setToValue(0);
                fadeOutTransition.setOnFinished(event -> getChildren().remove(0));

                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(DURATION), get(name).getRoot());
                fadeInTransition.setFromValue(0);
                fadeInTransition.setInterpolator(Interpolator.EASE_IN);
                fadeInTransition.setToValue(1);

                fadeOutTransition.play();
                fadeInTransition.play();
            } else {
                get(name).load();
                getChildren().add(get(name).getRoot());     //add the screen

                FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(DURATION), get(name).getRoot());
                fadeInTransition.setFromValue(0);
                fadeInTransition.setInterpolator(Interpolator.EASE_IN);
                fadeInTransition.setToValue(1);

                fadeInTransition.play();
            }
            currentControllableScreen = get(name);
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!!! \n");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (controls.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}