package es.starfallstudios.fallenlegends;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import java.util.Observable;

public class GameManager extends Observable {

    private static GameManager instance = null;
    private SharedPreferences sharedPreferences;

    private int zoneVersion;

    private GeoPoint userLocation;
    private static final DBManager dbManager = DBManager.getInstance();
    private static final StorageManager storageManager = StorageManager.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");

    private ArrayList<Zone> zones;

    // Creatures that are in the map
    private ArrayList<Creature> mapCreatures;

    //User related variables
    private Player player;

    /**
     * Gets the game manager instance
     * @return GameManager instance
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Private constructor to avoid instantiation
     */
    private GameManager() {
        zones = new ArrayList<Zone>();
        mapCreatures = new ArrayList<Creature>();
        userLocation = new GeoPoint(41.57660025593672, 1.6017485255249397);
        //zoneVersion = storageManager.getZoneVersion();
    }

    /**
     * Gets the zone version
     * @return int of the zone version
     */
    public int getZoneVersion() {
        return zoneVersion;
    }

    /**
     * Sets the zone version
     * @param zoneVersion int of the zone version
     */
    public void setZoneVersion(int zoneVersion) {
        this.zoneVersion = zoneVersion;
    }

    /**
     * Sets the user location
     * @param userLocation GeoPoint of the user location
     */
    public void setUserLocation(GeoPoint userLocation) {
        this.userLocation = userLocation;
    }

    /**
     * Gets the user location
     * @return GeoPoint of the user location
     */
    public GeoPoint getUserLocation() {
        return userLocation;
    }

    /**
     * Gets the zones from the database
     */
    public void getZones() {
        zones = dbManager.retrieveZones();
    }

    /**
     * Returns the list of zones
     * @return List of zones
     */
    public ArrayList<Zone> getZonesList() {
        return zones;
    }

    /**
     * Gets the creatures from the database
     */
    public void getCreatures() {
        mapCreatures = dbManager.retrieveCreatures();
    }

    /**
     * Returns the list of creatures that are in the map
     * @return List of creatures that are in the map
     */
    public ArrayList<Creature> getCreaturesList() {
        return mapCreatures;
    }

    /**
     * Returns the zone where a geopoint is located
     * @param point Geopoint to check
     * @return Zone where the geopoint is located
     */
    public Zone getZone(GeoPoint point) {
        for (Zone zone : zones) {
            if (zone.isCoordinatesInsideZone(point)) {
                return zone;
            }
        }
        return null;
    }

    /**
     * Gets the shared preferences
     * @return shared preferences
     */
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * Sets the shared preferences
     * @param sharedPreferences shared preferences
     */
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //User related methods
    public void loadPlayer(String uid) {
        dbManager.retrievePlayer(uid);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        synchronized (this) {
            this.player = player;
        }
        setChanged();
        notifyObservers();
    }
}