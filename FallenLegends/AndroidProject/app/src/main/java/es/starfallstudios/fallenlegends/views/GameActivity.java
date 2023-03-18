package es.starfallstudios.fallenlegends.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import es.starfallstudios.fallenlegends.Models.GameManager;
import es.starfallstudios.fallenlegends.Models.Match;
import es.starfallstudios.fallenlegends.R;

public class GameActivity extends AppCompatActivity {

    private Match match;
    private ProgressBar manaBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        setContentView(R.layout.activity_game);

        manaBar = findViewById(R.id.mana_bar_progress);

        match = new Match(GameManager.getInstance().getZone(GameManager.getInstance().getUserLocation()), GameManager.getInstance().getPlayer(), manaBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new gameboard()).commit();

        findViewById(R.id.player_card1).setOnClickListener(v -> {
            match.playCreature(0);
        });
        findViewById(R.id.player_card2).setOnClickListener(v -> {
            match.playCreature(1);
        });
        findViewById(R.id.player_card3).setOnClickListener(v -> {
            match.playCreature(2);
        });
        findViewById(R.id.player_card4).setOnClickListener(v -> {
            match.playCreature(3);
        });
    }

    //on activity destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        match.finishMatch();
        match = null;
    }
}