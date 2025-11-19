"""main.py

Entry point for lightweight Firebase integrations used by the backend.

Initializes the Firebase Admin SDK using a service account JSON file and
registers a realtime database listener that will publish a simple push
notification whenever a new creature event is observed.
"""

try:
    import firebase_admin
    from firebase_admin import credentials, messaging, db
except Exception:
    # Allow importing the module when firebase_admin isn't installed (docs).
    firebase_admin = None
    credentials = None
    messaging = None
    db = None


useApi = False

def main():
    """Initialize Firebase Admin and start listening for creature events.

    Expects a local 'firebase.json' service account file and a DB URL hard-coded
    in the call. The function registers a listener on the 'creatures' node.
    """
    firebase_cred = credentials.Certificate('firebase.json')
    firebase_app = firebase_admin.initialize_app(firebase_cred, {
        'databaseURL': 'https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/'
    })

    #send_topic_push("Test MSG", "Test Body")    

    ref = db.reference('creatures').listen(listener)

def send_topic_push(title, body): 
    """Send a Firebase Cloud Messaging notification to the 'creature_spawn' topic.

    Args:
        title (str): Notification title.
        body (str): Notification body.
    """
    topic = "creature_spawn"
    message = messaging.Message(notification=messaging.Notification(title=title, body=body), topic=topic)
    messaging.send(message)

def listener(event):
    """Realtime DB listener callback invoked on changes to the 'creatures' node.

    The callback sends a simple 'Creature Spawned' push notification when fired.
    Args:
        event: The pyrebase/firebase_admin event object describing the change.
    """
    send_topic_push("Creature Spawned", "A creature has spawned in the world")
    
if __name__ == '__main__':
    main()