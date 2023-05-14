package es.starfallstudios.fallenlegends;

import android.view.View;
import android.widget.Toast;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.MapEntity;
import es.starfallstudios.fallenlegends.models.Utils;

public class CreatureInfoWindow extends MarkerInfoWindow {
    /**
     * @param mapView view of the map
     *
     * @param creature creature to be displayed in the info window
     */

    private MapEntity entity;
    private GameManager gameManager;

    /**
     * Creates a new info window for a creature on map
     * @param mapView view of the map
     *
     * @param creature creature to be displayed in the info window
     */
    public CreatureInfoWindow(MapView mapView, MapEntity entity) {
        super(org.osmdroid.library.R.layout.bonuspack_bubble, mapView);
        this.entity = entity;
        gameManager = GameManager.getInstance();
    }

    @Override
    public void onOpen(Object item) {
        super.onOpen(item);
        String title = "Creature" + entity.getName() + " found!";
        String toast = "Creature" + entity.getName() + " found at " + Utils.distance(entity.getLocation(), gameManager.getUserLocation()) + "m";
        mView.findViewById(org.osmdroid.library.R.id.bubble_image).setVisibility(View.VISIBLE);
        Toast.makeText(getView().getContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
