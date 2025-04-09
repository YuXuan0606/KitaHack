package com.example.kitahack;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class signup extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText, addressEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private TextView existingAccountTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        signUpButton = findViewById(R.id.signUpButton);
        existingAccountTextView = findViewById(R.id.existingAccount);

        // Set click listener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input data from EditText
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                // Validate input
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the user data if everything is valid
                    saveUserData(name, email, phone, address, password);
                    // Navigate to the Welcome page after successful sign-up
                    Intent intent = new Intent(signup.this, WelcomeActivity.class);
                    intent.putExtra("userName", name);  // Pass the name to WelcomeActivity
                    startActivity(intent);
                    finish();  // Close the signup activity so the user can't go back to it
                }
            }
        });

        // Set click listener for existing account link (optional)
        existingAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the login activity (optional)
                Toast.makeText(signup.this, "Navigate to Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to save user data to internal storage
    private void saveUserData(String name, String email, String phone, String address, String password) {
        // Prepare the data to be saved in the file
        String userData = "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Address: " + address + "\n" +
                "Password: " + password + "\n";

        try {
            // Open a file output stream to save the data
            FileOutputStream fos = openFileOutput("user_data.txt", Context.MODE_PRIVATE);
            fos.write(userData.getBytes());
            fos.close();

            // Show a success message
            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle errors in saving data
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}