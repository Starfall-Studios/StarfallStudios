package es.starfallstudios.fallenlegends.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.R;

public class CreatureListAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<CreatureCollectionList> creatureCollectionList;

    public CreatureListAdapter(Context context, ArrayList<CreatureCollectionList> creatureCollectionList) {
        this.context = context;
        this.creatureCollectionList = creatureCollectionList;
    }

    @Override
    public int getCount() {
        return creatureCollectionList.size();
    }

    @Override
    public Object getItem(int i) {
        return creatureCollectionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HolderView holderView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup, false);
            holderView = new HolderView(view);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }

        CreatureCollectionList list = creatureCollectionList.get(i);
        holderView.creatureImage.setImageResource(list.getCreatureResourceId());
        //holderView.creatureName.setText(list.getCreatureName());

        return view;
    }

    private static class HolderView {
        private final ImageView creatureImage;
        //private final TextView creatureName;

        public HolderView(View view) {
            this.creatureImage = view.findViewById(R.id.deckview_card_image);
            //this.creatureName = view.findViewById(R.id.deckview_card_name);
        }
    }
}
