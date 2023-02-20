package es.starfallstudios.fallenlegends;

import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;

    private ArrayList<Zone> zones;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager() {
        zones = new ArrayList<Zone>();
    }

}