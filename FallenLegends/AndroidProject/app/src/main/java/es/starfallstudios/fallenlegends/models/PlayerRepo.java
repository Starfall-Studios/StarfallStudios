package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayerRepo {

    private final String TAG = getClass().getSimpleName();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://fallen-legends-30515-default-rtdb.europe-west1.firebasedatabase.app/");

    public static PlayerRepo instance;

    public static PlayerRepo getInstance() {
        if (instance == null) {
            instance = new PlayerRepo();
        }
        return instance;
    }

    public MutableLiveData<Player> requestPlayer(String uid) {
        MutableLiveData<Player> player = new MutableLiveData<>();
        DatabaseReference myRef = database.getReference("users/" + uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue(String.class);
                int xp = dataSnapshot.child("xp").getValue(Integer.class);
                //int gems = dataSnapshot.child("gems").getValue(Integer.class);
                //int food = dataSnapshot.child("food").getValue(Integer.class);
                //int stone = dataSnapshot.child("stone").getValue(Integer.class);
                //int wood = dataSnapshot.child("wood").getValue(Integer.class);
                Player temp = new Player(uid, username, xp, 0, 0, 0, 0);
                Deck tempDeck = new Deck();

                for (DataSnapshot creature : dataSnapshot.child("creatures").getChildren()) {
                    String name = creature.child("name").getValue(String.class);
                    int base = creature.child("base").getValue(Integer.class);
                    int exp = creature.child("experience").getValue(Integer.class);
                    int hp = creature.child("hp").getValue(Integer.class);
                    int attack = creature.child("attack").getValue(Integer.class);
                    int defense = creature.child("defense").getValue(Integer.class);
                    int stamina = creature.child("stamina").getValue(Integer.class);
                    boolean inDeck = creature.child("inDeck").getValue(Boolean.class);

                    tempDeck.addCreature(new Creature(Creature.BaseCreatures.values()[base], 999, exp, hp, attack, defense, stamina, Creature.CreatureType.ELECTRIC, inDeck));
                }

                temp.setDeck(tempDeck);
                player.setValue(temp);

                Log.d(TAG, "Player updated!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return player;
    }

    public MutableLiveData<Deck> requestDeck(String uid, ValueEventListener listener) {
        MutableLiveData<Deck> deck = new MutableLiveData<>();
        DatabaseReference myRef = database.getReference("users/" + uid + "/creatures");
        myRef.addValueEventListener(listener);
        return deck;
    }

    public void signUpUser(String uid, String username, String email) {
        DatabaseReference myRef = database.getReference("users");
        myRef.child(uid).child("username").setValue(username);
        myRef.child(uid).child("email").setValue(email);
        myRef.child(uid).child("xp").setValue(0);
        myRef.child(uid).child("gems").setValue(0);
        myRef.child(uid).child("food").setValue(0);
        myRef.child(uid).child("stone").setValue(0);
        myRef.child(uid).child("wood").setValue(0);
    }

}
