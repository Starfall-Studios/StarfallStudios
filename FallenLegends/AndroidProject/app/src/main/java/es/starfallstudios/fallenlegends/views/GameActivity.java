package es.starfallstudios.fallenlegends.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import es.starfallstudios.fallenlegends.models.Creature;
import es.starfallstudios.fallenlegends.models.Deck;
import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.models.Match;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.viewmodels.GameViewModel;

public class GameActivity extends AppCompatActivity {

    private GameViewModel viewModel;
    private ProgressBar manaBar;

    private ProgressBar playerHealthBar;
    private ProgressBar enemyHealthBar;

    private ArrayList<ImageView> cardImages;

    private ArrayList<ArrayList<Integer>> cardsStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        setContentView(R.layout.activity_game);
        manaBar = findViewById(R.id.mana_bar_progress);
        playerHealthBar = findViewById(R.id.player_healtbar_progress);
        enemyHealthBar = findViewById(R.id.opponent_healthbar_progress);

        ArrayList<TextView> card1Stats = new ArrayList<>();
        ArrayList<TextView> card2Stats = new ArrayList<>();
        ArrayList<TextView> card3Stats = new ArrayList<>();
        ArrayList<TextView> card4Stats = new ArrayList<>();

        card1Stats.add((TextView) findViewById(R.id.player_card1_health));
        card1Stats.add((TextView) findViewById(R.id.player_card1_attack));
        card1Stats.add((TextView) findViewById(R.id.player_card1_cost));

        card2Stats.add((TextView) findViewById(R.id.player_card2_health));
        card2Stats.add((TextView) findViewById(R.id.player_card2_attack));
        card2Stats.add((TextView) findViewById(R.id.player_card2_cost));

        card3Stats.add((TextView) findViewById(R.id.player_card3_health));
        card3Stats.add((TextView) findViewById(R.id.player_card3_attack));
        card3Stats.add((TextView) findViewById(R.id.player_card3_cost));

        card4Stats.add((TextView) findViewById(R.id.player_card4_health));
        card4Stats.add((TextView) findViewById(R.id.player_card4_attack));
        card4Stats.add((TextView) findViewById(R.id.player_card4_cost));

        cardImages = new ArrayList<>();

        cardImages.add((ImageView) findViewById(R.id.player_card1));
        cardImages.add((ImageView) findViewById(R.id.player_card2));
        cardImages.add((ImageView) findViewById(R.id.player_card3));
        cardImages.add((ImageView) findViewById(R.id.player_card4));

        cardsStats = new ArrayList<>();

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        Deck deck = GameManager.getInstance().getPlayer().getPlayerCreatureCollection().getDeck();
        //print deck to console
        System.out.println("Deck: ");
        for (Creature c : deck.getCreatures()) {
            System.out.println(c.getName());
        }

        for(int i = 0; i < 4; i++) {
            cardsStats.add(new ArrayList<>());
            cardImages.get(i).setBackgroundResource(deck.getCreatures().get(i).getResourceId());
            cardsStats.get(i).add(deck.getCreatures().get(i).getHealth());
            cardsStats.get(i).add(deck.getCreatures().get(i).getAttack());
            cardsStats.get(i).add(deck.getCreatures().get(i).getCost());
        }

        for (int i = 0; i < 3; i++) {
            card1Stats.get(i).setText(String.valueOf(cardsStats.get(0).get(i)));
            card2Stats.get(i).setText(String.valueOf(cardsStats.get(1).get(i)));
            card3Stats.get(i).setText(String.valueOf(cardsStats.get(2).get(i)));
            card4Stats.get(i).setText(String.valueOf(cardsStats.get(3).get(i)));
        }

        viewModel.getMana().observe(this, mana -> {
            manaBar.setProgress(mana);
        });

        viewModel.getPlayerHealth().observe(this, health -> {
            ((TextView) findViewById(R.id.player_health)).setText(String.valueOf(health) + " HP");
            playerHealthBar.setProgress(health);
        });

        viewModel.getOpponentHealth().observe(this, health -> {
            ((TextView) findViewById(R.id.opponent_health)).setText(String.valueOf(health) + " HP");
            enemyHealthBar.setProgress(health);
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

        viewModel.isMatchFinished().observe(this, isFinished -> {
            if (isFinished) {
                //show finished ui
                getSupportFragmentManager().beginTransaction().replace(R.id.board_container, FinishGameFragment.newInstance(viewModel.isPlayerWinner())).commit();
            }
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