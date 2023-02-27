package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

public class Creature {
    private String name;
    private int health;
    private int attack;
    private int defense;
    private int cost;
    private int id;
    private int zoneId;
    private int ownerId;
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
}
