package es.starfallstudios.fallenlegends.models;

public class MapEntity {

    public static enum Type {
        PLAYER, CREATURE, ITEM
    }

    private Type type;
    private double latitude;
    private double longitude;

}
