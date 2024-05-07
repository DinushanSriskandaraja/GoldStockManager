package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.metaltracker.databinding.ActivityProcessBinding;

public class ProcessActivity extends AppCompatActivity {

    private ActivityProcessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProcessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Retrieve the boolean parameter value
        boolean isSell = intent.getBooleanExtra("isSell",false); // Default value is false if not provided

        // Update UI based on the boolean value
        if (isSell) {
            // Change button text and set click listener for selling
            binding.btnAction.setText("Sell");
            binding.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform selling action
                }
            });
        } else {
            // Change button text and set click listener for buying
            binding.btnAction.setText("Buy");
            binding.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform buying action
                }
            });
        }
    }
}