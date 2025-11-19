"""zones.py

Zone management utilities for FallenLegends backend.

Provides a ZoneManager that loads/saves zone definitions from a local JSON file,
synchronizes zone data with a remote database via DBManager, and performs simple
point-in-rect checks to determine whether coordinates fall inside defined zones.

This module is intentionally lightweight and uses rectangular zone definitions
stored as lists of corner points.
"""
import json
from dbManager import DBManager

class ZoneManager:
    """Manage in-memory and persisted zone definitions.

    Attributes:
        zones (dict|list): In-memory representation of zones loaded from zones.json.
        version (str): Semantic version string for the zone dataset.

    The ZoneManager supports:
    - Loading and saving zones to a local JSON file.
    - Downloading zones from a configured remote database and reloading them.
    - Checking whether a latitude/longitude point lies inside a rectangular zone.
    - Synchronizing the stored version number with the remote DB.
    """
    zones = {}
    version = "0.0.1"

    def __init__(self):
        """Initialize a ZoneManager and load zones from local storage.

        The constructor will call loadZones so the instance is ready to answer
        spatial queries immediately after construction.
        """
        self.loadZones()

    def getZones(self):
        """Return the currently loaded zones.

        Returns:
            dict|list: The loaded zones structure as parsed from zones.json.
        """
        return self.zones

    def loadZones(self):
        """Load zones from the local 'zones.json' file.

        This replaces the in-memory zones state with the data from the file.
        Raises FileNotFoundError or json.JSONDecodeError if the file is missing or invalid.
        """
        with open("zones.json") as f:
            self.zones = json.load(f)

    def saveZones(self):
        """Persist the current in-memory zones to 'zones.json'.

        The JSON is written with an indentation of 4 for readability.
        """
        with open("zones.json", "w") as f:
            json.dump(self.zones, f, indent=4)

    def setVersion(self):
        """Push the manager's current version string to the remote database.

        Uses DBManager.setVersion to synchronize the dataset version.
        """
        db = DBManager()
        db.setVersion(self.version)

    def checkForUpdate(self):
        """Compare local version against the remote DB version.

        Returns:
            bool: True if remote version differs from local (an update is needed),
                  False if versions match (no update required).
        """
        db = DBManager()

        if self.version == db.getVersion():
            print("No update needed")
            return False
        else:
            print("Update needed")
            return True
        
    def downloadZones(self):
        """Download zones from the remote DB and overwrite local 'zones.json'.

        After saving the downloaded zones locally, the in-memory representation
        is reloaded so subsequent queries use the updated data.
        """
        db = DBManager()
        res = db.getZones()
        print("Downloaded zones! Saving to zones.json")

        with open('zones.json', 'w') as outfile:
            json.dump(res, outfile, indent=4)

        self.loadZones()

    def isInsideZone(self, zone, latitude, longitude):
        """Check whether a (latitude, longitude) point lies inside a rectangular zone.

        The zone is expected to define corner points in a structure accessible via
        zone["points"] where indices correspond to corners. This function performs
        simple axis-aligned bounding-box checks.

        Args:
            zone (dict): Zone definition containing a "points" list.
            latitude (float): Latitude of the point to test.
            longitude (float): Longitude of the point to test.

        Returns:
            bool: True if the point is inside the zone rectangle, False otherwise.
        """
        latInside = latitude > zone["points"][3][0] and latitude < zone["points"][1][0]
        longInside = longitude > zone["points"][3][1] and longitude < zone["points"][1][1]
        if (latInside and longInside):
            return True
        else:
            return False
        
    def getZone(self, latitude, longitude):
        """Find and return the zone that contains the given coordinate.

        Iterates over loaded zones and returns the first zone that contains
        the provided latitude and longitude.

        Args:
            latitude (float): Latitude to search for.
            longitude (float): Longitude to search for.

        Returns:
            dict|None: The matching zone dictionary if a zone contains the point,
                       otherwise None.
        """
        for zone in self.zones:
            if self.isInsideZone(zone, latitude, longitude):
                return zone
        return None