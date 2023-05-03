package es.starfallstudios.fallenlegends.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import es.starfallstudios.fallenlegends.R;

public class CreatureListAdapter extends ArrayAdapter<Creature> {

    private final Context context;

    public CreatureListAdapter(@NonNull Context context, int resource, @NonNull List<Creature> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Creature creature = getItem(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup, false);
        }

        ImageView creatureImage = view.findViewById(R.id.deckview_card_image);
        TextView creatureName = view.findViewById(R.id.deckview_card_name);
        TextView creatureHealth = view.findViewById(R.id.deckview_card_health);

        creatureImage.setImageResource(creature.getResourceId());
        creatureName.setText(creature.getName());
        creatureHealth.setText(String.valueOf(creature.getHealth()));

        return view;
    }
}
