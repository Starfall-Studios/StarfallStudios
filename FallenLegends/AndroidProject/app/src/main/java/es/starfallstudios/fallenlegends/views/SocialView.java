package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.SocialFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FrameLayout container;
    private Fragment friendsFragment;
    private Fragment teamsFragment;
    private Fragment leaderboardsFragment;

    public SocialView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderBoardView.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialView newInstance(String param1, String param2) {
        SocialView fragment = new SocialView();
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

        View v = inflater.inflate(R.layout.fragment_social, container, false);

        ListView listView = v.findViewById(R.id.list_social);
        ArrayList<String> names = new ArrayList<>();

        SocialFragmentViewModel viewModel = new ViewModelProvider(this).get(SocialFragmentViewModel.class);

        viewModel.getLeaderboard().observe(getViewLifecycleOwner(), leaderboard -> {
            names.addAll(leaderboard);
            names.sort(String::compareTo);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
            listView.setAdapter(arrayAdapter);
        });

        v.findViewById(R.id.btn_social_back).setOnClickListener(view -> {
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        // Inflate the layout for this fragment
        return v;
    }
}