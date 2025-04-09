package com.example.kitahack;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPhone, editAddress;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editAddress = findViewById(R.id.editAddress);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // Load previously saved data
        loadProfile();

        btnSaveProfile.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String address = editAddress.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Save to SharedPreferences
                getSharedPreferences("user_profile", MODE_PRIVATE)
                        .edit()
                        .putString("name", name)
                        .putString("email", email)
                        .putString("phone", phone)
                        .putString("address", address)
                        .apply();

                Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadProfile() {
        SharedPreferences prefs = getSharedPreferences("user_profile", MODE_PRIVATE);
        editName.setText(prefs.getString("name", ""));
        editEmail.setText(prefs.getString("email", ""));
        editPhone.setText(prefs.getString("phone", ""));
        editAddress.setText(prefs.getString("address", ""));
    }
}
