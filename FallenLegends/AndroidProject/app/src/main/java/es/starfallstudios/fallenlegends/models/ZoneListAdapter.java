package es.starfallstudios.fallenlegends.models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.starfallstudios.fallenlegends.R;

public class ZoneListAdapter extends ArrayAdapter<Zone> {

    private final Context context;

    public ZoneListAdapter(Context context, int resource) {
        super(context, resource, GameManager.getInstance().getZonesOwnedByPlayer(GameManager.getInstance().getPlayer().getUid()));
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Zone zone = getItem(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.zonecardview, viewGroup, false);
        }

        ImageView zoneImage = view.findViewById(R.id.zoneview_card_image);
        TextView zoneName = view.findViewById(R.id.zoneview_card_name);
        TextView zoneDescription = view.findViewById(R.id.zoneview_card_desc);
        Button zoneButton = view.findViewById(R.id.zoneview_card_button);

        zoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ZoneListAdapter", "onClick: " + zone.getName());
            }
        });

        zoneImage.setImageResource(R.drawable.icon_zones);
        zoneName.setText(zone.getName());
        zoneDescription.setText("testt");

        return view;
    }

}
