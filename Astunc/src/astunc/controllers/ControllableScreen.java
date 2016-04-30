package astunc.controllers;

import astunc.MainView;
import javafx.scene.Node;

/**
 * Created by Alchemistake on 26/04/16.
 */
public interface ControllableScreen {
    void setScreenParent(MainView screenPage);

    void load();

    void unload();

    Node getRoot();
}
