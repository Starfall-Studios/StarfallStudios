package es.starfallstudios.fallenlegends;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TermsConditionsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set window title to "Terms and Conditions"
        setTitle("Terms and Conditions");
        //set back button on title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_terms_conditions);
    }
}
