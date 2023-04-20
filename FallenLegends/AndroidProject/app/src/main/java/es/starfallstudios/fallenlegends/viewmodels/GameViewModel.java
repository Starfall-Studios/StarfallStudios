package es.starfallstudios.fallenlegends.viewmodels;

import android.view.View;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.Deck;
import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Match;

public class GameViewModel extends ViewModel {

    private Match match;

    public GameViewModel() {
        match = new Match(GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()), GameManager.getInstance().getPlayer());
    }

    public void finishMatch() {
        match.finishMatch();
    }

    public boolean isPlayerWinner() {
        return match.isWinner();
    }

    public void playCard(int index) {
        match.playCreature(index);
    }

    public LiveData<Integer> getMana() {
        return match.getPlayerMana();
    }

    public LiveData<Integer> getPlayerHealth() {
        return match.getPlayerHealth();
    }

    public LiveData<Integer> getOpponentHealth() {
        return match.getOpponentHealth();
    }

    public LiveData<Boolean> isMatchFinished() {
        return match.isMatchFinished();
    }

    public LiveData<Creature> getCurrentPlayerCreature() {
        return match.getPlayingCreaturePlayer();
    }

    public LiveData<Creature> getCurrentOpponentCreature() {
        return match.getPlayingCreatureOpponent();
    }

    public Deck getPlayerDeck() {
        return match.getPlayer().getDeck();
    }

}
