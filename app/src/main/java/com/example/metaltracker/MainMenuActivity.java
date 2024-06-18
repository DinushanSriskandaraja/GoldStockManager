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

import Interfaces.ApiService;
import Models.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainMenuActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainMenuBinding binding;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding=ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gettStockData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.metalpriceapi.com/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of your ApiService interface
        apiService = retrofit.create(ApiService.class);

        // Call your API method
        fetchPriceData();
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

    private void fetchPriceData() {
        String apiKey = "a73c1ddc6a36b7d12186518c0876c4d9";
        String base = "XAU";
        String currencies = "LKR";
        Call<ApiResponse> call = apiService.fetchData(apiKey, base, currencies);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    double price = apiResponse.getRates().get("LKR");
                    price=(price/32)*8;
                    // Update UI with fetched data
                    String formattedPrice = String.format("%.2f", price);
                    binding.lblDayPrice.setText(formattedPrice);
                } else {
                    // Handle unsuccessful responses
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle network errors or API call failures
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