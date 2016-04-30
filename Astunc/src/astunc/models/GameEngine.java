package astunc.models;

import astunc.models.gameObjects.Player;
import javafx.scene.image.ImageView;

/**
 * Created by Alchemistake on 30/04/16.
 */
public class GameEngine {
    Player[] players;

    public GameEngine() {
        players = new Player[3];
    }

    public void setPlayers(ImageView[] ships) {
        for (int i = 0; i < 3; i++) {
            players[i] = new Player(ships[i]);
        }
    }

    public void calculatePlayerPositions(boolean[][] inputs){
        for (int i = 0; i < 3; i++) {
            if(inputs[i][0])
                players[i].up();
            if(inputs[i][1])
                players[i].down();
            if(inputs[i][2])
                players[i].left();
            if(inputs[i][3])
                players[i].right();
        }
    }

    public int getPlayerX(int i){
        return players[i].getX();
    }
    public int getPlayerY(int i){
        return players[i].getY();
    }
}
