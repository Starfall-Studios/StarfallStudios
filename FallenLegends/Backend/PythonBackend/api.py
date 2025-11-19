"""api.py

Simple Flask API exposing zones and creatures endpoints for the FallenLegends backend.

Routes:
- GET /zones: Returns all zone definitions as JSON.
- GET /creatures: Returns current creatures as JSON.

This module instantiates ZoneManager and CreatureManager and exposes a startAPI helper.
"""
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
    """HTTP GET handler returning the loaded zones as JSON response."""
    return jsonify(zm.getZones())

@app.route('/creatures')
def getCreatures():
    """HTTP GET handler returning the current creatures as JSON response."""
    return jsonify(cm.getCreatures())


def startAPI():
    """Start the Flask development server (use for local testing only)."""
    app.run()