package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.GameViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinishGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinishGameFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private boolean playerWon;

    public FinishGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param playerWon Parameter 1.
     * @return A new instance of fragment FinishGameFragment.
     */
    public static FinishGameFragment newInstance(boolean playerWon) {
        FinishGameFragment fragment = new FinishGameFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, playerWon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerWon = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finish_game, container, false);

        TextView endGameText = v.findViewById(R.id.resultText);

        GameViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        viewModel.isMatchFinished().observe(getViewLifecycleOwner(), isMatchFinished -> {
            if (isMatchFinished) {
                endGameText.setText(playerWon ? "You won!" : "You lost!");
            }
        });

        v.findViewById(R.id.finishButton).setOnClickListener(View -> {
            requireActivity().finish();
        });

        return v;
    }
}