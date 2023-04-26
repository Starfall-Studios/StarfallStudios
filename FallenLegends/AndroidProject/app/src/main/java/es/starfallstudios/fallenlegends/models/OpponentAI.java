package es.starfallstudios.fallenlegends.models;

import android.os.Debug;
import android.util.Log;

import java.util.Random;

public class OpponentAI {

    private Match match;
    private Deck deck;

    private Creature enemyCreature;

    public OpponentAI(Match match, Deck deck) {
        this.match = match;
        this.deck = deck;
    }

    public void playCard(Creature c) {
        if (c == null) {
            Log.d("OpponentAI", "No card to play");
            return;
        }
        if (match.getOpponentMana() >= c.getCost()) {
            match.setOpponentMana(match.getOpponentMana() - c.getCost());
        } else {
            Log.d("OpponentAI", "Not enough mana");
            return;
        }
        match.setCreatureOpponent(c);
    }

    public void updateMatchState() {
        enemyCreature = match.getCreaturePlayer();

        if (creatureInPlay()) return;

        switch (enemyCreature.getType()) {
            case NONE:
                Log.d("OpponentAI", "No enemy creature");
                playCard(deck.getRandomCreatureAlive());
                break;
            case ELECTRIC:
                playCard(deck.getRandomCreatureAlive());
                break;
            case FIRE:
                break;
            case WATER:
                break;
            case FAIRY:
                break;
            case ROCK:
                break;
            default:
                break;
        }
    }

    private boolean creatureInPlay() {
        return !match.getCreatureOpponent().isBlank();
    }

}
