package es.starfallstudios.fallenlegends.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.models.PlayerRepo;

public class SocialFragmentViewModel extends ViewModel {

    private PlayerRepo playerRepo;

    public SocialFragmentViewModel() {
        playerRepo = PlayerRepo.getInstance();
    }

    public LiveData<ArrayList<String>> getLeaderboard() {
        return playerRepo.getLeaderboard();
    }

}
