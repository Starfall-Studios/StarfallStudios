import dbManager
import json
from zones import ZoneManager
import utils
import random

class CreatureManager:

    def __init__(self):
        self.creatures = {}
        self.zm = ZoneManager()
        self.loadCreatures()

    def getCreatureCount(self):
        return len(self.creatures)

    def getCreatures(self):
        return self.creatures
    
    def setCreatures(self, creatures):
        self.creatures = creatures
    
    def loadCreatures(self):
        with open("baseCreatures.json") as f:
            self.baseCreatures = json.load(f)
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

    def uploadCreatures(self):
        db = dbManager.DBManager()
        db.setCreatures(self.creatures)
        print("Uploaded creatures!")

    def createRandomCreature(self):
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