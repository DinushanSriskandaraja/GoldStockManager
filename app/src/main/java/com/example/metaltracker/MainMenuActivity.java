package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metaltracker.databinding.ActivityMainMenuBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        binding.btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, TransactionHistoryActivity.class);

                // Put extra parameters using key-value pairs
                boolean paramValue = false;
                intent.putExtra("USER_ID", mAuth.getUid());

                // Start the activity with the Intent you just created
                startActivity(intent);
            }
        });
    }


    private void gettStockData() {
        String uid=mAuth.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uid)
                .child("stock");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Assuming stock is stored as a single value (e.g., total stock count)
                    double stock = dataSnapshot.getValue(Double.class)*8;

                    // Now you can use the 'stock' value as needed
                    // Example: Display in TextView or perform further operations
                    binding.lblOnHandStock.setText(String.valueOf(stock));
                } else {
                    binding.lblOnHandStock.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                //Log.e("StockData", "Failed to retrieve stock data", databaseError.toException());
            }
        });
    }
}