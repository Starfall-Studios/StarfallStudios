package es.starfallstudios.fallenlegends.models;

public class OpponentAI {

    private Player player;
    private Match match;
    private Deck deck;

    private Creature enemyCreature;

    public OpponentAI(Match match) {
        this.match = match;
    }

    public void playCard() {
        match.setCreatureOpponent(deck.getCreatures().get(0));
    }

    public void updateMatchState() {
        enemyCreature = match.getCreaturePlayer();

        switch (enemyCreature.getType()) {
            case ELECTRIC:
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

}
