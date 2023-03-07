package es.starfallstudios.fallenlegends.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import es.starfallstudios.fallenlegends.GameManager;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.Zone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentZone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentZone extends Fragment {

    //Text Labels
    private TextView zoneName;
    private TextView zoneDescription;
    private TextView zoneOwner;

    private Zone zone;

    public CurrentZone() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CurrentZone newInstance() {
        return new CurrentZone();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_zone, container, false);

        zone = GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation());

        view.findViewById(R.id.acceptButton).setOnClickListener(View -> {
            Toast.makeText(view.getContext(), "Accept", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.closeButton).setOnClickListener(View -> {
            Toast.makeText(view.getContext(), "Close", Toast.LENGTH_SHORT).show();
            //close fragment
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        zoneName = view.findViewById(R.id.txt_zone_name);
        zoneName.append(" " + zone.getName());

        zoneOwner = view.findViewById(R.id.txt_zone_owner);
        zoneOwner.append(String.valueOf(zone.getOwner()));

        // Inflate the layout for this fragment
        return view;
    }
}