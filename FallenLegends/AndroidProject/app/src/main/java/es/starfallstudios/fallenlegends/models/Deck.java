package es.starfallstudios.fallenlegends.models;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Creature> creatures;

    public Deck() {
        creatures = new ArrayList<>();
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }

    public void removeCreature(Creature creature) {
        creatures.remove(creature);
    }

    public void removeCreature(int index) {
        creatures.remove(index);
    }

}
