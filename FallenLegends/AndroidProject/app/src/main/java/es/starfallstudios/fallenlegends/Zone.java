package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Zone {
    private int owner;
    private int id;
    private String name;

    private ArrayList<GeoPoint> points;

    public Zone(int owner, int id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        points = new ArrayList<>();

        //create default points
        points.add(new GeoPoint(41.57660025593672, 1.6017485255249397));
        points.add(new GeoPoint(41.57321351450865, 1.6013365167652305));
        points.add(new GeoPoint(41.57445008068013, 1.6055154627566814));
        points.add(new GeoPoint(41.577139622357116, 1.6053977459682045));

    }
    public Zone(int owner, int id, String name, ArrayList<GeoPoint> points) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public ArrayList<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<GeoPoint> points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

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
}
