package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.metaltracker.databinding.ActivityMainMenuBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding=ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gettStockData();
        binding.btnSellNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ProcessActivity.class);

                // Put extra parameters using key-value pairs
                boolean paramValue = true;
                intent.putExtra("isSell", paramValue);

                // Start the activity with the Intent you just created
                startActivity(intent);
            }
        });

        binding.btnBuyNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ProcessActivity.class);

                // Put extra parameters using key-value pairs
                boolean paramValue = false;
                intent.putExtra("isSell", paramValue);

                // Start the activity with the Intent you just created
                startActivity(intent);
            }
        });
        binding.btnProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                // Redirect the user to the login screen or any other appropriate screen
                Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void gettStockData() {
    }
}