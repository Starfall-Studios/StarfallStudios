package es.starfallstudios.fallenlegends;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.util.GeoPoint;

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

    public ArrayList<Zone> retrieveZones() {
        ArrayList<Zone> zones = new ArrayList<Zone>();
        DatabaseReference myRef = database.getReference("zones");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    int id = zoneSnapshot.child("id").getValue(Integer.class);
                    String name = zoneSnapshot.child("name").getValue(String.class);
                    int owner = zoneSnapshot.child("owner").getValue(Integer.class);

                    ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

                    for (DataSnapshot pointSnapshot : zoneSnapshot.child("points").getChildren()) {
                        double latitude = pointSnapshot.child("0").getValue(Double.class);
                        double longitude = pointSnapshot.child("1").getValue(Double.class);
                        points.add(new GeoPoint(latitude, longitude));
                    }

                    zones.add(new Zone(id, owner, name, points));

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
