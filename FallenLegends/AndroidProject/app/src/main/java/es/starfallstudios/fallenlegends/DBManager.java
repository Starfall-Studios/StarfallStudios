package es.starfallstudios.fallenlegends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DBManager {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void storeZones(ArrayList<Zone> zones) {
        DatabaseReference myRef = database.getReference();
        for (Zone zone : zones) {
            myRef.child("zones").child(Integer.toString(zone.getId())).setValue(zone.toJSON());
        }
    }

    public ArrayList<Zone> retrieveZones() {
        ArrayList<Zone> zones = new ArrayList<Zone>();
        DatabaseReference myRef = database.getReference("zones");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    String data = zoneSnapshot.getValue(String.class);
                    System.out.println(data);
                    Zone zone = Zone.fromJSON(data);
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

        return zones;
    }
}
