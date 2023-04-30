# import threading
from zones import ZoneManager
from creatures import CreatureManager
# import api
import utils
import json

useApi = False

def main():
    print("Fallen Legends Backend v0.1 starting...")
    zoneManager = ZoneManager()
    zoneManager.checkForUpdate()
    zoneManager.loadZones()

    creatureManager = CreatureManager()

    creatureManager.createRandomCreature()
    creatureManager.createRandomCreature()
    
    creatureManager.uploadCreatures()

    creatureManager.saveCreatures()
    
if __name__ == '__main__':
    main()