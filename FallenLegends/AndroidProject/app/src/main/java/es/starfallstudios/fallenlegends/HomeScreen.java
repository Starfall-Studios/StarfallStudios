package es.starfallstudios.fallenlegends;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;
    private MapController mapController;
    private PopupWindow profilePopupWindow;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        gameManager = GameManager.getInstance();

        getLocation();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        setContentView(R.layout.activity_home_screen);

        GeoPoint startPoint = new GeoPoint(40.416775, -3.703790);
        mapView = findViewById(R.id.map);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(9);
        mapView.setMultiTouchControls(true);
        //show streets and buildings but not labels
        mapView.setTileSource(TileSourceFactory.MAPNIK);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //request permission to user
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            System.out.println("Longitude: " + longitude + " Latitude: " + latitude);
                            TextView textView = findViewById(R.id.textView_HomeScreen);
                            textView.setText("Longitude: " + longitude + " Latitude: " + latitude);
                            moveToLocation(latitude, longitude);
                        }
                    }
                });
    }

    public void onAboutClick(View view) {
        // inflate the layout of the popup window
        Intent intent = new Intent(this, AboutScreen.class);
        startActivity(intent);
    }

    private void moveToLocation(double latitude, double longitude) {
        GeoPoint point = new GeoPoint(latitude, longitude);
        mapController.animateTo(point, (double) 21, (long) 1000);
    }

    public void onTermsClick(View view) {
        Intent intent = new Intent(this, TermsConditionsScreen.class);
        startActivity(intent);
    }

    public void onCloseProfileClick(View view) {
        profilePopupWindow.dismiss();
    }

    public void onUpdateLocationClick(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            System.out.println("Longitude: " + longitude + " Latitude: " + latitude);
                            TextView textView = findViewById(R.id.textView_HomeScreen);
                            textView.setText("Longitude: " + longitude + " Latitude: " + latitude);
                            moveToLocation(latitude, longitude);
                        }
                    }
                });

    }
    public void onProfileClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.profile_info, null);

        // create the popup window
        int width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        profilePopupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window
        profilePopupWindow.showAtLocation(view, Gravity.LEFT, 0, -100);
    }

    private void drawZone() {
        ArrayList<Zone> zones = gameManager.getZonesList();

        for (Zone zone : zones) {
            ArrayList<GeoPoint> points = zone.getPoints();
            points.add(points.get(0));
            Polygon polygon = new Polygon();
            polygon.setPoints(points);
            polygon.setFillColor(Color.argb(50, 255, 0, 0));
            polygon.setStrokeColor(Color.RED);
            polygon.setStrokeWidth(5);
            mapView.getOverlays().add(polygon);
        }
    }

}
