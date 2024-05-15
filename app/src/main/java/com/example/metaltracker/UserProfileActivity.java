package com.example.metaltracker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.metaltracker.databinding.ActivityUserProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}