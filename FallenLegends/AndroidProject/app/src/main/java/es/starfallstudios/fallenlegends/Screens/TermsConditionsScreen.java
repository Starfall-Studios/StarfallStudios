package es.starfallstudios.fallenlegends.Screens;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import es.starfallstudios.fallenlegends.R;

public class TermsConditionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window title to "Terms and Conditions"
        setTitle(R.string.TermsCond);
        //set back button on title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_terms_conditions);
    }
}
