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

import es.starfallstudios.fallenlegends.models.DBManager;
import es.starfallstudios.fallenlegends.R;

public class SignUpScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText email;
    EditText username;
    EditText password;
    EditText repeatPassword;
    Button signupButton;

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

        setContentView(R.layout.activity_signup_screen);

        email = findViewById(R.id.input_email);
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_createpassword);
        signupButton = findViewById(R.id.signupButton);
        repeatPassword = findViewById(R.id.input_createrepeatpassword);

        signupButton.setOnClickListener(v -> signup());
    }

    private void signup (){
        String email = this.email.getText().toString().trim();
        String username = this.username.getText().toString().trim();
        String password = this.password.getText().toString().trim();
        String repeatPassword = this.repeatPassword.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(SignUpScreen.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repeatPassword)) {
            Toast.makeText(SignUpScreen.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DBManager.getInstance().signUpUser(mAuth.getUid(), username, email);
                finish();
                startActivity(new Intent(SignUpScreen.this, HomeScreen.class));
                Toast.makeText(SignUpScreen.this, getResources().getString(R.string.welcomeMsg) + " " + username + "!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpScreen.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(SignUpScreen.this, "Error signing in!", Toast.LENGTH_SHORT).show());

    }

}