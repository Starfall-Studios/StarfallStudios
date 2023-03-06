package es.starfallstudios.fallenlegends.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import es.starfallstudios.fallenlegends.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentZone#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentZone extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrentZone() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentZone.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentZone newInstance(String param1, String param2) {
        CurrentZone fragment = new CurrentZone();
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

        View view = inflater.inflate(R.layout.fragment_current_zone, container, false);

        view.findViewById(R.id.acceptButton).setOnClickListener(View -> {
            Toast.makeText(view.getContext(), "Accept", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.closeButton).setOnClickListener(View -> {
            Toast.makeText(view.getContext(), "Close", Toast.LENGTH_SHORT).show();
            //close fragment
            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        // Inflate the layout for this fragment
        return view;
    }
}