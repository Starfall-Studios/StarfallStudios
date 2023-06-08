package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Match {

    private final int MAX_HEALTH = 100;
    private final float PLAYER_DAMAGE_MULTIPLIER = 0.1f;

    private Zone zone;

    private Player player;
    private OpponentAI opponent;

    private int playerMana;
    private int opponentMana;

    private int playerHealth;
    private int opponentHealth;

    private Creature playingCreaturePlayer;
    private Creature playingCreatureOpponent;

    private ArrayList<Creature> playingCreaturesPlayer;

    private Thread gameTick;

    private PlayerRepo playerRepo;
    private ZoneRepo zoneRepo;

    private boolean winner;

    MutableLiveData<Creature> playingCreaturePlayerLD = new MutableLiveData<>();
    MutableLiveData<Creature> playingCreatureOpponentLD = new MutableLiveData<>();

    MutableLiveData<Integer> playerCreatureHealthLD = new MutableLiveData<>();
    MutableLiveData<Integer> opponentCreatureHealthLD = new MutableLiveData<>();

    MutableLiveData<Integer> playerHealthLD = new MutableLiveData<>();
    MutableLiveData<Integer> enemyHealthLD = new MutableLiveData<>();
    MutableLiveData<Integer> playerManaLD = new MutableLiveData<>();
    MutableLiveData<Integer> enemyManaLD = new MutableLiveData<>();


    MutableLiveData<Boolean> matchFinishedLD = new MutableLiveData<>();

    private void gameTickUpdate() {
        // Update mana
        if(playerMana < 100) playerMana += 10;
        if (opponentMana < 100) opponentMana += 10;

        playerManaLD.postValue(playerMana);

        opponent.updateMatchState();

        Log.w("Match", "GAMESTATE: PlayerHP: " + playerHealth + " OpponentHP: " + opponentHealth);
        Log.w("Match", "Player: " + playingCreaturePlayer.toString());
        Log.w("Match", "AI: " + playingCreatureOpponent.toString());

        if(!playingCreaturePlayer.isBlank()) {
            playerCreatureHealthLD.postValue(playingCreaturePlayer.getHealth());
            if (!playingCreatureOpponent.isBlank()) {
                opponentCreatureHealthLD.postValue(playingCreatureOpponent.getHealth());
                playingCreatureOpponent.setHealth(playingCreatureOpponent.getHealth() - playingCreaturePlayer.getAttack());
                playingCreaturePlayer.setHealth(playingCreaturePlayer.getHealth() - playingCreatureOpponent.getAttack());
                if (playingCreatureOpponent.getHealth() <= 0) {
                    killCreature(playingCreatureOpponent);
                }
                if (playingCreaturePlayer.getHealth() <= 0) {
                    killCreature(playingCreaturePlayer);
                }
            } else {
                damageOpponent(playingCreaturePlayer.getAttack());
            }
        } else {
            if (!playingCreatureOpponent.isBlank()) {
                opponentCreatureHealthLD.postValue(playingCreatureOpponent.getHealth());
                damagePlayer(playingCreatureOpponent.getAttack());
            }
        }

        // Slowly damage both players if no creatures are in play
        if(playingCreatureOpponent.isBlank() && playingCreaturePlayer.isBlank()) {
            damageOpponent(5);
            damagePlayer(5);
        }

        // Update health
        playerHealthLD.postValue(playerHealth);
        enemyHealthLD.postValue(opponentHealth);

        Log.d("Match", "GAMETICK");
    }

    public Match(Zone zone, Player player) {
        this.zone = zone;
        this.player = player;

        zoneRepo = ZoneRepo.getInstance();
        playerRepo = PlayerRepo.getInstance();

        playingCreaturePlayer = Creature.blankCreature();
        playingCreatureOpponent = Creature.blankCreature();
        playingCreatureOpponentLD.setValue(playingCreatureOpponent);
        playingCreaturePlayerLD.setValue(playingCreaturePlayer);

        //check if zone has owner
        if (!zone.hasOwner()) {
            //zone has no owner, so we are the first player
            zone.setOwner(player.getUsername());
            winner = true;
            finishMatch();
        } else {
            winner = false;

            playerHealth = MAX_HEALTH;
            opponentHealth = MAX_HEALTH;

            playerMana = 30;
            opponentMana = 30;

            gameTick = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(1000);
                            gameTickUpdate();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            });

            playingCreaturesPlayer = player.getPlayerCreatureCollection().getDeck().getCreatures();

            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Deck tempDeck = new Deck();

                    for (DataSnapshot creature : dataSnapshot.getChildren()) {
                        int base = creature.child("base").getValue(Integer.class);
                        int exp = creature.child("experience").getValue(Integer.class);
                        int hp = creature.child("hp").getValue(Integer.class);
                        int attack = creature.child("attack").getValue(Integer.class);
                        int defense = creature.child("defense").getValue(Integer.class);
                        int stamina = creature.child("stamina").getValue(Integer.class);

                        Creature c = new Creature(Creature.BaseCreatures.values()[base], 999, exp, hp, attack, defense, stamina, Creature.CreatureType.ELECTRIC, true);
                        tempDeck.addCreature(c);
                        Log.w("Match", "CREATURE ADDED TO AI DECK: " + c.toString());
                    }

                    opponent = new OpponentAI(Match.this, tempDeck);
                    gameTick.start();

                    Log.d("GAMEEEE", "Deck updated! STARTING MATCH!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                }
            };

            playerRepo.requestDeck(zone.getOwner(), listener);
        }
    }

    public MutableLiveData<Integer> getPlayerMana() {
        playerManaLD.setValue(playerMana);
        return playerManaLD;
    }

    public int getOpponentMana() {
        return opponentMana;
    }

    public void setOpponentMana(int mana) {
        opponentMana = mana;
    }

    public MutableLiveData<Integer> getPlayerHealth() {
        playerHealthLD.setValue(playerHealth);
        return playerHealthLD;
    }

    public MutableLiveData<Integer> getOpponentHealth() {
        enemyHealthLD.setValue(opponentHealth);
        return enemyHealthLD;
    }


    public void finishMatch() {
        matchFinishedLD.postValue(true);
        if (gameTick != null) {
            if(gameTick.isAlive()) {
                gameTick.interrupt();
                gameTick = null;
            }
        }
        Log.d("Match", "FINISHING MATCH, WINNER IS: " + (winner ? "PLAYER" : "OPPONENT"));

        if (isWinner()) {
            playerRepo.updateExperience(player.getUid(), 10);
            GameManager.getInstance().updateZoneOwner(zone, player.getUid());
        } else {
            playerRepo.updateExperience(zone.getOwner(), 10);
        }

    }

    public boolean isWinner() {
        return winner;
    }

    public MutableLiveData<Boolean> isMatchFinished() {
        return matchFinishedLD;
    }

    public void playCreature(int index) {
        int cost = playingCreaturesPlayer.get(index).getCost();

        if (playerMana >= cost) playerMana -= cost;
        else return;

        playerManaLD.setValue(playerMana);

        setCreaturePlayer(playingCreaturesPlayer.get(index));

        Log.d("Match", "CREATURE PLAYED: " + playingCreaturePlayer.toString());
    }

    private void damagePlayer(int damage) {
        int damageTaken = (int) (damage * PLAYER_DAMAGE_MULTIPLIER);
        if (damageTaken >= playerHealth) {
            winner = false; // PLAYER LOSES
            playerHealth = 0;

            finishMatch();
        }
        else playerHealth -= damageTaken;

        playerHealthLD.postValue(playerHealth);
    }

    private void damageOpponent(int damage) {
        int damageTaken = (int) (damage * PLAYER_DAMAGE_MULTIPLIER);
        if (damageTaken >= opponentHealth) {
            winner = true; // PLAYER WINS
            opponentHealth = 0;

            finishMatch();
        }
        else opponentHealth -= damageTaken;

        enemyHealthLD.postValue(opponentHealth);
    }

    public MutableLiveData<Creature> getPlayingCreaturePlayer() {
        playingCreaturePlayerLD.setValue(playingCreaturePlayer);
        return playingCreaturePlayerLD;
    }

    public MutableLiveData<Creature> getPlayingCreatureOpponent() {
        playingCreatureOpponentLD.setValue(playingCreatureOpponent);
        return playingCreatureOpponentLD;
    }

    public Player getPlayer() {
        return player;
    }

    public Creature getCreaturePlayer() {
        return playingCreaturePlayer;
    }

    public Creature getCreatureOpponent() {
        return playingCreatureOpponent;
    }

    public void setCreaturePlayer(Creature creature) {
        playingCreaturePlayer = creature;
        playingCreaturePlayerLD.postValue(creature);
    }

    public void setCreatureOpponent(Creature creature) {
        playingCreatureOpponent = creature;
        playingCreatureOpponentLD.postValue(creature);
    }

    private void killCreature(Creature creature) {
        if(creature == playingCreaturePlayer) {
            Log.d("Match", "KILLING CREATURE PLAYER");
            playingCreaturePlayer = Creature.blankCreature();
            playingCreaturePlayerLD.postValue(Creature.blankCreature());
        }
        if(creature == playingCreatureOpponent) {
            Log.d("Match", "KILLING CREATURE OPPONENT");
            playingCreatureOpponent = Creature.blankCreature();
            playingCreatureOpponentLD.postValue(Creature.blankCreature());
        }
    }

    public MutableLiveData<Integer> getPlayerCreatureHealthLD() {
        return playerCreatureHealthLD;
    }

    public MutableLiveData<Integer> getOpponentCreatureHealthLD() {
        return opponentCreatureHealthLD;
    }

}
