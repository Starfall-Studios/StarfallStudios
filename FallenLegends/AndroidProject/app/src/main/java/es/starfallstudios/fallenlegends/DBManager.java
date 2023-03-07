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

import es.starfallstudios.fallenlegends.Screens.MainActivity;

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

    public String retrieveUsername(String uid) {
        final String[] user = {""};
        DatabaseReference myRef = database.getReference("users/" + uid + "/username");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user[0] = dataSnapshot.getValue(String.class);
                GameManager.getInstance().setUsername(user[0]);
                Log.w("FIREBASE", "Username updated to " + user[0]);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.d("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        return user[0];
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

    public ArrayList<Creature> retrieveCreatures() {
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        DatabaseReference myRef = database.getReference("creatures");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot creatureSnapshot : dataSnapshot.getChildren()) {
                    int id = creatureSnapshot.child("id").getValue(Integer.class);
                    String name = creatureSnapshot.child("name").getValue(String.class);
                    int zone = creatureSnapshot.child("zone").getValue(Integer.class);
                    double latitude = creatureSnapshot.child("latitude").getValue(Double.class);
                    double longitude = creatureSnapshot.child("longitude").getValue(Double.class);

                    creatures.add(new Creature(name, id, zone, latitude, longitude));
                }
                Log.d("FIREBASE", "Creatures updated!");
                MainActivity.creaturesLoaded = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });

        return creatures;
    }

    public void signUpUser(String uid, String username, String email) {
        DatabaseReference myRef = database.getReference("users");
        myRef.child(uid).child("username").setValue(username);
        myRef.child(uid).child("email").setValue(email);
        myRef.child(uid).child("xp").setValue(0);
    }
}
