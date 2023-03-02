package es.starfallstudios.fallenlegends.Screens;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.BuildConfig;
import es.starfallstudios.fallenlegends.Creature;
import es.starfallstudios.fallenlegends.CreatureInfoWindow;
import es.starfallstudios.fallenlegends.GameManager;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.Zone;

public class HomeScreen extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private MapView mapView;
    private MapController mapController;
    private PopupWindow profilePopupWindow;
    private GameManager gameManager;
    private Marker userMarker;
    private FirebaseAuth mAuth;

    private LocationCallback locationCallback;
    private com.google.android.gms.location.LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        gameManager = GameManager.getInstance();
        mAuth = FirebaseAuth.getInstance();

        getLocation();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        setContentView(R.layout.activity_home_screen);

        GeoPoint startPoint = new GeoPoint(41.58025556428497, 1.6077941269397034);
        mapView = findViewById(R.id.map);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(20);
        //mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        mapView.setMaxZoomLevel(21.0);
        mapView.setMinZoomLevel(14.0);
        userMarker = new Marker(mapView);

        //show streets and buildings but not labels
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        drawZone();
        drawCreatures();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    moveToLocation(latitude, longitude);
                }
            }
        };

        startLocationUpdates();
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
                            moveToLocation(latitude, longitude);
                        }
                    }
                });
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You don't have location permissions!", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void moveToLocation(double latitude, double longitude) {
        GeoPoint point = new GeoPoint(latitude, longitude);
        gameManager.setUserLocation(point);
        userMarker.setPosition(point);
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(userMarker);
        mapController.animateTo(point, (double) 21, (long) 1000);
    }

    public void onUpdateLocationClick(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
                            moveToLocation(latitude, longitude);
                        }
                    }
                });

    }
    public void onProfileClick(View view) { //Fix this method
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile, popup.getMenu());
        popup.show();
    }

    public boolean onProfileMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                startActivity(new Intent(this, AboutScreen.class));
                return true;
            case R.id.TermsConditions:
                startActivity(new Intent(this, TermsConditionsScreen.class));
                return true;
            case R.id.Logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginScreen.class));
                return true;
            default:
                return false;
        }

    }

    public boolean onNavMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_locationZone:
                Zone zone = gameManager.getZone(gameManager.getUserLocation());
                System.out.println(zone.getName() + ", player at coords: " + gameManager.getUserLocation().getLatitude() + ", " + gameManager.getUserLocation().getLongitude());
                return true;
            default:
                return false;
        }
    }

    private void drawZone() {
        ArrayList<Zone> zones = gameManager.getZonesList();

        for (Zone zone : zones) {
            ArrayList<GeoPoint> points = zone.getPoints();
            points.add(points.get(0));
            Polygon polygon = new Polygon();
            polygon.setPoints(points);
            polygon.setTitle(zone.getName());
            polygon.setFillColor(Color.argb(35, 0, 0, 255));
            polygon.setStrokeColor(Color.BLUE);
            polygon.setStrokeWidth(5);
            mapView.getOverlayManager().add(polygon);
        }
    }

    private void drawCreatures() {
        ArrayList<Creature> creatures = gameManager.getCreaturesList();

        for (Creature creature : creatures) {
            Marker marker = new Marker(mapView);
            marker.setPosition(creature.getLocation());
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            //marker.setIcon(getResources().getDrawable(R.drawable.ic_creature));
            marker.setTitle(creature.getName());
            marker.setInfoWindow(new CreatureInfoWindow(mapView, creature));
            mapView.getOverlayManager().add(marker);
        }
    }

}
