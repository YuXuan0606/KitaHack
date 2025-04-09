package com.example.kitahack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button startButton = findViewById(R.id.startButton);
        if (isUserDataExists()) {
            navigateToWelcomeActivity();
        } else {
            // If no user data exists, show the start button and set its listener
            startButton.setVisibility(View.VISIBLE);  // Make sure button is visible
            startButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            });
        }
    }

    private boolean isUserDataExists() {
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            int character;
            StringBuilder stringBuilder = new StringBuilder();

            // Read content from the file
            while ((character = fis.read()) != -1) {
                stringBuilder.append((char) character);
            }

            // If the file is not empty, user data exists
            return stringBuilder.length() > 0;
        } catch (IOException e) {
            // If file is not found or an error occurs, assume no user data
            return false;
        }
    }

    // Method to navigate to WelcomeActivity with the user's name
    private void navigateToWelcomeActivity() {
        try {
            // Read user data from the file (assumed format: name is the first line)
            FileInputStream fis = openFileInput("user_data.txt");
            int character;
            StringBuilder stringBuilder = new StringBuilder();
            while ((character = fis.read()) != -1) {
                stringBuilder.append((char) character);
            }
            String data = stringBuilder.toString();
            String name = data.split("\n")[0].split(":")[1].trim();  // Extract name

            // Navigate to WelcomeActivity with the user's name
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("userName", name);  // Pass the user's name
            startActivity(intent);
            finish();  // Close MainActivity so the user can't go back
        } catch (IOException e) {
            // Handle error if the file is not found or cannot be read
            e.printStackTrace();
        }
    }
}