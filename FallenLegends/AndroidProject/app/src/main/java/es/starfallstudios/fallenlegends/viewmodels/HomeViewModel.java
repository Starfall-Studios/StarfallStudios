package es.starfallstudios.fallenlegends.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.core.app.ActivityCompat;

import es.starfallstudios.fallenlegends.R;

import androidx.lifecycle.ViewModel;

import es.starfallstudios.fallenlegends.models.Map;

public class HomeViewModel extends ViewModel {

    private Map map = null;

    public HomeViewModel() {
    }

    public void setupMap(Activity activity) {
        map = new Map(activity.findViewById(R.id.map));
        checkForPermissions(activity);
    }


    public void updateLocation(View view) {
        map.getLocation();
    }

    private void checkForPermissions(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
