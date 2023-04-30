package es.starfallstudios.fallenlegends.models;

public class ZoneInfo {

    private String ownerId;
    private String zoneName;
    private int zoneId;
    private String ownerName;

    public ZoneInfo(String ownerId, String zoneName, String ownerName, int zoneId) {
        this.ownerId = ownerId;
        this.zoneName = zoneName;
        this.ownerName = ownerName;
        this.zoneId = zoneId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getZoneId() {
        return zoneId;
    }

}
