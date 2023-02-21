package es.starfallstudios.fallenlegends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;
    private static DBManager dbManager = DBManager.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");

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

    public void setZones(ArrayList<Zone> zones) {
        this.zones = zones;
        //dbManager.storeZones(zones);
    }

    public void getZones() {
        zones = dbManager.retrieveZones();
    }

    public ArrayList<Zone> getZonesList() {
        return zones;
    }

}