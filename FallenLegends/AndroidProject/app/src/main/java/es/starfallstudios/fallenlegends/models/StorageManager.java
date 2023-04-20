package es.starfallstudios.fallenlegends.models;

import android.content.SharedPreferences;

public class StorageManager {

    public static StorageManager instance;
    private SharedPreferences sharedPreferences;

    public static StorageManager getInstance() {
        if (instance == null) {
            instance = new StorageManager();
        }
        return instance;
    }

    public void saveZoneVersion(int version) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("zoneVersion", String.valueOf(version));
        editor.apply();
    }

    public int getZoneVersion() {
        return Integer.parseInt(sharedPreferences.getString("zoneVersion", "0"));
    }
}
