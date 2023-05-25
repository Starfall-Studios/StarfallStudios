import firebase_admin
from firebase_admin import credentials, messaging


useApi = False

def main():
    firebase_cred = credentials.Certificate('firebase.json')
    firebase_app = firebase_admin.initialize_app(firebase_cred)

    send_topic_push("Test MSG", "Test Body")

def send_topic_push(title, body): 
        topic = "creature_spawn"
        message = messaging.Message(notification=messaging.Notification(title=title, body=body), topic=topic)
        messaging.send(message)
    
if __name__ == '__main__':
    main()