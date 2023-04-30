package es.starfallstudios.fallenlegends.viewmodels;

import androidx.lifecycle.ViewModel;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.CreatureCollection;
import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Player;
import es.starfallstudios.fallenlegends.models.PlayerRepo;

public class DeckFragmentViewModel extends ViewModel {

    private Player player;
    private PlayerRepo playerRepo;

    public DeckFragmentViewModel() {
        player = GameManager.getInstance().getPlayer();
        playerRepo = PlayerRepo.getInstance();
    }

    public CreatureCollection getCreatureCollection() {
        return player.getPlayerCreatureCollection();
    }

    public void addToDeck(Creature creature) {
        creature.addToDeck();
        playerRepo.saveCreatureCollection(player.getUid(), player.getPlayerCreatureCollection());
    }

    public void removeFromDeck(Creature creature) {
        creature.removeFromDeck();
        playerRepo.saveCreatureCollection(player.getUid(), player.getPlayerCreatureCollection());
    }

}
