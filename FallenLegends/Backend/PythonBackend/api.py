from flask import Flask, jsonify
import zones
import creatures

app = Flask(__name__)
zm = zones.ZoneManager()
zm.loadZones()

cm = creatures.CreatureManager()
cm.loadCreatures()

@app.route('/zones')
def getZones():
    return jsonify(zm.getZones())

@app.route('/creatures')
def getCreatures():
    return jsonify(cm.getCreatures())


def startAPI():
    app.run()