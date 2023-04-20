package es.starfallstudios.fallenlegends.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.starfallstudios.fallenlegends.models.GameManager;
import es.starfallstudios.fallenlegends.R;
import es.starfallstudios.fallenlegends.models.PlayerRepo;

public class LoginScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button loginButton;
    Button signupButton;
    Button recoveryButton;

    private final PlayerRepo playerRepo = new PlayerRepo();

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

        setContentView(R.layout.activity_login_screen);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signupButton = findViewById(R.id.btn_opensignup);
        recoveryButton = findViewById(R.id.btn_openrecovery);

        loginButton.setOnClickListener(v -> login());
        signupButton.setOnClickListener(v -> startActivity(new Intent(LoginScreen.this, SignUpScreen.class)));
        recoveryButton.setOnClickListener(v -> startActivity(new Intent(LoginScreen.this, RecoveryScreen.class)));
    }

    private void login() {
        String email = this.email.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginScreen.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(task -> {
            if (!mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(LoginScreen.this, "Email not verified!", Toast.LENGTH_SHORT).show();
                return;
            }

            playerRepo.requestPlayer(mAuth.getCurrentUser().getUid()).observe(this, player -> {
                GameManager.getInstance().setPlayer(player);
                finish();
                startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                Toast.makeText(LoginScreen.this, getResources().getString(R.string.welcomeMsg) + "!", Toast.LENGTH_SHORT).show();
            });

        }).addOnFailureListener(e -> Toast.makeText(LoginScreen.this, "Error logging in!", Toast.LENGTH_SHORT).show());

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            playerRepo.requestPlayer(currentUser.getUid()).observe(this, player -> {
                GameManager.getInstance().setPlayer(player);
                finish();
                startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                Toast.makeText(LoginScreen.this, getResources().getString(R.string.welcomeMsg) + "!", Toast.LENGTH_SHORT).show();
            });
        }
    }
}