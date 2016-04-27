package astunc.models;

import astunc.models.SettingsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.javac.util.Pair;
import javafx.scene.input.KeyCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;

public class DataManager {
    // CONSTANTS
    private static final Long HOUR = (long) 1000 * 60 * 60;
    private static final Long DAY = HOUR * 24;
    // PROPERTIES
    private Gson gson;

    private ArrayList<Pair<String, Long>> highscores;
    private SettingsModel settings;

    private String highscoresJSON, settingsJSON;
    private File saveFile;

    // CONSTRUCTOR
    public DataManager() {
        super();
        gson = new Gson();
        initiliazeJSONs();
        loadHighscores();
        loadSettings();
        writeJSONs();
    }

    // METHODS - Private
    private void fileCheck() {
        if (!saveFile.exists()) {
            System.out.println("We don't have the files jack.");
            saveFile.getParentFile().mkdirs();
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initiliazeJSONs() {
        saveFile = new File(System.getProperty("user.home") + "/Astunc/Config.conf");

        fileCheck();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(saveFile));


            highscoresJSON = bufferedReader.readLine();
            settingsJSON = bufferedReader.readLine();

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadHighscores() {
        Type collectionType = new TypeToken<ArrayList<Pair<String, Long>>>() {
        }.getType();
        highscores = gson.fromJson(highscoresJSON, collectionType);
        if (highscores == null) {
            System.out.println("We don't have the jasons jack.");
            generateDefaultHighscore();
        }
    }

    private void saveHighscore() {
        highscoresJSON = gson.toJson(highscores);
    }

    private void generateDefaultHighscore() {
        highscores = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            highscores.add(Pair.of("Generic Highscore Name", DAY + i * HOUR));
        }

        saveHighscore();
    }

    private void loadSettings() {
        settings = gson.fromJson(settingsJSON, SettingsModel.class);
        if (settings == null) {
            settings = new SettingsModel();
            settings.setDefaultSettings();
        }

        saveSettings();
    }

    private void saveSettings() {
        settingsJSON = gson.toJson(settings);
    }

    // METHODS - Public
    public ArrayList<Pair<String, Long>> getHighscores() {
        return highscores;
    }

    public void writeJSONs() {
        fileCheck();

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(saveFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printWriter.println(highscoresJSON);
        printWriter.println(settingsJSON);

        printWriter.close();
    }

    public void updateHighscores(Pair<String, Long> newHighScore) {
        highscores.add(newHighScore);
        highscores.sort(new HighscoreComparator());
        highscores.remove(highscores.size() - 1);

        saveHighscore();
    }

    public void setPlayerSettings(@NotNull int index, @Nullable KeyCode[] keys, SettingsModel.@Nullable SpaceShip activeShip, @Nullable String name) {
        settings.setPlayerSettings(index, keys, activeShip, name);
        saveSettings();
    }

    public KeyCode[] getPlayerKeys(int index) {
        return settings.getPlayerKeycodes(index);
    }

    public SettingsModel.SpaceShip getSpaceship(int index) {
        return settings.getPlayerSpaceShip(index);
    }

    public String getPlayerName(int index){ return settings.getPlayerName(index);}

    // INNER CLASSES
    private class HighscoreComparator implements Comparator<Pair<String, Long>> {
        @Override
        public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
            return Long.compare(o1.snd, o2.snd);
        }
    }
}
