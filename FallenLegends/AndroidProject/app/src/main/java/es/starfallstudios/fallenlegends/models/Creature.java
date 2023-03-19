package es.starfallstudios.fallenlegends.models;

import org.osmdroid.util.GeoPoint;

public class Creature {
    public static enum CreatureType {
        FIRE,
        ROCK,
        ELECTRIC,
        WATER,
        FAIRY
    }

    private String name;
    private int experience;
    private int health;
    private int attack;
    private int defense;
    private int stamina;
    private CreatureType type;

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

    public Creature(String name, int id, int experience, int health, int attack, int defense, int stamina, CreatureType type) {
        this.name = name;
        this.id = id;
        this.experience = experience;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.stamina = stamina;
        this.type = type;
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

    public void setHealth(int health) {
        this.health = health;
    }

    public String toString() {
        return "Name: " + name + " | Health: " + health + " | Attack: " + attack + " | Defense: " + defense + " | Stamina: " + stamina + " | Type: " + type;
    }
}
