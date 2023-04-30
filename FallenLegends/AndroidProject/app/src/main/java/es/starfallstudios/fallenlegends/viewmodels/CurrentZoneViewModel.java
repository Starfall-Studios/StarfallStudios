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
import es.starfallstudios.fallenlegends.models.ZoneInfo;
import es.starfallstudios.fallenlegends.models.ZoneRepo;

public class CurrentZoneViewModel extends ViewModel {

    private final ZoneRepo zoneRepo;

    public CurrentZoneViewModel() {
        zoneRepo = ZoneRepo.getInstance();
    }

    public LiveData<Zone> getZone() {
        return zoneRepo.requestZone(GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()).getId());
    }

    public LiveData<ZoneInfo> getZoneInfo() {
        return zoneRepo.requestZoneInfo(GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()).getId());
    }

}
