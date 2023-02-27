import random
from zones import ZoneManager
from creatures import CreatureManager

def main():
    print("Fallen Legends Backend v0.1 starting...")
    zoneManager = ZoneManager()
    zoneManager.checkForUpdate()
    zoneManager.loadZones()

    creatureManager = CreatureManager()
    creatures = creatureManager.getCreatures()
    
    creatureManager.uploadCreatures()

    creatureManager.saveCreatures()
    zoneManager.saveZones()
    
if __name__ == '__main__':
    main()