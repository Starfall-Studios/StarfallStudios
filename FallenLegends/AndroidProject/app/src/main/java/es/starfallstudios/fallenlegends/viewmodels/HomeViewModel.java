package es.starfallstudios.fallenlegends.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import es.starfallstudios.fallenlegends.R;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.DBManager;
import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Map;
import es.starfallstudios.fallenlegends.models.MapEntity;
import es.starfallstudios.fallenlegends.views.MainActivity;

public class HomeViewModel extends ViewModel {

    private Map map = null;

    public HomeViewModel() {
    }

    public void setupMap(Activity activity) {
        map = new Map(activity.findViewById(R.id.map));
        checkForPermissions(activity);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MapEntity> mapEntities = new ArrayList<>();

                for (DataSnapshot creatureSnapshot : dataSnapshot.getChildren()) {
                    double latitude = creatureSnapshot.child("latitude").getValue(Double.class);
                    double longitude = creatureSnapshot.child("longitude").getValue(Double.class);
                    int creatureType = creatureSnapshot.child("type").getValue(Integer.class);
                    int creatureTier = creatureSnapshot.child("tier").getValue(Integer.class);

                    mapEntities.add(new MapEntity(GameManager.getInstance().creatureNames.get(Creature.BaseCreatures.values()[creatureType]), MapEntity.Type.CREATURE, latitude, longitude));
                }
                map.drawCreatures(mapEntities);
                GameManager.getInstance().setMapEntities(mapEntities);
                Log.d("FIREBASE", "Creatures updated!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
        };

        DBManager.getInstance().retrieveMapEntities(listener);
    }

    public void spawnCreature() {
        DBManager.getInstance().spawnRandomCreatureNearby();
    }

    public void updateLocation() {
        map.getLocation();
    }

    private void checkForPermissions(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
