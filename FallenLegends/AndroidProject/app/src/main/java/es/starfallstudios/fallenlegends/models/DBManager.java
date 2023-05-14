package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.views.MainActivity;

public class DBManager {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");
    private static DBManager instance;

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public boolean checkForUpdate() {
        DatabaseReference myRef = database.getReference("version");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int version = dataSnapshot.getValue(Integer.class);
                if (version > GameManager.getInstance().getZoneVersion()) {
                    StorageManager.getInstance().saveZoneVersion(version);
                    MainActivity.zonesLoaded = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
        return false;
    }

    public ArrayList<Zone> retrieveZones() {
        ArrayList<Zone> zones = new ArrayList<>();
        DatabaseReference myRef = database.getReference("zones");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    int id = zoneSnapshot.child("id").getValue(Integer.class);
                    String name = zoneSnapshot.child("name").getValue(String.class);
                    String owner = zoneSnapshot.child("owner").getValue(String.class);

                    ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

                    for (DataSnapshot pointSnapshot : zoneSnapshot.child("points").getChildren()) {
                        double latitude = pointSnapshot.child("0").getValue(Double.class);
                        double longitude = pointSnapshot.child("1").getValue(Double.class);
                        points.add(new GeoPoint(latitude, longitude));
                    }

                    zones.add(new Zone(owner, id, name, points));

                }
                Log.d("FIREBASE", "Zones updated!");
                MainActivity.zonesLoaded = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        return zones;
    }

    public void retrieveMapEntities(ValueEventListener listener) {
        DatabaseReference myRef = database.getReference("creatures");
        myRef.addValueEventListener(listener);
    }

    public void spawnRandomCreatureNearby() {
        DatabaseReference myRef = database.getReference("creatures");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int id = (int) dataSnapshot.getChildrenCount() + 1;

                GeoPoint pos = Utils.getRandomLocationInRange(GameManager.getInstance().getUserLocation(), 100);

                myRef.runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        currentData.child(String.valueOf(id)).child("latitude").setValue(pos.getLatitude());
                        currentData.child(String.valueOf(id)).child("longitude").setValue(pos.getLongitude());
                        currentData.child(String.valueOf(id)).child("type").setValue(Utils.getRandomNumberInRange(0, Creature.BaseCreatures.values().length - 2));
                        currentData.child(String.valueOf(id)).child("tier").setValue(Utils.getRandomNumberInRange(0, 4));
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        Log.d("FIREBASE", "Creature spawned!");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
    }

}
