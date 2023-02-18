package es.starfallstudios.fallenlegends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

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


        setContentView(R.layout.activity_home_screen);
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
