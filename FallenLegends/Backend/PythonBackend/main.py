import pyrebase
import threading
import json
import random

config = {
  "apiKey": " AIzaSyCM5GKBfTwFWUmDvP0yQFnexz3b-q2Ngss ",
  "authDomain": "fallen-legends-30515.firebaseapp.com",
  "databaseURL": "https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/",
  "storageBucket": "fallen-legends-30515.appspot.com"
}

zones = {}
version = "0.0.1"

def downloadZones():
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()
    res = db.child("zones").get()
    print("Downloaded zones! Saving to zones.json")

    with open('zones.json', 'w') as outfile:
        json.dump(res.val(), outfile)

def loadZones():
    with open('zones.json') as json_file:
        data = json.load(json_file)
        return data

def setVersion():
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()
    db.child("version").set(version)
    print("Version set to " + version)

def checkForUpdate():
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()
    _version = db.child("version").get()

    if _version.val() == version:
        print("No update needed")
        return False
    else:
        print("Update needed")
        return True

# function to create random position inside 4 coordinates
def createRandomPosition():
    latitude, longitude = 0, 0

    minLat = zones[0]["points"][0][0]
    minLong = zones[0]["points"][0][1]
    maxLat = zones[-1]["points"][3][0]
    maxLong = zones[-1]["points"][3][1]

    latitude = random.uniform(minLat, maxLat)
    longitude = random.uniform(minLong, maxLong)

    return latitude, longitude

def createRandomCreature():
    pass

def isInsideZone(zone, latitude, longitude):
    # check if point is inside zone
    if (latitude > zone["points"][0][0] and latitude < zone["points"][3][0]) and (longitude > zone["points"][0][1] and longitude < zone["points"][3][1]):
        return True
    else:
        return False
    
def getZone(latitude, longitude):
    for zone in zones:
        if isInsideZone(zone, latitude, longitude):
            return zone
    return None

if __name__ == '__main__':
    print("Hey")
    checkForUpdate()
    zones = loadZones()
    lat, long = createRandomPosition()
    print(lat, long)
    print(getZone(lat, long))