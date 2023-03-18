package es.starfallstudios.fallenlegends.views;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;


import androidx.appcompat.app.AppCompatActivity;

import es.starfallstudios.fallenlegends.R;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import es.starfallstudios.fallenlegends.viewmodels.HomeViewModel;
import es.starfallstudios.fallenlegends.databinding.ActivityHomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
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

        checkForPermissions();

        ActivityHomeScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
        binding.setViewModel(new HomeViewModel(getParent(), findViewById(R.id.map)));
        binding.executePendingBindings();

        //Load navbar fragment intro navbar container
        getSupportFragmentManager().beginTransaction().replace(R.id.navbar_container, new navbar()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.updateLocation_container, new UpdateLocation_button()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.headerbar_container, new HeaderBar()).commit();


    }

    public void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request permission to user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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
                //mAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginScreen.class));
                return true;
            default:
                return false;
        }

    }

    public boolean onNavMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_locationZone:
                //Zone zone = gameManager.getZone(gameManager.getUserLocation());
                //System.out.println(zone.getName() + ", player at coords: " + gameManager.getUserLocation().getLatitude() + ", " + gameManager.getUserLocation().getLongitude());
                return true;
            default:
                return false;
        }
    }



}
