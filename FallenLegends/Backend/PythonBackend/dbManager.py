import pyrebase
import config

class DBManager:
    def __init__(self):
        self.firebase = pyrebase.initialize_app(config.config)
        self.db = self.firebase.database()

    def setVersion(self, version):
        self.db.child("version").set(version)
        print("Version set to " + version)

    def getVersion(self):
        return self.db.child("version").get().val()
    
    def getZones(self):
        return self.db.child("zones").get().val()
    
    def getCreatures(self):
        return self.db.child("creatures").get().val()