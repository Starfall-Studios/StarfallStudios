import dbManager
import json
from zones import ZoneManager
import utils

class CreatureManager:

    def __init__(self):
        self.creatures = {}
        self.zm = ZoneManager()
        self.loadCreatures()

    def getCreatures(self):
        return self.creatures
    
    def setCreatures(self, creatures):
        self.creatures = creatures
    
    def loadCreatures(self):
        with open("creatures.json") as f:
            self.creatures = json.load(f)

    def saveCreatures(self):
        with open("creatures.json", "w") as f:
            json.dump(self.creatures, f, indent=4)

    def downloadCreatures(self):
        db = dbManager.DBManager()
        res = db.getCreatures()
        print("Downloaded creatures! Saving to creatures.json")

        with open('creatures.json', 'w') as outfile:
            json.dump(res, outfile)

        self.loadCreatures()

    def createRandomCreature(self):
        latitude, longitude = utils.createRandomPosition()
        zone = self.zm.getZone(latitude, longitude)
        creature = {
            "name": "Test Creature 2",
            "zone": zone["id"],
            "latitude": latitude,
            "longitude": longitude
        }
        print("Created creature at " + str(latitude) + ", " + str(longitude) + " in zone " + str(zone["id"]))
        return creature