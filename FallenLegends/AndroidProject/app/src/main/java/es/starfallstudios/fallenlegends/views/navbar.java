package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.starfallstudios.fallenlegends.R;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link navbar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class navbar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public navbar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment navbar.
     */
    // TODO: Rename and change types and number of parameters
    public static navbar newInstance(String param1, String param2) {
        navbar fragment = new navbar();
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
        View view = inflater.inflate(R.layout.fragment_navbar, container, false);

        view.findViewById(R.id.btn_current_zone).setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContent_container, new CurrentZone()).commit();
        });

        view.findViewById(R.id.btn_social).setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContent_container, new SocialView()).commit();
        });

        view.findViewById(R.id.btn_deck).setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContent_container, new DeckView()).commit();
        });

        view.findViewById(R.id.btn_property_zones).setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContent_container, new ZoneManagementFragment()).commit();
        });

        view.findViewById(R.id.btn_shop).setOnClickListener(View -> {
            getParentFragmentManager().beginTransaction().replace(R.id.mainContent_container, new StoreFragment()).commit();
        });
        return view;
    }


}