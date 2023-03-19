package es.starfallstudios.fallenlegends.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.starfallstudios.fallenlegends.R;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window title to "About"
        setTitle(R.string.about);
        //set back button on title bar to go back to home screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_about_screen);
    }

}
