package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

public class Creature {
    private String name;
    private int experience;
    private int health;
    private int attack;
    private int defense;

    private int id;

    // If the creature is in a zone, this is the zone id
    private int zoneId;

    // When creature spawns in the world, it will be in a specific location
    private double latitude;
    private double longitude;

    /**
     * Constructor for creatures that are in a zone
     * @param name Name of the creature
     * @param id Id of the creature
     * @param zoneId Id of the zone where the creature is
     */
    public Creature(String name, int id, int zoneId, double latitude, double longitude) {
        this.id = id;
        this.zoneId = zoneId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the location of the creature
     * @return geopoint of creature
     */
    public GeoPoint getLocation() {
        return new GeoPoint(latitude, longitude);
    }

    /**
     * Gets the name of the creature
     * @return name of the creature
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the creature
     * @return id of the creature
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the zone id of the creature
     * @return zone id of the creature
     */
    public int getZoneId() {
        return zoneId;
    }

    /**
     * Gets the experience of the creature
     * @return
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Gets the health of the creature
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the attack of the creature
     * @return
     */
    public int getAttack() {
        return attack;
    }
}
