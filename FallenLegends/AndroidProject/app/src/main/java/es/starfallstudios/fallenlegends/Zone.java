package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

import java.util.List;

public class Zone {
    private int owner;
    private int id;
    private String name;

    private List<GeoPoint> points;

    public Zone(int owner, int id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(List<GeoPoint> points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

}
