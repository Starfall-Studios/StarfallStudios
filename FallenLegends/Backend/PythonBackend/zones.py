import json
from dbManager import DBManager

class ZoneManager:
    zones = {}
    version = "0.0.1"

    def __init__(self):
        self.loadZones()

    def getZones(self):
        return self.zones

    def loadZones(self):
        with open("zones.json") as f:
            self.zones = json.load(f)

    def saveZones(self):
        with open("zones.json", "w") as f:
            json.dump(self.zones, f)

    def setVersion(self):
        db = DBManager()
        db.setVersion(self.version)

    def checkForUpdate(self):
        db = DBManager()

        if self.version == db.getVersion():
            print("No update needed")
            return False
        else:
            print("Update needed")
            return True
        
    def downloadZones(self):
        db = DBManager()
        res = db.getZones()
        print("Downloaded zones! Saving to zones.json")

        with open('zones.json', 'w') as outfile:
            json.dump(res, outfile)

        self.loadZones()

    def isInsideZone(self, zone, latitude, longitude):
        # check if point is inside zone
        latInside = latitude > zone["points"][3][0] and latitude < zone["points"][1][0]
        longInside = longitude > zone["points"][3][1] and longitude < zone["points"][1][1]
        if (latInside and longInside):
            print("Inside zone")
            return True
        else:
            return False
        
    def getZone(self, latitude, longitude):
        for zone in self.zones:
            if self.isInsideZone(zone, latitude, longitude):
                return zone
        return None