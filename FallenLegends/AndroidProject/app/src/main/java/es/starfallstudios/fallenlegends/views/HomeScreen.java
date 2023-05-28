package es.starfallstudios.fallenlegends.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;


import androidx.appcompat.app.AppCompatActivity;

import es.starfallstudios.fallenlegends.R;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import es.starfallstudios.fallenlegends.viewmodels.HomeViewModel;

public class HomeScreen extends AppCompatActivity {

    private HomeViewModel viewModel;

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

        setContentView(R.layout.activity_home_screen);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.setupMap(this);

        //Load navbar fragment intro navbar container
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_container, new navbar()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.headerbar_container, new HeaderBar()).commit();

        findViewById(R.id.updateLocation_button).setOnClickListener(View -> {
            viewModel.updateLocation();
        });

        findViewById(R.id.debugSpawnCreature_button).setOnClickListener(View -> {
            viewModel.spawnCreature();
        });
    }

    public void onProfileClick(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.profile_container, new ProfileFragment()).commit();
        if(findViewById(R.id.profile_container).getVisibility() == View.GONE) {
            findViewById(R.id.profile_container).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.profile_container).setVisibility(View.GONE);
        }
    }

    public void onAboutClick(View view) {
        startActivity(new Intent(this, AboutScreen.class));
    }

    public void onTermsCondClick(View view) {
        startActivity(new Intent(this, TermsConditionsScreen.class));
    }

    public void onLogoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, LoginScreen.class));
        finish();
    }
}
