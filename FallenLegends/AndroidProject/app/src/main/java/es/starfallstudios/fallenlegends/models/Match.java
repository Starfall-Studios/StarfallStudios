package es.starfallstudios.fallenlegends.models;

import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Match {

    private Zone zone;

    private Player player;
    private Player opponent;

    private int playerMana;
    private int opponentMana;

    private Creature playingCreaturePlayer;
    private Creature playingCreatureOpponent;

    private ArrayList<Creature> playingCreaturesPlayer;

    private Thread gameTick;

    private ProgressBar manaBar;

    private void gameTickUpdate() {
        // Draw mana bar
        manaBar.setProgress(playerMana);

        // Update mana
        if(playerMana < 100) playerMana += 10;
        if (opponentMana < 100) opponentMana += 10;

        Log.d("Match", "GAMETICK");
    }

    public Match(Zone zone, Player player, ProgressBar manaBar) {
        this.zone = zone;
        this.player = player;
        this.manaBar = manaBar;

        playerMana = 30;
        opponentMana = 10;

        playingCreaturePlayer = null;
        playingCreatureOpponent = null;

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

    public void finishMatch() {
        if(gameTick.isAlive()) {
            gameTick.interrupt();
            gameTick = null;
        }
    }

    public void playCreature(int index) {
        Log.d("Match", "PLAYING CREATURE " + index);
        if (playerMana >= 20) playerMana -= 20;
        manaBar.setProgress(playerMana);
    }
}
