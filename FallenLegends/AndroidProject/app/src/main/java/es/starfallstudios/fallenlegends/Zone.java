package es.starfallstudios.fallenlegends;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Zone {
    private int owner;
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
    public Zone(int owner, int id, String name, ArrayList<GeoPoint> points) {
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
    public int getOwner() {
        return owner;
    }

    /**
     * Set the zone owner id
     * @param owner player id
     */
    public void setOwner(int owner) {
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
    
    //Method to convert current instance to a byte array
    public byte[] toByteArray() {
        byte[] bytes = new byte[4 + 4 + 4 + name.length() + 4 + points.size() * 8];
        int offset = 0;
        System.arraycopy(intToByteArray(owner), 0, bytes, offset, 4);
        offset += 4;
        System.arraycopy(intToByteArray(id), 0, bytes, offset, 4);
        offset += 4;
        System.arraycopy(intToByteArray(name.length()), 0, bytes, offset, 4);
        offset += 4;
        System.arraycopy(name.getBytes(), 0, bytes, offset, name.length());
        offset += name.length();
        System.arraycopy(intToByteArray(points.size()), 0, bytes, offset, 4);
        offset += 4;
        for (GeoPoint point : points) {
            System.arraycopy(doubleToByteArray(point.getLatitude()), 0, bytes, offset, 8);
            offset += 8;
            System.arraycopy(doubleToByteArray(point.getLongitude()), 0, bytes, offset, 8);
            offset += 8;
        }
        return bytes;
    }

    public static Zone fromBytes(byte[] byteArray) {
        int offset = 0;
        int owner = byteArrayToInt(byteArray, offset);
        offset += 4;
        int id = byteArrayToInt(byteArray, offset);
        offset += 4;
        int nameLength = byteArrayToInt(byteArray, offset);
        offset += 4;
        String name = new String(byteArray, offset, nameLength);
        offset += nameLength;
        int pointsSize = byteArrayToInt(byteArray, offset);
        offset += 4;
        ArrayList<GeoPoint> points = new ArrayList<>();
        for (int i = 0; i < pointsSize; i++) {
            double latitude = byteArrayToDouble(byteArray, offset);
            offset += 8;
            double longitude = byteArrayToDouble(byteArray, offset);
            offset += 8;
            points.add(new GeoPoint(latitude, longitude));
        }
        return new Zone(owner, id, name, points);
    }

    private static int byteArrayToInt(byte[] byteArray, int offset) {
        return byteArray[offset] & 0xFF |
                (byteArray[offset + 1] & 0xFF) << 8 |
                (byteArray[offset + 2] & 0xFF) << 16 |
                (byteArray[offset + 3] & 0xFF) << 24;
    }

    private static double byteArrayToDouble(byte[] byteArray, int offset) {
        long longg = byteArray[offset] & 0xFF |
                (byteArray[offset + 1] & 0xFF) << 8 |
                (byteArray[offset + 2] & 0xFF) << 16 |
                (byteArray[offset + 3] & 0xFF) << 24 |
                (byteArray[offset + 4] & 0xFF) << 32 |
                (byteArray[offset + 5] & 0xFF) << 40 |
                (byteArray[offset + 6] & 0xFF) << 48 |
                (byteArray[offset + 7] & 0xFF) << 56;
        return Double.longBitsToDouble(longg);
    }

    private byte[] intToByteArray(int intt) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (intt & 0xFF);
        bytes[1] = (byte) ((intt >> 8) & 0xFF);
        bytes[2] = (byte) ((intt >> 16) & 0xFF);
        bytes[3] = (byte) ((intt >> 24) & 0xFF);
        return bytes;
    }

    private byte[] doubleToByteArray(double doublee) {
        long longg = Double.doubleToLongBits(doublee);
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (longg & 0xFF);
        bytes[1] = (byte) ((longg >> 8) & 0xFF);
        bytes[2] = (byte) ((longg >> 16) & 0xFF);
        bytes[3] = (byte) ((longg >> 24) & 0xFF);
        bytes[4] = (byte) ((longg >> 32) & 0xFF);
        bytes[5] = (byte) ((longg >> 40) & 0xFF);
        bytes[6] = (byte) ((longg >> 48) & 0xFF);
        bytes[7] = (byte) ((longg >> 56) & 0xFF);
        return bytes;
    }
}
