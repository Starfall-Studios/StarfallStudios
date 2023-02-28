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

    public Creature(String name, int id, int zoneId, double latitude, double longitude) {
        this.id = id;
        this.zoneId = zoneId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoPoint getLocation() {
        return new GeoPoint(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getExperience() {
        return experience;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }
}
