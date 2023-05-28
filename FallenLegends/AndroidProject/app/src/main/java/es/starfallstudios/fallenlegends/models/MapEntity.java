package es.starfallstudios.fallenlegends.models;

import org.osmdroid.util.GeoPoint;

public class MapEntity {

    public static enum Type {
        PLAYER, CREATURE, ITEM, OTHER
    }

    private String name;
    private Type type;
    private double latitude;
    private double longitude;
    private Creature.BaseCreatures baseCreature;

    public Type getEntityType() {
        if (this instanceof Player) {
            return Type.PLAYER;
        } else if (this instanceof Creature) {
            return Type.CREATURE;
        } else {
            return Type.OTHER;
        }
    }

    public MapEntity(String name, Type type, double latitude, double longitude) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MapEntity(String name, Type type, double latitude, double longitude, int tier) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MapEntity(Creature.BaseCreatures baseCreature, Type type, double latitude, double longitude, int tier) {
        this.name = GameManager.getInstance().creatureNames.get(baseCreature);
        this.baseCreature = baseCreature;
        this.type = type;
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

    public Creature.BaseCreatures getBaseCreature() {
        return baseCreature;
    }

}
