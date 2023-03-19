package es.starfallstudios.fallenlegends.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Match;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.GameViewModel;

public class GameActivity extends AppCompatActivity {

    private GameViewModel viewModel;
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

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        viewModel.getMana().observe(this, mana -> {
            manaBar.setProgress(mana);
        });

        viewModel.getPlayerHealth().observe(this, health -> {
            ((TextView) findViewById(R.id.player_health)).setText(String.valueOf(health) + " HP");
        });

        viewModel.getOpponentHealth().observe(this, health -> {
            ((TextView) findViewById(R.id.opponent_health)).setText(String.valueOf(health) + " HP");
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.board_container, new gameboard()).commit();

        findViewById(R.id.player_card1).setOnClickListener(v -> {
            viewModel.playCard(0);
        });
        findViewById(R.id.player_card2).setOnClickListener(v -> {
            viewModel.playCard(1);
        });
        findViewById(R.id.player_card3).setOnClickListener(v -> {
            viewModel.playCard(2);
        });
        findViewById(R.id.player_card4).setOnClickListener(v -> {
            viewModel.playCard(3);
        });
    }

    //on activity destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.finishMatch();
        viewModel = null;
    }
}