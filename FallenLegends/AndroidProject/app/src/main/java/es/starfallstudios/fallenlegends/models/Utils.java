package es.starfallstudios.fallenlegends.models;

import org.osmdroid.util.GeoPoint;

public class Utils {

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * Calculates the distance between two points in meters
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return distance in meters
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        //convert distance to meters
        dist = dist * 1609.344;

        return (dist);
    }

    /**
     * Calculates the distance between two points in meters
     * @param p1 GeoPoint 1
     * @param p2 GeoPoint 2
     * @return distance in meters
     */
    public static double distance(GeoPoint p1, GeoPoint p2) {
        return distance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
    }

    /**
     * Checks if two points are close enough to interact
     * @param p1 GeoPoint 1
     * @param p2 GeoPoint 2
     * @param interactionDistance distance in meters
     * @return true if the distance between the two points is less than the interaction distance
     */
    public static boolean isCloseEnough(GeoPoint p1, GeoPoint p2, double interactionDistance) {
        return distance(p1, p2) <= interactionDistance;
    }

    /**
     * Checks if two points are close enough to interact
     * @param lat1 latitude of point 1
     * @param lon1 longitude of point 1
     * @param lat2 latitude of point 2
     * @param lon2 longitude of point 2
     * @param interactionDistance distance in meters
     * @return true if the distance between the two points is less than the interaction distance
     */
    public static boolean isCloseEnough(double lat1, double lon1, double lat2, double lon2, double interactionDistance) {
        return distance(lat1, lon1, lat2, lon2) <= interactionDistance;
    }

}
