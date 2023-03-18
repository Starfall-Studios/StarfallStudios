package es.starfallstudios.fallenlegends.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.osmdroid.views.MapView;

import es.starfallstudios.fallenlegends.models.Map;

public class HomeViewModel extends BaseObservable {

    private final Map map;

    @Bindable
    private String toastMessage = null;

    public HomeViewModel(Activity activity, MapView mapView) {
        map = Map.getInstance(mapView, activity);
    }

    public void updateLocation() {
        map.getLocation();
    }
}
