package es.starfallstudios.fallenlegends;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeScreen extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this));

        getLocation();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }


        setContentView(R.layout.activity_home_screen);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //request permission to user
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            System.out.println("Longitude: " + longitude + " Latitude: " + latitude);
                            TextView textView = findViewById(R.id.textView_HomeScreen);
                            textView.setText("Longitude: " + longitude + " Latitude: " + latitude);
                        }
                    }
                });
    }

    public void onAboutClick(View view) {
        Intent intent = new Intent(this, AboutScreen.class);
        startActivity(intent);
    }

    public void onTermsClick(View view) {
        Intent intent = new Intent(this, TermsConditionsScreen.class);
        startActivity(intent);
    }
}
