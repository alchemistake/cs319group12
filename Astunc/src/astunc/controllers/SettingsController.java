package astunc.controllers;

import astunc.Main;
import astunc.MainView;
import astunc.models.SettingsModel;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class SettingsController implements ControllableScreen {
    public static final String UP = "Up: ", DOWN = "Down: ", LEFT = "Left: ", RIGHT = "Right: ", SHOOT = "Shoot: ", PAUSE = "Pause: ";
    public static final String[] KEY_NAMES = new String[]{UP, DOWN, LEFT, RIGHT, SHOOT, PAUSE};
    public static final String CSS = "-fx-background-color: blue";

    @FXML
    private HBox root;
    @FXML
    private TextField p1Name, p2Name, p3Name;
    @FXML
    private Button p1Up, p1Down, p1Left, p1Right, p1Shoot, p1Pause;
    @FXML
    private Button p2Up, p2Down, p2Left, p2Right, p2Shoot, p2Pause;
    @FXML
    private Button p3Up, p3Down, p3Left, p3Right, p3Shoot, p3Pause;
    @FXML
    private Button p1Red, p1Blue, p1Gray, p1Cyan;
    @FXML
    private Button p2Red, p2Blue, p2Gray, p2Cyan;
    @FXML
    private Button p3Red, p3Blue, p3Gray, p3Cyan;

    private Button activeKeyCodeButton;
    private String activeKeyCodeButtonPrevText;

    private MainView screenParent;

    private EventHandler<KeyEvent> keyCodePressHandler;
    private ChangeListener<Boolean> changeListener;

    private Button[] p1Keys, p2Keys, p3Keys;
    private Button[] p1Ships, p2Ships, p3Ships;
    private Button[] reds, blues, grays, cyans;
    private Button[][] ships;

    private TextField[] textFields;

    private KeyCode[] p1KeyCodesToSave, p2KeyCodesToSave, p3KeyCodesToSave;
    private KeyCode[][] keyCodesToSave;
    private SettingsModel.SpaceShip[] shipsToBeSaved;

    public SettingsController() {
    }

    @Override
    public void setScreenParent(MainView screenPage) {
        screenParent = screenPage;
    }

    @Override
    public void unload() {
        for (int i = 0; i < 3; i++)
            screenParent.getDataManager().setPlayerSettings(i,keyCodesToSave[i],shipsToBeSaved[i],textFields[i].getText());

        keyCodesToSave = null;
        shipsToBeSaved = null;
        textFields = null;

        keyCodePressHandler = null;

        p1Keys = null;
        p2Keys = null;
        p3Keys = null;

        blues = null;
        reds = null;
        grays = null;
        cyans = null;

        p1Ships = null;
        p2Ships = null;
        p3Ships = null;

        ships = null;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @FXML
    public void setKeyCode(ActionEvent actionEvent) {
        if (activeKeyCodeButton != null) {
            activeKeyCodeButton.setText(activeKeyCodeButtonPrevText);
            activeKeyCodeButton.removeEventFilter(KeyEvent.KEY_PRESSED, keyCodePressHandler);
        }
        activeKeyCodeButton = ((Button) actionEvent.getSource());
        activeKeyCodeButtonPrevText = activeKeyCodeButton.getText();
        activeKeyCodeButton.setText("Press Escape To Cancel");
        activeKeyCodeButton.addEventFilter(KeyEvent.KEY_PRESSED, keyCodePressHandler);
    }

    @FXML
    public void setSpaceShip(ActionEvent actionEvent) {
        Button source = (Button) actionEvent.getSource();
        actionEvent.consume();

        String player = source.getId().substring(0, 2);
        String ship = source.getId().substring(2).toUpperCase();

        int i = 0;
        switch (player) {
            case "p1":
                for (; i < p1Ships.length; i++)
                    if (p1Ships[i].getStyle().equals(CSS)) {
                        p1Ships[i].setStyle("");
                        break;
                    }
                break;
            case "p2":
                for (; i < p2Ships.length; i++)
                    if (p2Ships[i].getStyle().equals(CSS)) {
                        p2Ships[i].setStyle("");
                        break;
                    }
                break;
            case "p3":
                for (; i < p3Ships.length; i++)
                    if (p3Ships[i].getStyle().equals(CSS)) {
                        p3Ships[i].setStyle("");
                        break;
                    }
                break;
        }

        source.setStyle(CSS);

        for (int j = 0; j < ships[i].length; j++) {
            ships[i][j].setDisable(false);
        }

        int p = player.charAt(1) - '1';

        if (ship.equals(SettingsModel.SpaceShip.BLUE.toString())) {
            for (Button blue : blues) {
                blue.setDisable(true);
            }
            shipsToBeSaved[p] = SettingsModel.SpaceShip.BLUE;
        } else if (ship.equals(SettingsModel.SpaceShip.RED.toString())) {
            for (Button red : reds) {
                red.setDisable(true);
            }
            shipsToBeSaved[p] = SettingsModel.SpaceShip.RED;
        } else if (ship.equals(SettingsModel.SpaceShip.GRAY.toString())) {
            for (Button gray : grays) {
                gray.setDisable(true);
            }
            shipsToBeSaved[p] = SettingsModel.SpaceShip.GRAY;
        } else if (ship.equals(SettingsModel.SpaceShip.CYAN.toString())) {
            for (Button cyan : cyans) {
                cyan.setDisable(true);
            }
            shipsToBeSaved[p] = SettingsModel.SpaceShip.CYAN;
        }
    }

    @FXML
    private void returnToMainMenu(ActionEvent actionEvent) {
        screenParent.setScreen(Main.MAIN_MENU_NAME);
    }

    @Override
    public void load() {
        keyCodePressHandler = event -> {
            KeyCode curKeyCode = event.getCode();
            event.consume();
            if (curKeyCode == KeyCode.ESCAPE) {
                activeKeyCodeButton.setText(activeKeyCodeButtonPrevText);
                activeKeyCodeButton.removeEventFilter(KeyEvent.KEY_PRESSED, keyCodePressHandler);
                activeKeyCodeButton = null;
            } else if (validate(curKeyCode)) {
                for (int i = 0; i < 6; i++) {
                    if (activeKeyCodeButtonPrevText.contains(KEY_NAMES[i])) {
                        activeKeyCodeButton.setText(KEY_NAMES[i] + curKeyCode);
                        activeKeyCodeButton.removeEventFilter(KeyEvent.KEY_PRESSED, keyCodePressHandler);
                        keyCodesToSave[(activeKeyCodeButton.getId().charAt(1) - '1')][i] = curKeyCode;
                        activeKeyCodeButton = null;
                        break;
                    }
                }
            }
        };

        p1Keys = new Button[]{p1Up, p1Down, p1Left, p1Right, p1Shoot, p1Pause};
        p2Keys = new Button[]{p2Up, p2Down, p2Left, p2Right, p2Shoot, p2Pause};
        p3Keys = new Button[]{p3Up, p3Down, p3Left, p3Right, p3Shoot, p3Pause};

        p1KeyCodesToSave = screenParent.getDataManager().getPlayerKeys(0);
        p2KeyCodesToSave = screenParent.getDataManager().getPlayerKeys(1);
        p3KeyCodesToSave = screenParent.getDataManager().getPlayerKeys(2);

        keyCodesToSave = new KeyCode[][]{p1KeyCodesToSave,p2KeyCodesToSave,p3KeyCodesToSave};

        for (int i = 0; i < SettingsModel.NO_OF_KEY; i++) {
            p1Keys[i].setText(KEY_NAMES[i] + p1KeyCodesToSave[i]);
            p2Keys[i].setText(KEY_NAMES[i] + p2KeyCodesToSave[i]);
            p3Keys[i].setText(KEY_NAMES[i] + p3KeyCodesToSave[i]);
        }

        blues = new Button[]{p1Blue, p2Blue, p3Blue};
        reds = new Button[]{p1Red, p2Red, p3Red};
        grays = new Button[]{p1Gray, p2Gray, p3Gray};
        cyans = new Button[]{p1Cyan, p2Cyan, p3Cyan};

        p1Ships = new Button[]{p1Red, p1Blue, p1Gray, p1Cyan};
        p2Ships = new Button[]{p2Red, p2Blue, p2Gray, p2Cyan};
        p3Ships = new Button[]{p3Red, p3Blue, p3Gray, p3Cyan};

        ships = new Button[][]{reds, blues, grays, cyans};

        shipsToBeSaved = new SettingsModel.SpaceShip[3];

        for (int i = 0; i < 3; i++) {
            shipsToBeSaved[i] = screenParent.getDataManager().getSpaceship(i);
            switch (shipsToBeSaved[i]) {
                case RED:
                    for (int j = 0; j < 3; j++) {
                        reds[j].setDisable(true);
                    }
                    reds[i].setStyle(CSS);
                    break;
                case BLUE:
                    for (int j = 0; j < 3; j++) {
                        blues[j].setDisable(true);
                    }
                    blues[i].setStyle(CSS);
                    break;
                case GRAY:
                    for (int j = 0; j < 3; j++) {
                        grays[j].setDisable(true);
                    }
                    grays[i].setStyle(CSS);
                    break;
                case CYAN:
                    for (int j = 0; j < 3; j++) {
                        cyans[j].setDisable(true);
                    }
                    cyans[i].setStyle(CSS);
                    break;
            }
        }

        textFields = new TextField[]{p1Name,p2Name,p3Name};

        for (int i = 0; i < 3; i++) {
            textFields[i].setPromptText(screenParent.getDataManager().getPlayerName(i));
        }
    }

    private boolean validate(KeyCode keyCode) {
        for (int i = 0; i < SettingsModel.NO_OF_KEY; i++)
            if (p1KeyCodesToSave[i] == keyCode || p2KeyCodesToSave[i] == keyCode || p3KeyCodesToSave[i] == keyCode)
                return false;
        return true;
    }
}