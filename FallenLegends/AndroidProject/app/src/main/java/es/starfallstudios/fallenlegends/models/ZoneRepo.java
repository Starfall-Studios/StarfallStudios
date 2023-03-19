package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ZoneRepo {

    private final String TAG = getClass().getSimpleName();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");

    public MutableLiveData<Zone> requestZone(int id) {
        MutableLiveData<Zone> zone = new MutableLiveData<>();
        DatabaseReference myRef = database.getReference("zones/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int _id = dataSnapshot.child("id").getValue(Integer.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String owner = dataSnapshot.child("owner").getValue(String.class);

                    ArrayList<GeoPoint> points = new ArrayList<GeoPoint>();

                    for (DataSnapshot pointSnapshot : dataSnapshot.child("points").getChildren()) {
                        double latitude = pointSnapshot.child("0").getValue(Double.class);
                        double longitude = pointSnapshot.child("1").getValue(Double.class);
                        points.add(new GeoPoint(latitude, longitude));
                    }
                    zone.setValue(new Zone(owner, _id, name, points));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        });
        return zone;
    }

}
