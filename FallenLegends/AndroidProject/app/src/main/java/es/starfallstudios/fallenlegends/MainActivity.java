package es.starfallstudios.fallenlegends;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class MainActivity extends AppCompatActivity {

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameManager = GameManager.getInstance();
        gameManager.getZones();
        gameManager.getCreatures();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        View logo = findViewById(R.id.imageView);
        scaleView(logo, 0.95f, 1.15f);

        Thread background = new Thread(() -> {
            try {
                // Thread will sleep for 5 seconds
                Thread.sleep(5*1000);

                // After 5 seconds redirect to another intent
                Intent i = new Intent(MainActivity.this,LoginScreen.class);
                startActivity(i);

                //Remove activity
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // start thread
        background.start();
    }

    public void scaleView(View v, float startScale, float endScale) {
        System.out.println("Scaling view");
        Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.7f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(5000);
        v.startAnimation(anim);
    }

}