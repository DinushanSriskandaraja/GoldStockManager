package com.example.metaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.metaltracker.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ActivityUserProfileBinding binding;
    private EditText txtUname;
    private EditText txtShopName;
    private EditText txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        txtUname=binding.txtUserNameInput;
        txtShopName=binding.txtShopNameInput;
        txtEmail=binding.txtEmailInput;
        String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        txtEmail.setText(userMail);
        getUserProfileData();
    }
    private void getUserProfileData(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the user's data in the database
        DatabaseReference userRef = mDatabase.child("users").child(userId);

        // Add a listener to fetch data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if user data exists
                if (dataSnapshot.exists()) {
                    // Get user data from dataSnapshot
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String shopName = dataSnapshot.child("shopName").getValue(String.class);

                    // Set the data to EditText fields
                    txtUname.setText(username);
                    txtShopName.setText(shopName);
                } else {
//                    Toast.makeText(UserProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveProfileData(View view) {
        String userName=txtUname.getText().toString().trim();
        String shopName=txtShopName.getText().toString().trim();
//        String email=txtEmail.getText().toString().trim();

        Toast.makeText(UserProfileActivity.this, "User Updated Successfully! ", Toast.LENGTH_SHORT).show();

        // Get the current user ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a Map to store user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userName);
        userData.put("shopName", shopName);

        mDatabase.child("users").child(userId).setValue(userData);

    }
    public void navBack(View view) {
        Intent intent = new Intent(UserProfileActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
}