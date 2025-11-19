"""utils.py

Utility functions used across the FallenLegends backend.

Includes:
- Random position generation constrained by loaded zones.
- Haversine distance calculation for two lat/lon points (returns kilometers).
- Generation of a deterministic list of baseline creature templates.
"""

import random
from zones import ZoneManager
from math import sin, cos, sqrt, atan2, radians

creatureNames = ["Gryphix", "Nightmire","Lumino","Frostbite","Sunspark","Thunderwing","Stormbringer","Shadowfury","Celestia","Inferno","Moonbeam","Starlight","Mysticor","Dreamweaver","Faeleth","Phoenixfire","Krakensong","Aquarion","Darksoul","Silverglow","Skydancer","Elderscale","Woodlandia","Wildfire","Icefall","Nightshade","Shadowclaw","Thunderbolt","Magicorn","Spellweaver","Crystalwing","Sunburst","Emberheart","Moonstone","Starborn","Enchantress","Earthsprite","Faerieflame","Sunray","Mysticmeadow"]
creatureTypes = ["Water", "Fire", "Electric", "Rock", "Fairy"]

# CREATES RANDOM POSITION INSIDE ZONES
def createRandomPosition():
    """Create a random latitude/longitude pair inside the overall zone bounds.

    The function loads the set of zones, computes the bounding latitude and
    longitude from the first and last zone entries and returns a uniform random
    location in that rectangular bounding box.

    Returns:
        tuple(float, float): (latitude, longitude)

    Note:
        This is a simple approach that samples the global bounding box and does
        not guarantee the point lies inside a particular zone. Consumers expecting
        a zone-assigned coordinate should call ZoneManager.getZone afterwards.
    """
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
    """Calculate the Haversine distance between two latitude/longitude coordinates.

    Args:
        lat1 (float): Latitude of the first point in decimal degrees.
        lon1 (float): Longitude of the first point in decimal degrees.
        lat2 (float): Latitude of the second point in decimal degrees.
        lon2 (float): Longitude of the second point in decimal degrees.

    Returns:
        float: Distance in kilometers between the two points.
    """
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

def generateBaseCreatures():
    """Generate a deterministic list of base creature templates.

    Each template contains id, name, type and base stats. IDs start at 0 and
    correspond to indices in the generated list.

    Returns:
        list[dict]: List of creature template dictionaries.
    """
    creatures = []
    for i in range(0, 40):
        creatures.append({
            "id": i,
            "name": creatureNames[i],
            "type": creatureTypes[random.randint(0, len(creatureTypes) - 1)],
            "hp": random.randint(50, 200),
            "attack": random.randint(10, 100),
            "defense": random.randint(10, 100)
        })

    return creatures