import firebase_admin
from firebase_admin import credentials, messaging, db


useApi = False

def main():
    firebase_cred = credentials.Certificate('firebase.json')
    firebase_app = firebase_admin.initialize_app(firebase_cred, {
        'databaseURL': 'https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/'
    })

    #send_topic_push("Test MSG", "Test Body")    

    ref = db.reference('creatures').listen(listener)

def send_topic_push(title, body): 
        topic = "creature_spawn"
        message = messaging.Message(notification=messaging.Notification(title=title, body=body), topic=topic)
        messaging.send(message)

def listener(event):
    send_topic_push("Creature Spawned", "A creature has spawned in the world")
    
if __name__ == '__main__':
    main()