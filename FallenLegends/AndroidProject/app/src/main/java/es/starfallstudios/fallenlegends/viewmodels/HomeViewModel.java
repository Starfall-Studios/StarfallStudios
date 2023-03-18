package es.starfallstudios.fallenlegends.ViewModels;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.osmdroid.views.MapView;

import es.starfallstudios.fallenlegends.Models.Map;

public class HomeViewModel extends BaseObservable {

    private Map map;
    private Activity activity;

    @Bindable
    private String toastMessage = null;

    public HomeViewModel(Activity activity, MapView mapView) {
        map = Map.getInstance(mapView, activity);
        this.activity = activity;
    }

    public void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
