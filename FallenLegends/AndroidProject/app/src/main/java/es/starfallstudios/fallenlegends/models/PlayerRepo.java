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
                player.setValue(new Player(uid, username, xp, 0, 0, 0, 0));
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
}
