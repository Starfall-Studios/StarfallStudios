import random
from zones import ZoneManager
from creatures import CreatureManager

def main():
    print("Hey")
    zoneManager = ZoneManager()
    zoneManager.checkForUpdate()
    zoneManager.loadZones()

    creatureManager = CreatureManager()
    c1 = creatureManager.createRandomCreature()

    creatures = creatureManager.getCreatures()
    creatures.append(c1)
    creatureManager.setCreatures(creatures)
    creatureManager.saveCreatures()
    
if __name__ == '__main__':
    print("Hey")
    main()