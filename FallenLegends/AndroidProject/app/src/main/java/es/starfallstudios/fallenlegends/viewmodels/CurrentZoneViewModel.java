package es.starfallstudios.fallenlegends.viewmodels;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Player;
import es.starfallstudios.fallenlegends.models.PlayerRepo;
import es.starfallstudios.fallenlegends.models.Zone;
import es.starfallstudios.fallenlegends.models.ZoneRepo;

public class CurrentZoneViewModel extends ViewModel {

    @Nullable
    private MutableLiveData<Zone> currentZone;

    @Nullable
    private MutableLiveData<Player> owner;

    private Zone zone;

    private ZoneRepo zoneRepo;
    private PlayerRepo playerRepo;

    public CurrentZoneViewModel() {
        zoneRepo = new ZoneRepo();
        playerRepo = new PlayerRepo();
    }

    public LiveData<Zone> getZone() {
        if (currentZone==null) {
            int zoneId = GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()).getId();
            currentZone = zoneRepo.requestZone(zoneId);
            zone = currentZone.getValue();
        }
        return currentZone;
    }

    public LiveData<Player> getOwner() {
        if (owner==null) {
            owner = playerRepo.requestPlayer((zoneRepo.requestZone(GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()).getId()).getValue()).getOwner());
        }
        return owner;
    }

}
