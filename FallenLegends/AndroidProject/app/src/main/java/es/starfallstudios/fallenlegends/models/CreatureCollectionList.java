package es.starfallstudios.fallenlegends.models;

import android.util.Log;

public class CreatureCollectionList {

    private int creatureResourceId;
    private String creatureName;

    public CreatureCollectionList(int creatureResourceId, String creatureName) {
        this.creatureResourceId = creatureResourceId;
        this.creatureName = creatureName;
    }

    public int getCreatureResourceId() {
        return creatureResourceId;
    }

    public String getCreatureName() {
        Log.w("CreatureCollectionList", "getCreatureName: " + creatureName);
        return creatureName;
    }

}
