package astunc.models;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SettingsModel {
    public static final int NO_OF_KEY = 6;

    private PlayerSettings[] playerSettingses;

    public SettingsModel() {
        playerSettingses = new PlayerSettings[3];
        for (int i = 0; i < 3; i++) {
            playerSettingses[i] = new PlayerSettings();
        }

    }

    private class PlayerSettings {
        // up,down,left,right,shoot,pause
        private KeyCode[] keys;
        private SpaceShip activeShip;
        private String name;

        private PlayerSettings() {
            keys = new KeyCode[NO_OF_KEY];
            activeShip = null;
            name = "YOU ARE NO ONE";
        }

        public PlayerSettings(SpaceShip activeShip, String name) {
            keys = new KeyCode[NO_OF_KEY];
            this.activeShip = activeShip;
            this.name = name;
        }
    }

    public enum SpaceShip {GRAY, RED, BLUE, CYAN}

    public void setPlayerSettings(@NotNull int index,@Nullable KeyCode[] keys, @Nullable SpaceShip activeShip, @Nullable String name) {
        if(keys != null && keys.length >= NO_OF_KEY)
            for (int i = 0; i < NO_OF_KEY; i++) {
                if(keys[i] != null && keys[i] != KeyCode.ESCAPE)
                    playerSettingses[index].keys[i] = keys[i];
            }

        if(activeShip != null)
            playerSettingses[index].activeShip = activeShip;

        if(name != null && !name.isEmpty())
            playerSettingses[index].name = name;
    }

    public void setDefaultSettings(){
        playerSettingses[0].keys = new KeyCode[]{KeyCode.UP,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.ALT,KeyCode.COMMA};
        playerSettingses[1].keys = new KeyCode[]{KeyCode.W,KeyCode.S,KeyCode.A,KeyCode.D,KeyCode.SHIFT,KeyCode.TAB};
        playerSettingses[2].keys = new KeyCode[]{KeyCode.I,KeyCode.J,KeyCode.K,KeyCode.L,KeyCode.U,KeyCode.O};

        playerSettingses[0].activeShip = SpaceShip.GRAY;
        playerSettingses[1].activeShip = SpaceShip.RED;
        playerSettingses[2].activeShip = SpaceShip.BLUE;

        playerSettingses[0].name = "Husnu";
        playerSettingses[1].name = "Husnuye";
        playerSettingses[2].name = "Hayri";
    }

    public KeyCode[] getPlayerKeycodes(int index){
        return playerSettingses[index].keys;
    }

    public SpaceShip getPlayerSpaceShip(int index){
        return playerSettingses[index].activeShip;
    }

    public String getPlayerName(int index){
        return playerSettingses[index].name;
    }
}
