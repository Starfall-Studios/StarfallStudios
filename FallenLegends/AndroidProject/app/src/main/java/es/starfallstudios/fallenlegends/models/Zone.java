package es.starfallstudios.fallenlegends.Models;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Zone {
    private String owner;
    private int id;
    private String name;

    private ArrayList<GeoPoint> points;

    /**
     * Create a new zone
     * @param owner player id
     * @param id zone id
     * @param name zone name
     * @param points ArrayList of GeoPoints clockwise starting from the top left corner
     */
    public Zone(String owner, int id, String name, ArrayList<GeoPoint> points) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.points = points;
    }

    /**
     * Get the zone owner id
     *
     * @return player id
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the zone owner id
     * @param owner player id
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get the zone name
     * @return zone name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the zone's delimiter points
     * @return ArrayList of GeoPoints clockwise starting from the top left corner
     */
    public ArrayList<GeoPoint> getPoints() {
        return points;
    }

    /**
     * Set the zone's delimiter points
     * @param points ArrayList of GeoPoints clockwise starting from the top left corner
     */
    public void setPoints(ArrayList<GeoPoint> points) {
        this.points = points;
    }

    /**
     * Get the zone id
     * @return zone id
     */
    public int getId() {
        return id;
    }

    /**
     * Check if a point is inside the zone
     * @param coordinates GeoPoint to check
     * @return true if the point is inside the zone, false otherwise
     */
    public boolean isCoordinatesInsideZone(GeoPoint coordinates) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).getLatitude() > coordinates.getLatitude()) != (points.get(j).getLatitude() > coordinates.getLatitude()) &&
                    (coordinates.getLongitude() < (points.get(j).getLongitude() - points.get(i).getLongitude()) * (coordinates.getLatitude() - points.get(i).getLatitude()) / (points.get(j).getLatitude() - points.get(i).getLatitude()) + points.get(i).getLongitude())) {
                result = !result;
            }
        }
        return result;
    }

    public boolean hasOwner() {
        return !owner.equals("0");
    }
}
