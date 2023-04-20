package es.starfallstudios.fallenlegends.models;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

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

    private boolean winner;

    MutableLiveData<Creature> playingCreaturePlayerLD = new MutableLiveData<>();
    MutableLiveData<Creature> playingCreatureOpponentLD = new MutableLiveData<>();

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

        if(playingCreaturePlayer != null) {
            if (playingCreatureOpponent != null) {
                playingCreatureOpponent.setHealth(playingCreatureOpponent.getHealth() - playingCreaturePlayer.getAttack());
                playingCreaturePlayer.setHealth(playingCreaturePlayer.getHealth() - playingCreatureOpponent.getAttack());
                if (playingCreatureOpponent.getHealth() <= 0) {
                    playingCreatureOpponent = null;
                }
                if (playingCreaturePlayer.getHealth() <= 0) {
                    playingCreaturePlayer = null;
                }
            } else {
                damageOpponent(playingCreaturePlayer.getAttack());
            }
        } else {
            if (playingCreatureOpponent != null) {
                damagePlayer(playingCreatureOpponent.getAttack());
            }
        }

        if(playingCreaturePlayer == null && playingCreatureOpponent == null) {
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

        winner = false;

        playerHealth = MAX_HEALTH;
        opponentHealth = MAX_HEALTH;

        playingCreaturesPlayer = player.getDeck().getCreatures();

        playerMana = 30;
        opponentMana = 30;

        playingCreaturePlayer = null;
        playingCreatureOpponent = new Creature(Creature.BaseCreatures.THUNDERWING, 9998, 20, 60, 50, 50, 50, Creature.CreatureType.ELECTRIC);

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
        gameTick.start();
    }

    public MutableLiveData<Integer> getPlayerMana() {
        playerManaLD.setValue(playerMana);
        return playerManaLD;
    }

    public MutableLiveData<Integer> getOpponentMana() {
        enemyManaLD.setValue(opponentMana);
        return enemyManaLD;
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
    }

    public boolean isWinner() {
        return winner;
    }

    public MutableLiveData<Boolean> isMatchFinished() {
        return matchFinishedLD;
    }

    public void playCreature(int index) {
        Log.d("Match", "PLAYING CREATURE " + index);
        if (playerMana >= 20) playerMana -= 20;

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
    }

    private void damageOpponent(int damage) {
        int damageTaken = (int) (damage * PLAYER_DAMAGE_MULTIPLIER);
        if (damageTaken >= opponentHealth) {
            winner = true; // PLAYER WINS
            opponentHealth = 0;

            finishMatch();
        }
        else opponentHealth -= damageTaken;
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
        playingCreaturePlayerLD.setValue(creature);
    }

    public void setCreatureOpponent(Creature creature) {
        playingCreatureOpponent = creature;
        playingCreatureOpponentLD.setValue(creature);
    }

}
