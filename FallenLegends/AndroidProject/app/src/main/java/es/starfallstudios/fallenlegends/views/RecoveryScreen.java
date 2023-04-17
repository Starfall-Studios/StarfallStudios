package es.starfallstudios.fallenlegends.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import es.starfallstudios.fallenlegends.R;

public class RecoveryScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(View.SYSTEM_UI_FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException ignored) {
        }

        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_recovery);

        email = findViewById(R.id.input_email_recovery);

        findViewById(R.id.btn_recovery).setOnClickListener(v -> recovery());
    }

    private void recovery() {
        if (email.getText().toString().isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Email Sent!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                email.setError("Email not found");
                email.requestFocus();
            }
        });
    }
}