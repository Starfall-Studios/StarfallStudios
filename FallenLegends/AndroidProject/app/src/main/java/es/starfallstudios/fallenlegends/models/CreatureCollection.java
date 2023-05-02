package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreatureCollection {

    private PlayerRepo playerRepo;

    private ArrayList<Creature> creatures;

    public static CreatureCollection generateDefaultCreatureCollection(String uid) {
        //Add default creatures to the collection
        CreatureCollection collection = new CreatureCollection(uid);
        collection.add(new Creature(Creature.BaseCreatures.FROSTBITE, 999, 0, 45, 49, 49, 45, Creature.CreatureType.ELECTRIC, true));
        collection.add(new Creature(Creature.BaseCreatures.GRYPHIX, 999, 0, 45, 49, 49, 45, Creature.CreatureType.ELECTRIC, true));
        collection.add(new Creature(Creature.BaseCreatures.LUMINO, 999, 0, 45, 49, 49, 45, Creature.CreatureType.ELECTRIC, true));
        collection.add(new Creature(Creature.BaseCreatures.NIGHTMIRE, 999, 0, 45, 49, 49, 45, Creature.CreatureType.ELECTRIC, true));
        collection.add(new Creature(Creature.BaseCreatures.FROSTBITE, 999, 0, 45, 49, 49, 45, Creature.CreatureType.ELECTRIC, false));

        return collection;
    }

    public CreatureCollection(String playerUid) {
        creatures = new ArrayList<>();

        playerRepo = PlayerRepo.getInstance();

        ValueEventListener eventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                creatures.clear();
                for(DataSnapshot creature : dataSnapshot.getChildren()) {
                    int base = creature.child("base").getValue(Integer.class);
                    int exp = creature.child("experience").getValue(Integer.class);
                    int hp = creature.child("hp").getValue(Integer.class);
                    int attack = creature.child("attack").getValue(Integer.class);
                    int defense = creature.child("defense").getValue(Integer.class);
                    int stamina = creature.child("stamina").getValue(Integer.class);
                    boolean inDeck = creature.child("inDeck").getValue(Boolean.class);

                    Creature c = new Creature(Creature.BaseCreatures.values()[base], 999, exp, hp, attack, defense, stamina, Creature.CreatureType.ELECTRIC, inDeck);
                    creatures.add(c);

                    Log.w("Match", "CREATURE ADDED TO COLLECTION: " + c.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        playerRepo.requestDeck(playerUid, eventListener);
    }

    public Deck getDeck() {
        Deck deck = new Deck();
        for (Creature c : creatures) {
            if (c.isInDeck()) {
                deck.addCreature(c);
            }
        }
        return deck;
    }

    public void add(Creature c) {
        creatures.add(c);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

}
