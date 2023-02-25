import pyrebase

config = {
  "apiKey": " AIzaSyCM5GKBfTwFWUmDvP0yQFnexz3b-q2Ngss ",
  "authDomain": "fallen-legends-30515.firebaseapp.com",
  "databaseURL": "https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/",
  "storageBucket": "fallen-legends-30515.appspot.com"
}

def setup():
    firebase = pyrebase.initialize_app(config)
    db = firebase.database()
    res = db.child("zones").get()
    print(res.val())


if __name__ == '__main__':
    print("Hey")
    setup()
