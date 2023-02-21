package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<GeoPoint> points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    //Serialize to JSON
    public String toJSON() {
        String json = "{";
        json += "\"owner\":" + owner + ",";
        json += "\"id\":" + id + ",";
        json += "\"name\":\"" + name + "\",";
        json += "\"points\":[";
        for (GeoPoint point : points) {
            json += "{\"latitude\":" + point.getLatitude() + ",\"longitude\":" + point.getLongitude() + "},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]}";
        return json;
    }

    //Deserialize from JSON
    public static Zone fromJSON(String json) {
        int owner = Integer.parseInt(json.substring(json.indexOf("\"owner\":") + 8, json.indexOf(",\"id\":")));
        int id = Integer.parseInt(json.substring(json.indexOf("\"id\":") + 5, json.indexOf(",\"name\":")));
        String name = json.substring(json.indexOf("\"name\":\"") + 8, json.indexOf("\",\"points\":"));
        ArrayList<GeoPoint> points = new ArrayList<>();
        String pointsString = json.substring(json.indexOf("\"points\":[") + 10, json.indexOf("]}"));
        String[] pointsArray = pointsString.split("},");
        for (String point : pointsArray) {
            double latitude = Double.parseDouble(point.substring(point.indexOf("\"latitude\":") + 11, point.indexOf(",\"longitude\":")));
            double longitude = Double.parseDouble(point.substring(point.indexOf("\"longitude\":") + 12, point.length()));
            points.add(new GeoPoint(latitude, longitude));
        }
        return new Zone(owner, id, name, points);
    }

}
