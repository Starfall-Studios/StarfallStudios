package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.models.GameManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageZoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageZoneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "zoneIndex";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int index;
    private String mParam2;

    public ManageZoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageZoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageZoneFragment newInstance(int param1, String param2) {
        ManageZoneFragment fragment = new ManageZoneFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage_zone, container, false);

        v.findViewById(R.id.zone_Manage_Close_Button).setOnClickListener(v1 -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContent_container, new ZoneManagementFragment()).commit());

        ((TextView) v.findViewById(R.id.zone_manage_title)).setText(GameManager.getInstance().getZonesOwnedByPlayer(GameManager.getInstance().getPlayer().getUid()).get(index).getName());

        v.findViewById(R.id.zone_manage_abandon_button).setOnClickListener(v1 -> {
            GameManager.getInstance().abandonZone(GameManager.getInstance().getZonesOwnedByPlayer(GameManager.getInstance().getPlayer().getUid()).get(index));
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContent_container, new ZoneManagementFragment()).commit();
        });

        return v;
    }
}