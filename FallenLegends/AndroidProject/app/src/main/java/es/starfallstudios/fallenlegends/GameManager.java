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
        DatabaseReference myRef = database.getReference();

        for (Zone zone : zones) {
            myRef.child("zones").child(Integer.toString(zone.getId())).setValue(zone);
        }
    }

    public void getZones() {
        DatabaseReference myRef = database.getReference("zones");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    Zone zone = zoneSnapshot.getValue(Zone.class);
                    zones.add(zone);
                }
                Log.d("FIREBASE", "Zones updated!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

    public ArrayList<Zone> getZonesList() {
        return zones;
    }

}