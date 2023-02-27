import random
from zones import ZoneManager
from math import sin, cos, sqrt, atan2, radians

# CREATES RANDOM POSITION INSIDE ZONES
def createRandomPosition():
    latitude, longitude = 0, 0

    zoneManager = ZoneManager()

    _zones = zoneManager.getZones()

    minLat = _zones[0]["points"][0][0]
    minLong = _zones[0]["points"][0][1]
    maxLat = _zones[-1]["points"][3][0]
    maxLong = _zones[-1]["points"][3][1]

    latitude = random.uniform(minLat, maxLat)
    longitude = random.uniform(minLong, maxLong)

    return latitude, longitude

# GET DISTANTCE BETWEEN TWO POINTS IN KM
def getDistance(lat1, lon1, lat2, lon2):
    R = 6373.0

    lat1 = radians(lat1)
    lon1 = radians(lon1)
    lat2 = radians(lat2)
    lon2 = radians(lon2)

    dlon = lon2 - lon1
    dlat = lat2 - lat1

    a = sin(dlat / 2)**2 + cos(lat1) * cos(lat2) * sin(dlon / 2)**2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))

    distance = R * c

    return distance