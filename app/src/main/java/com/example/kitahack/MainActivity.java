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

        startButton.setOnClickListener(view -> {
            if (isUserDataExists()) {
                // If user data exists, navigate directly to WelcomeActivity
                navigateToWelcomeActivity();
            } else {
                // If user is not registered, proceed to signup
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean isUserDataExists() {
        try {
            FileInputStream fis = openFileInput("user_data.txt");
            int character;
            StringBuilder stringBuilder = new StringBuilder();

            // Read the content from the file
            while ((character = fis.read()) != -1) {
                stringBuilder.append((char) character);
            }

            // Check if the file is not empty (user data exists)
            return stringBuilder.length() > 0;
        } catch (IOException e) {
            // File doesn't exist or error in reading
            return false;
        }
    }

    // Method to navigate to WelcomeActivity
    private void navigateToWelcomeActivity() {
        try {
            // Read the name from the file (first line)
            FileInputStream fis = openFileInput("user_data.txt");
            int character;
            StringBuilder stringBuilder = new StringBuilder();
            while ((character = fis.read()) != -1) {
                stringBuilder.append((char) character);
            }
            String data = stringBuilder.toString();
            String name = data.split("\n")[0].split(":")[1].trim(); // Extract name from file content

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

