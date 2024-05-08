package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.metaltracker.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtMail;
    private EditText txtPw;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        txtMail = binding.txtMail;
        txtPw = binding.txtPw;
        Button btnlogin = binding.btnLogin;
        // Initialize views
        btnlogin.setOnClickListener(view -> {
            // Check if txtMail and txtPw are not null
            if (txtMail != null && txtPw != null) {
                String email = txtMail.getText().toString().trim();
                String password = txtPw.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    signIn(email, password);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Text fields are not initialized", Toast.LENGTH_SHORT).show();
            }
        });
        // Apply window insets listener for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // TODO: Update UI based on the currentUser
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void signUp(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Update UI as needed
                            Toast.makeText(MainActivity.this, "Sign up successful.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
