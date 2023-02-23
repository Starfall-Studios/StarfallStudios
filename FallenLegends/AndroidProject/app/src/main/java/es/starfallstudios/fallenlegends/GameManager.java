package es.starfallstudios.fallenlegends;

import com.google.firebase.database.FirebaseDatabase;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;

    private GeoPoint userLocation;
    private static final DBManager dbManager = DBManager.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");

    private ArrayList<Zone> zones;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager() {
        zones = new ArrayList<Zone>();
        userLocation = new GeoPoint(41.57660025593672, 1.6017485255249397);
    }

    public void setUserLocation(GeoPoint userLocation) {
        this.userLocation = userLocation;
    }

    public GeoPoint getUserLocation() {
        return userLocation;
    }

    public void getZones() {
        zones = dbManager.retrieveZones();
    }

    public ArrayList<Zone> getZonesList() {
        return zones;
    }

    public Zone getZone(GeoPoint point) {
        for (Zone zone : zones) {
            if (zone.isCoordinatesInsideZone(point)) {
                return zone;
            }
        }
        return null;
    }
}