package es.starfallstudios.fallenlegends.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Observable;
import java.util.Observer;

import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Player;
import es.starfallstudios.fallenlegends.models.PlayerRepo;

public class HeaderViewModel extends ViewModel {

    @Nullable
    private MutableLiveData<Player> player;

    private PlayerRepo playerRepo;

    public HeaderViewModel() {
        playerRepo = new PlayerRepo();
    }

    @Nullable
    public LiveData<Player> getPlayer(String uid) {
        if (player==null) {
            player = playerRepo.requestPlayer(uid);
        }
        return player;
    }
}
