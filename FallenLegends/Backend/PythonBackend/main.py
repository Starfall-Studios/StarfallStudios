import threading
from zones import ZoneManager
from creatures import CreatureManager
import api

useApi = False

def main():
    print("Fallen Legends Backend v0.1 starting...")
    zoneManager = ZoneManager()
    zoneManager.checkForUpdate()
    zoneManager.loadZones()

    creatureManager = CreatureManager()

    if useApi:
      thread = threading.Thread(target=api.startAPI)
      thread.start()
    
    creatureManager.uploadCreatures()

    creatureManager.saveCreatures()
    zoneManager.saveZones()
    
if __name__ == '__main__':
    main()