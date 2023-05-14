package es.starfallstudios.fallenlegends.models;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.util.Log;

import androidx.core.location.LocationRequestCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
import es.starfallstudios.fallenlegends.CreatureInfoWindow;

public class Map {

    private final MapView mapView;
    private MapController mapController;

    private final Marker userMarker;

    private final GameManager gameManager;
    private final Activity activity;

    private ArrayList<Marker> mapEntitiesMarkers;

    public Map(MapView mapView) {
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        this.mapView = mapView;
        this.activity = (Activity) mapView.getContext();
        gameManager = GameManager.getInstance();
        mapController = (MapController) mapView.getController();

        GeoPoint startPoint = new GeoPoint(41.58025556428497, 1.6077941269397034);
        mapController = (MapController) this.mapView.getController();
        mapController.setCenter(startPoint);
        mapController.setZoom(20);
        this.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        this.mapView.setMultiTouchControls(true);
        this.mapView.setMaxZoomLevel(21.0);
        this.mapView.setMinZoomLevel(18.0);
        userMarker = new Marker(this.mapView);

        mapEntitiesMarkers = new ArrayList<>();

        //show streets and buildings but not labels
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);

        drawZone();

        getLocation();
    }

    private void moveToLocation(double latitude, double longitude) {
        GeoPoint point = new GeoPoint(latitude, longitude);
        gameManager.setUserLocation(point);
        userMarker.setPosition(point);
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(userMarker);
        mapController.animateTo(point, (double) 21, (long) 1000);
    }

    public void getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(mapView.getContext());

        try {
            fusedLocationClient.getCurrentLocation(LocationRequestCompat.QUALITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                moveToLocation(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.w("Error", "GPS PERMISSIONS MISSING");
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

    public void drawCreatures(ArrayList<MapEntity> entities) {
        //Clear previous markers
        for (Marker marker : mapEntitiesMarkers) {
            mapView.getOverlayManager().remove(marker);
        }
        mapEntitiesMarkers.clear();

        for (MapEntity entity : entities) {
            Marker marker = new Marker(mapView);
            marker.setPosition(entity.getLocation());
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            //marker.setIcon(getResources().getDrawable(R.drawable.ic_creature));
            marker.setTitle(entity.getName());
            marker.setInfoWindow(new CreatureInfoWindow(mapView, entity));
            mapView.getOverlayManager().add(marker);
        }
    }

}
