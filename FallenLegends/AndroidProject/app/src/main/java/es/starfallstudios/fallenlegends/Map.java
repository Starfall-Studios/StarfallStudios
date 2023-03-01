package es.starfallstudios.fallenlegends;

import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class Map {

    private Map instance;
    private MapView mapView;
    private MapController mapController;

    private Marker userMarker;

    private Map(MapView mapView) {
        this.mapView = mapView;
        mapController = (MapController) mapView.getController();
    }

    public Map getInstance(MapView mapView) {
        if (instance == null) {
            instance = new Map(mapView);
        }
        return instance;
    }

}
