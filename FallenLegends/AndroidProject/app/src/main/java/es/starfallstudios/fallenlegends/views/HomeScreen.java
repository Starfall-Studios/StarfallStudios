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

    public void onProfileClick(View view) { //Fix this method
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_profile, popup.getMenu());
        popup.show();
    }

    public boolean onProfileMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                startActivity(new Intent(this, AboutScreen.class));
                return true;
            case R.id.TermsConditions:
                startActivity(new Intent(this, TermsConditionsScreen.class));
                return true;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginScreen.class));
                return true;
            default:
                return false;
        }

    }

}
