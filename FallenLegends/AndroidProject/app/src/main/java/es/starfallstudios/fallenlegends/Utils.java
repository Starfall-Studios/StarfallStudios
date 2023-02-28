package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

public class Utils {

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static double distance(GeoPoint p1, GeoPoint p2) {
        return distance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
    }

    public static boolean isCloseEnough(GeoPoint p1, GeoPoint p2, double interactionDistance) {
        return distance(p1, p2) <= interactionDistance;
    }

    public static boolean isCloseEnough(double lat1, double lon1, double lat2, double lon2, double interactionDistance) {
        return distance(lat1, lon1, lat2, lon2) <= interactionDistance;
    }

}
