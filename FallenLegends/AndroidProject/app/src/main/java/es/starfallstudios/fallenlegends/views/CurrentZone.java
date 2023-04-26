package es.starfallstudios.fallenlegends.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.models.Zone;
import es.starfallstudios.fallenlegends.viewmodels.CurrentZoneViewModel;

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

        CurrentZoneViewModel viewModel = new ViewModelProvider(this).get(CurrentZoneViewModel.class);


        view.findViewById(R.id.acceptButton).setOnClickListener(View -> {
            startActivity(new Intent(getActivity(), GameActivity.class));
        });

        view.findViewById(R.id.closeButton).setOnClickListener(View -> {
            Toast.makeText(view.getContext(), "Close", Toast.LENGTH_SHORT).show();
            //close fragment
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        zoneOwner = view.findViewById(R.id.txt_zone_owner);
        zoneOwner.setText(getResources().getString(R.string.zoneFragment_zoneOwner) + " Nobody");
        zoneName = view.findViewById(R.id.txt_zone_name);
        viewModel.getZoneInfo().observe(getViewLifecycleOwner(), zoneInfo -> {
            zoneName.setText(getResources().getString(R.string.zoneFragment_zoneName) + " " + zoneInfo.getZoneName());
            zoneOwner.setText(getResources().getString(R.string.zoneFragment_zoneOwner) + " " + zoneInfo.getOwnerName());
        });

        // Inflate the layout for this fragment
        return view;
    }
}