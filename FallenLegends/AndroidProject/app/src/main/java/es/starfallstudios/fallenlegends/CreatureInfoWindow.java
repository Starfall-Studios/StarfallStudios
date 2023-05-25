package es.starfallstudios.fallenlegends;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.DBManager;
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
     * @param entity creature to be displayed in the info window
     */
    public CreatureInfoWindow(MapView mapView, MapEntity entity) {
        super(R.layout.map_entity_bubble, mapView);
        this.entity = entity;
        gameManager = GameManager.getInstance();
    }

    @Override
    public void onOpen(Object item) {
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        mView.findViewById(R.id.bubble_image).setVisibility(View.VISIBLE);

        Button button = (Button) mView.findViewById(org.osmdroid.library.R.id.bubble_moreinfo);
        ImageView icon = mView.findViewById(org.osmdroid.library.R.id.bubble_image);
        icon.setImageResource(gameManager.creatureResources.get(entity.getBaseCreature()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "HOLA HEHE", Toast.LENGTH_SHORT).show();
                //String msg = DBManager.getInstance().captureCreature(entity) ? "Creature captured!" : "Creature escaped!";
                //Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
