package es.starfallstudios.fallenlegends.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.starfallstudios.fallenlegends.Fragments.navbar;
import es.starfallstudios.fallenlegends.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new navbar()).commit();

    }
}