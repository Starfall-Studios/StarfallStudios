package es.starfallstudios.fallenlegends;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.views.DeckView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageCreatureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageCreatureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "health";
    private static final String ARG_PARAM3 = "attack";
    private static final String ARG_PARAM4 = "mana";
    private static final String ARG_PARAM5 = "imageResourceId";

    // TODO: Rename and change types of parameters
    private String name;
    private int health;
    private int attack;
    private int mana;
    private int resourceId;

    public ManageCreatureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageCreatureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageCreatureFragment newInstance(String param1, String param2) {
        ManageCreatureFragment fragment = new ManageCreatureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            health = getArguments().getInt(ARG_PARAM2);
            attack = getArguments().getInt(ARG_PARAM3);
            mana = getArguments().getInt(ARG_PARAM4);
            resourceId = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage_creature, container, false);

        v.findViewById(R.id.creature_Manage_Close_Button).setOnClickListener(v1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainContent_container, new DeckView()).commit();
        });

        ((ImageView) v.findViewById(R.id.creature_Manage_Image)).setImageResource(resourceId);

        ((TextView) v.findViewById(R.id.creature_manage_title)).setText(name);
        ((TextView) v.findViewById(R.id.creature_manage_health)).setText(String.valueOf(health));
        ((TextView) v.findViewById(R.id.creature_manage_attack)).setText(String.valueOf(attack));
        ((TextView) v.findViewById(R.id.creature_manage_mana)).setText(String.valueOf(mana));

        return v;
    }
}