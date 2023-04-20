package es.starfallstudios.fallenlegends.models;

import android.content.SharedPreferences;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.firebase.database.FirebaseDatabase;
import org.osmdroid.util.GeoPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import es.starfallstudios.fallenlegends.R;

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

    public HashMap<Creature.BaseCreatures, Integer> creatureResources;
    public HashMap<Creature.BaseCreatures, String> creatureNames;

    //Repos
    private final PlayerRepo playerRepo = new PlayerRepo();

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

        creatureResources = new HashMap<>();
        creatureNames = new HashMap<>();

        //Load creature resources
        creatureResources.put(Creature.BaseCreatures.FROSTBITE, R.drawable.creature_frostbite);
        creatureNames.put(Creature.BaseCreatures.FROSTBITE, "Frostbite");

        creatureResources.put(Creature.BaseCreatures.GRYPHIX, R.drawable.creature_gryphix);
        creatureNames.put(Creature.BaseCreatures.GRYPHIX, "Gryphix");

        creatureResources.put(Creature.BaseCreatures.LUMINO, R.drawable.creature_lumino);
        creatureNames.put(Creature.BaseCreatures.LUMINO, "Lumino");

        creatureResources.put(Creature.BaseCreatures.NIGHTMIRE, R.drawable.creature_nightmire);
        creatureNames.put(Creature.BaseCreatures.NIGHTMIRE, "Nightmire");

        creatureResources.put(Creature.BaseCreatures.THUNDERWING, R.drawable.creature_thunderwing);
        creatureNames.put(Creature.BaseCreatures.THUNDERWING, "Thunderwing");
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}