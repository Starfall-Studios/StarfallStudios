"""creatures.py

CreatureManager handles in-memory creature instances and persistence helpers.

Responsibilities:
- Load base creature templates and current creatures from JSON files.
- Save creatures locally and upload/download them from the remote DB via DBManager.
- Create randomized creatures positioned within the world using utils.ZoneManager.
"""
import dbManager
import json
from zones import ZoneManager
import utils
import random

class CreatureManager:
    """Manage creatures: loading, saving, uploading and random spawning.

    Attributes:
        creatures (list|dict): The active creature instances.
        zm (ZoneManager): Helper to query zones for spawn locations.
        baseCreatures (list): Templates used to derive new creatures.
    """
    def __init__(self):
        """Initialize CreatureManager, create ZoneManager and load creature data."""
        self.creatures = {}
        self.zm = ZoneManager()
        self.loadCreatures()

    def getCreatureCount(self):
        """Return number of loaded creatures.

        Returns:
            int: Count of creatures in the manager.
        """
        return len(self.creatures)

    def getCreatures(self):
        """Return the in-memory creature collection."""
        return self.creatures
    
    def setCreatures(self, creatures):
        """Replace the in-memory creature collection.

        Args:
            creatures (list|dict): New creatures to store in-memory.
        """
        self.creatures = creatures
    
    def loadCreatures(self):
        """Load base templates and current creatures from JSON files.

        Expects 'baseCreatures.json' and 'creatures.json' to exist locally.
        """
        with open("baseCreatures.json") as f:
            self.baseCreatures = json.load(f)
        with open("creatures.json") as f:
            self.creatures = json.load(f)

    def saveCreatures(self):
        """Persist current creatures to 'creatures.json' with readable indentation."""
        with open("creatures.json", "w") as f:
            json.dump(self.creatures, f, indent=4)

    def downloadCreatures(self):
        """Download creatures from remote DB and overwrite local 'creatures.json'.

        After downloading, reloads the in-memory collection.
        """
        db = dbManager.DBManager()
        res = db.getCreatures()
        print("Downloaded creatures! Saving to creatures.json")

        with open('creatures.json', 'w') as outfile:
            json.dump(res, outfile)

        self.loadCreatures()

    def uploadCreatures(self):
        """Upload the manager's creatures to the remote DB using DBManager.setCreatures."""
        db = dbManager.DBManager()
        db.setCreatures(self.creatures)
        print("Uploaded creatures!")

    def createRandomCreature(self):
        """Create and register a randomized creature instance.

        Picks a random position using utils.createRandomPosition, resolves the
        containing zone, selects a random base template and tier, computes scaled
        stats and appends the new creature to the in-memory collection.

        Returns:
            dict: The newly created creature dictionary.
        """
        latitude, longitude = utils.createRandomPosition()
        zone = self.zm.getZone(latitude, longitude)
        base = self.baseCreatures[random.randint(0, len(self.baseCreatures) - 1)]
        tier = random.randint(1, 5)
        multiplier = [1, 1.1, 1.25, 1.5, 2]
        creature = {
            "id": len(self.creatures) + 1,
            "name": base["name"],
            "type": base["type"],
            "tier": tier,
            "hp": base["hp"] * multiplier[tier-1],
            "attack": base["attack"] * multiplier[tier-1],
            "defense": base["defense"] * multiplier[tier-1],
            "zone": zone["id"],
            "latitude": latitude,
            "longitude": longitude,
            "owner": ""
        }
        print("Created creature at " + str(latitude) + ", " + str(longitude) + " in zone " + str(zone["id"]))
        self.creatures.append(creature)
        return creature