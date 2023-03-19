package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.HeaderViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderBar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderBar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txt_playerName;

    public HeaderBar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeaderBar.
     */
    // TODO: Rename and change types and number of parameters
    public static HeaderBar newInstance(String param1, String param2) {
        HeaderBar fragment = new HeaderBar();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_header_bar, container, false);

        txt_playerName = v.findViewById(R.id.txt_playerName);

        HeaderViewModel viewModel = new ViewModelProvider(requireActivity()).get(HeaderViewModel.class);
        viewModel.getPlayer(FirebaseAuth.getInstance().getUid()).observe(getViewLifecycleOwner(), player -> {
            txt_playerName.setText(player.getUsername());
            Log.d("HeaderBar", "onCreateView setting player name to: " + player.getUsername());
        });

        Log.d("HeaderBar", "header view created!");

        return v;
    }
}