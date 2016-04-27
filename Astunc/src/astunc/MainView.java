package astunc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import astunc.controllers.ControllableScreen;
import astunc.models.DataManager;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainView extends StackPane {
    private HashMap<String, Node> screens = new HashMap<>();
    private DataManager dataManager;

    public MainView(DataManager dataManager) {
        super();
        this.dataManager = dataManager;
    }

        public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

        public Node getScreen(String name) {
        return screens.get(name);
    }

        public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("fxml/" + resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControllableScreen myScreenControler = ((ControllableScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {
                /*
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);                    //remove the displayed screen
                                getChildren().add(0, screens.get(name));     //add the screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();
                */
                getChildren().remove(0);                    //remove the displayed screen
                getChildren().add(0, screens.get(name));     //add the screen
            } else {
                //setOpacity(0.0);
                getChildren().add(screens.get(name));
                //Timeline fadeIn = new Timeline(
                //        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                //        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
                //fadeIn.play();
            }
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!!! \n");
            return false;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
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