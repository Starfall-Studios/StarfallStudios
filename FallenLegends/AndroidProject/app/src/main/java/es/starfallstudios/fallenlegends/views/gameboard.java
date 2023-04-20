package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gameboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gameboard extends Fragment {

    private GameViewModel viewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public gameboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gameboard.
     */
    // TODO: Rename and change types and number of parameters
    public static gameboard newInstance(String param1, String param2) {
        gameboard fragment = new gameboard();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gameboard, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        viewModel.getCurrentOpponentCreature().observe(getViewLifecycleOwner(), creature -> {
            if (creature != null) {
                ((ImageView) v.findViewById(R.id.current_opponent_card)).setImageResource(creature.getResourceId());
            } else {
                ((ImageView) v.findViewById(R.id.current_opponent_card)).setImageResource(R.drawable.logo_fallen);
            }
        });

        viewModel.getCurrentPlayerCreature().observe(getViewLifecycleOwner(), creature -> {
            if (creature != null) {
                ((ImageView) v.findViewById(R.id.current_player_card)).setImageResource(creature.getResourceId());
            } else {
                ((ImageView) v.findViewById(R.id.current_player_card)).setImageResource(R.drawable.logo_fallen);
            }
        });

        return v;
    }
}