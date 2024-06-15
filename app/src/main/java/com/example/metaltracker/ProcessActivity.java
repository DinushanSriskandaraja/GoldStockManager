package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metaltracker.databinding.ActivityProcessBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import Interfaces.SellCheckCallback;

public class ProcessActivity extends AppCompatActivity {

    private ActivityProcessBinding binding;
    private DatabaseReference mDatabase;
    private EditText datePick;
    private EditText priceRate;
    private EditText weight;

    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityProcessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        datePick=binding.pickDate;
        weight=binding.weight;
        priceRate=binding.priceRate;
        description=binding.txtDescription;
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                    sellGold();
                }
            });
        } else {
            // Change button text and set click listener for buying
            binding.btnAction.setText("Buy");
            binding.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform buying action
                    buyGold();
                }
            });
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProcessActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }
    private void buyGold() {
        double price = Double.parseDouble(priceRate.getText().toString().trim());
        String des = description.getText().toString().trim();
        String date = datePick.getText().toString().trim();
        double pawn = Double.parseDouble(weight.getText().toString().trim());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a new entry for the buy
        Map<String, Object> buyData = new HashMap<>();
        buyData.put("price", price);
        buyData.put("description", des);
        buyData.put("date", date);
        buyData.put("weight", pawn);

        // Get a reference to the "buys" node under the current user's data
        DatabaseReference userBuysRef = mDatabase.child("users").child(userId).child("buys");

        // Create a unique key for the new buy entry
        String buyId = userBuysRef.push().getKey();
        userBuysRef.child(buyId).setValue(buyData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProcessActivity.this, "Gold purchase recorded successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProcessActivity.this, "Failed to record gold purchase.", Toast.LENGTH_SHORT).show();
            }
        });

        // Get a reference to the "stock" node under the current user's data
        DatabaseReference userStockRef = mDatabase.child("users").child(userId).child("stock");

        // Check if the stock exists and update it
        userStockRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double currentStock = 0.0;
                if (dataSnapshot.exists()) {
                    currentStock = dataSnapshot.getValue(Double.class);
                }
                double newStock = currentStock + pawn;
                userStockRef.setValue(newStock).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProcessActivity.this, "Stock updated successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProcessActivity.this, "Failed to update stock.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Toast.makeText(ProcessActivity.this, "Failed to access stock data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sellGold() {
        double sellWeight = Double.parseDouble(weight.getText().toString().trim());
        String description = this.description.getText().toString().trim();
        String date = datePick.getText().toString().trim();
        double price = Double.parseDouble(priceRate.getText().toString().trim());

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get a reference to the "stock" node under the current user's data
        DatabaseReference userStockRef = mDatabase.child("users").child(userId).child("stock");

        // Check if the stock exists and update it
        userStockRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double currentStock = 0.0;
                if (dataSnapshot.exists()) {
                    currentStock = dataSnapshot.getValue(Double.class);
                }

                if (currentStock >= sellWeight) {
                    double newStock = currentStock - sellWeight;

                    // Create a new entry for the sell
                    Map<String, Object> sellData = new HashMap<>();
                    sellData.put("price", price);
                    sellData.put("description", description);
                    sellData.put("date", date);
                    sellData.put("weight", sellWeight);

                    // Get a reference to the "sells" node under the current user's data
                    DatabaseReference userSellsRef = mDatabase.child("users").child(userId).child("sells");

                    // Create a unique key for the new sell entry
                    String sellId = userSellsRef.push().getKey();
                    userSellsRef.child(sellId).setValue(sellData).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Update the stock value in the database
                            userStockRef.setValue(newStock).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(ProcessActivity.this, "Gold sale recorded and stock updated successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProcessActivity.this, "Failed to update stock.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(ProcessActivity.this, "Failed to record gold sale.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Show a popup message indicating insufficient stock
                    showInsufficientStockMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors here
                Toast.makeText(ProcessActivity.this, "Failed to access stock data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to show insufficient stock message
    private void showInsufficientStockMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Insufficient Stock")
                .setMessage("You do not have enough stock to sell.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(R.drawable.ic_launcher_foreground)
                .show();
    }



}