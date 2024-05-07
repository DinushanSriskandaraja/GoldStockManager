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

public class MainMenuActivity extends AppCompatActivity {

    private ActivityMainMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding=ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
    }
}